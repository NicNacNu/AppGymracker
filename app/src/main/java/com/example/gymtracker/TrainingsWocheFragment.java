package com.example.gymtracker;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gymtracker.db.WochenUebungsCorssRefDAO;
import com.example.gymtracker.db.uebungDAO;
import com.example.gymtracker.db.wochenDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainingsWocheFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> tageListe;
    private HashMap<String, List<String>> trainingsEinheiten;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int geladeneTage = 0; // Zähler für geladene Tage

    WochenUebungsCorssRefDAO wochenUebungsCorssRefDAO = UebungMainApplication.getAppDatabase().getWochenUebungsCorssRefDAO();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainingswoche, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);

        initData();
        return view;
    }

    private void initData() {
        // Initialisiere Listen
        tageListe = new ArrayList<>();
        trainingsEinheiten = new HashMap<>();

        tageListe.add("Montag");
        tageListe.add("Dienstag");
        tageListe.add("Mittwoch");
        tageListe.add("Donnerstag");
        tageListe.add("Freitag");
        tageListe.add("Samstag");
        tageListe.add("Sontag");

        // Lade Trainingsdaten im Hintergrund und benachrichtige den UI-Thread
        executorService.execute(() -> {
            List<String> montag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Montag");
            updateTrainingsEinheiten("Montag", montag);

            List<String> dienstag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Dienstag");
            updateTrainingsEinheiten("Dienstag", dienstag);

            List<String> mittwoch = wochenUebungsCorssRefDAO.getUebungenFuerTag("Mittwoch");
            updateTrainingsEinheiten("Mittwoch", mittwoch);

            List<String> donnerstag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Donnerstag");
            updateTrainingsEinheiten("Donnerstag", donnerstag);

            List<String> freitag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Freitag");
            updateTrainingsEinheiten("Freitag", freitag);

            List<String> samstag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Samstag");
            updateTrainingsEinheiten("Samstag", samstag);

            List<String> sontag = wochenUebungsCorssRefDAO.getUebungenFuerTag("Sontag");
            updateTrainingsEinheiten("Sontag", sontag);
        });
    }

    private synchronized void updateTrainingsEinheiten(String tag, List<String> trainings) {
        // Diese Methode stellt sicher, dass die Daten korrekt aktualisiert werden
        if (trainings != null) {
            trainingsEinheiten.put(tag, trainings);
        } else {
            trainingsEinheiten.put(tag, new ArrayList<>());
        }

        geladeneTage++; // Erhöhe den Zähler

        // Überprüfe, ob alle Tage geladen wurden
        if (geladeneTage == 7) {
            // Benachrichtige den UI-Thread, dass die Daten geladen wurden und den Adapter aktualisieren
            requireActivity().runOnUiThread(this::setupAdapter);
        }
    }

    private void setupAdapter() {
        List<Map<String, String>> groupList = new ArrayList<>();
        List<List<Map<String, String>>> childList = new ArrayList<>();

        for (String tag : tageListe) {
            Map<String, String> groupMap = new HashMap<>();
            groupMap.put("day", tag);
            groupList.add(groupMap);

            List<Map<String, String>> childItems = new ArrayList<>();
            List<String> trainings = trainingsEinheiten.get(tag);
            if (trainings != null) {
                for (String training : trainings) {
                    Map<String, String> childMap = new HashMap<>();
                    childMap.put("trainings", training);
                    childItems.add(childMap);
                }
            }
            childList.add(childItems);
        }

        expandableListAdapter = new SimpleExpandableListAdapter(
                getContext(),
                groupList,
                R.layout.expandable_list_group,
                new String[]{"day"},
                new int[]{R.id.textViewDay},
                childList,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"trainings"},
                new int[]{android.R.id.text1}
        );

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            ImageView editIcon = v.findViewById(R.id.imageViewEdit);
            editIcon.setOnClickListener(view -> openEditFragment(tageListe.get(groupPosition)));
            return false;
        });
    }

    private void openEditFragment(String day) {
        Fragment editFragment = EditDayFragment.newInstance(day);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


