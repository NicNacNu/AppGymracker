package com.example.gymtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.gymtracker.db.TagUebungsCrossRefDAO;
import com.example.gymtracker.db.uebungDAO;
import com.example.gymtracker.db.tagDAO;

public class WorkoutDetailFragment extends Fragment {

    private static final String ARG_WORKOUT_NAME = "workout_name";
    private String workoutName;
    private static int workoutID;
    private double personalRecord = 0.0; // Standard PR-Wert
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private uebungDAO uebungDAO = UebungMainApplication.getAppDatabase().getUebungDAO();
    private tagDAO tagDAO = UebungMainApplication.getAppDatabase().getTagDAO();
    private TagUebungsCrossRefDAO tagUebungsCrossRefDAO = UebungMainApplication.getAppDatabase().getTagUebungsCrossRefDAO();

    public static WorkoutDetailFragment newInstance(int id, String workoutName) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        workoutID = id;
        args.putString(ARG_WORKOUT_NAME, workoutName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        TextView workoutTitle = view.findViewById(R.id.workoutTitle);
        TextView textPersonalRecord = view.findViewById(R.id.textPersonalRecord);
        EditText editArbeitsSets = view.findViewById(R.id.editArbeitsSets);
        EditText editWiederholungen = view.findViewById(R.id.editWiederholungen);
        EditText editMaxGewicht = view.findViewById(R.id.editMaxGewicht);
        Button btnSpeichern = view.findViewById(R.id.btnSpeichern);

        if (getArguments() != null) {
            workoutName = getArguments().getString(ARG_WORKOUT_NAME);
            workoutTitle.setText(workoutName);
        }

        // **Lade bestehende Werte aus der Datenbank für den heutigen Tag**
        String heutigesDatum = getCurrentDate();
        executorService.execute(() -> {
            personalRecord = uebungDAO.getUebungPR(workoutID);
            TagUebungsCrossRef bestehenderEintrag = tagUebungsCrossRefDAO.getEintragByDatumUndUebung(heutigesDatum, workoutID);

            requireActivity().runOnUiThread(() -> {
                textPersonalRecord.setText("Personal Record: " + personalRecord + " Kg");

                if (bestehenderEintrag != null) {
                    editArbeitsSets.setText(String.valueOf(bestehenderEintrag.arbeitsSets));
                    editWiederholungen.setText(String.valueOf(bestehenderEintrag.wiederholungen));
                    editMaxGewicht.setText(String.valueOf(bestehenderEintrag.maxGewichtStr));
                }
            });
        });

        // **Speichern-Button**
        btnSpeichern.setOnClickListener(v -> {
            String arbeitsSetsStr = editArbeitsSets.getText().toString();
            String wiederholungenStr = editWiederholungen.getText().toString();
            String maxGewichtStr = editMaxGewicht.getText().toString();

            if (!arbeitsSetsStr.isEmpty() && !wiederholungenStr.isEmpty() && !maxGewichtStr.isEmpty()) {
                int arbeitsSets = Integer.parseInt(arbeitsSetsStr);
                int wiederholungen = Integer.parseInt(wiederholungenStr);
                double maxGewicht = Double.parseDouble(maxGewichtStr);

                // **Falls neuer PR, aktualisieren**
                if (maxGewicht > personalRecord) {
                    personalRecord = maxGewicht;
                    textPersonalRecord.setText("Personal Record: " + personalRecord + " kg");
                    executorService.execute(() -> uebungDAO.setUebungPR(workoutID, personalRecord));
                }

                insertTodayIfNotExists();

                executorService.execute(() -> {
                    TagUebungsCrossRef bestehenderEintrag = tagUebungsCrossRefDAO.getEintragByDatumUndUebung(heutigesDatum, workoutID);
                    if (bestehenderEintrag == null) {
                        // **Neuen Eintrag hinzufügen**
                        TagUebungsCrossRef neuerEintrag = new TagUebungsCrossRef(heutigesDatum, workoutID, arbeitsSets, wiederholungen, maxGewicht);
                        tagUebungsCrossRefDAO.addTagesUebungCrossRef(neuerEintrag);
                    } else {
                        // **Existierenden Eintrag aktualisieren**
                        bestehenderEintrag.arbeitsSets = arbeitsSets;
                        bestehenderEintrag.wiederholungen = wiederholungen;
                        bestehenderEintrag.maxGewichtStr = maxGewicht;
                        tagUebungsCrossRefDAO.updateTagesUebungCrossRef(bestehenderEintrag);
                    }
                });
            }


        });

        return view;
    }

    private void insertTodayIfNotExists() {
        executorService.execute(() -> {
            String heutigesDatum = getCurrentDate();
            if (tagDAO.countTagByDatum(heutigesDatum) == 0) {
                tagDAO.insert(new tag(heutigesDatum));
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

}


