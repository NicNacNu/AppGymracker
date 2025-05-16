package com.example.gymtracker;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gymtracker.db.WochenUebungsCorssRefDAO;
import com.example.gymtracker.db.wochenDAO;
import com.example.gymtracker.db.uebungDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UebungAdapter extends ArrayAdapter<uebung> {
    private final Context context;
    private final List<uebung> uebungsList;
    private final int wocheID;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    WochenUebungsCorssRefDAO crossRefDAO = UebungMainApplication.getAppDatabase().getWochenUebungsCorssRefDAO();
    wochenDAO wochenDAO = UebungMainApplication.getAppDatabase().getWochenDAO();
    uebungDAO uebungDAO = UebungMainApplication.getAppDatabase().getUebungDAO();


    public UebungAdapter(@NonNull Context context, List<uebung> uebungsList, int wocheID) {
        super(context, 0, uebungsList);
        this.context = context;
        this.uebungsList = uebungsList;
        this.wocheID = wocheID;
        this.crossRefDAO = crossRefDAO;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_uebung, parent, false);
        }

        TextView textViewUebung = convertView.findViewById(R.id.textViewUebung);
        ImageView buttonToggle = convertView.findViewById(R.id.buttonToggle);

        uebung uebung = uebungsList.get(position);
        textViewUebung.setText(uebung.getUebungName());

        // Prüfe, ob die Übung bereits mit der Woche verknüpft ist
        executorService.execute(() -> {
            boolean isLinked = crossRefDAO.isUebungInWoche(wocheID, uebung.getUebungID());

            // UI-Update im Hauptthread
            ((Activity) context).runOnUiThread(() -> {
                buttonToggle.setImageResource(isLinked ? R.drawable.baseline_horizontal_rule_24 : R.drawable.baseline_add_24);
            });
        });

        buttonToggle.setOnClickListener(v ->
        {
            executorService.execute(new Runnable()
            {
                @Override
                public void run() {
                    boolean isLinked = crossRefDAO.isUebungInWoche(wocheID, uebung.getUebungID());

                    boolean wocheExists = wochenDAO.exists(wocheID);
                    boolean uebungExists = uebungDAO.exists(uebung.getUebungID());

                    if (!wocheExists || !uebungExists) {
                        Log.e("DB_ERROR", "Fremdschlüsselprüfung fehlgeschlagen: WochenID oder UebungID existiert nicht!");
                        return;
                    }

                    if (isLinked) {
                        crossRefDAO.deleteCrossRef(wocheID, uebung.getUebungID());
                    } else {
                        crossRefDAO.addCrossRef(new WocheUebungCrossRef(wocheID, uebung.getUebungID()));
                    }

                    // UI-Update im Hauptthread
                    ((Activity) context).runOnUiThread(() -> {
                        buttonToggle.setImageResource(isLinked ? R.drawable.baseline_add_24 : R.drawable.baseline_horizontal_rule_24);
                    });
                }
            });
        });

        return convertView;
    }
}

