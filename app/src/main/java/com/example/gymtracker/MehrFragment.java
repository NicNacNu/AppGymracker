package com.example.gymtracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gymtracker.db.TagUebungsCrossRefDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MehrFragment extends Fragment {

    private Button openDatePickerButton;
    private ListView listViewTrainingsData;  // Verwende eine ListView statt ExpandableListView
    private TagUebungsCrossRefDAO tagUebungsCrossRefDAO;

    private int selectedYear, selectedMonth, selectedDay;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mehr, container, false);

        // Initialisierung der UI-Komponenten
        openDatePickerButton = view.findViewById(R.id.openDatePickerButton);
        listViewTrainingsData = view.findViewById(R.id.listViewTrainingsData);  // Neue ID für ListView

        // DAO Initialisieren
        tagUebungsCrossRefDAO = UebungMainApplication.getAppDatabase().getTagUebungsCrossRefDAO();

        // Listener für den Button, der den DatePickerDialog öffnet
        openDatePickerButton.setOnClickListener(v -> openDatePickerDialog());

        return view;
    }

    // Methode, um den DatePickerDialog zu öffnen
    private void openDatePickerDialog() {
        // Aktuelles Datum holen
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog erstellen
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Nach Auswahl eines Datums wird der Wochentag berechnet und Trainingsdaten geladen
                    selectedYear = year;
                    selectedMonth = monthOfYear;
                    selectedDay = dayOfMonth;

                    // Datum im Format yyyy-MM-dd
                    String datum = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    loadTrainingsDataForDate(datum);
                },
                selectedYear,
                selectedMonth,
                selectedDay
        );

        // Dialog anzeigen
        datePickerDialog.show();
    }

    // Methode zum Abrufen der Trainingsdaten und der Übung
    private void loadTrainingsDataForDate(String datum) {
        executorService.execute(() -> {
            // Lade die Daten für das angegebene Datum
            List<TagUebungsCrossRef> trainingsData = tagUebungsCrossRefDAO.getTrainingsDataForDate(datum);

            // Liste der Übungsnamen und deren IDs
            Map<Integer, String> uebungsNamenMap = new HashMap<>();
            for (TagUebungsCrossRef training : trainingsData) {
                // Hole den Übungsnamen anhand der uebungID
                String uebungsName = tagUebungsCrossRefDAO.getUebungNameById(training.uebungID);
                uebungsNamenMap.put(training.uebungID, uebungsName);
            }

            // Aktualisiere UI im Haupt-Thread
            getActivity().runOnUiThread(() -> {
                if (trainingsData != null && !trainingsData.isEmpty()) {
                    updateListView(trainingsData, uebungsNamenMap);
                } else {
                    Toast.makeText(getActivity(), "Keine Trainingsdaten für das ausgewählte Datum", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Methode zum Aktualisieren der ListView
    private void updateListView(List<TagUebungsCrossRef> trainingsData, Map<Integer, String> uebungsNamenMap) {
        List<String> listItems = new ArrayList<>();

        // Füge alle Trainingsdaten zu einer Liste hinzu
        for (TagUebungsCrossRef training : trainingsData) {
            String uebungsName = uebungsNamenMap.get(training.uebungID);
            String listItem = "Übung: " + uebungsName +
                    "\nSets: " + training.arbeitsSets +
                    "\nWiederholungen: " + training.wiederholungen +
                    "\nMax. Gewicht: " + training.maxGewichtStr + " kg";
            listItems.add(listItem);
        }

        // Adapter für die ListView erstellen
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,  // Einfaches Layout für ListView
                listItems  // Die Liste der Trainingsdaten
        );

        // Setze den Adapter für die ListView
        listViewTrainingsData.setAdapter(adapter);
    }
}
