package com.example.gymtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {

    public ListAdapter(@NonNull Context context, ArrayList<ListData> ArrlistData) {
        super(context, R.layout.list_item, ArrlistData);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Hole das aktuelle ListData Objekt
        ListData listData = getItem(position);

        // Falls das view noch null ist, erstelle eine neue View
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Referenz zu den UI-Elementen in der list_item.xml
        TextView uebungTextView = view.findViewById(R.id.UebungTagListView);

        // Setze die Daten für die TextViews aus ListData
        if (listData != null) {
            // Beispiel: TrainingsDaten und Trainingstag anzeigen
            uebungTextView.setText(listData.getUebung().getUebungsName());  // Annahme: Uebung hat getUebungsName()
        }

        return view;
    }
}
