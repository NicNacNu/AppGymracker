package com.example.gymtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymtracker.db.uebungDAO;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseSelectionFragment extends Fragment {

    private Spinner spinnerExercises;
    private Button buttonDeleteExercise;
    private uebungDAO exerciseDao;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ExerciseSelectionFragment() {
        // Leerer Konstruktor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_selection, container, false);

        exerciseDao = UebungMainApplication.getAppDatabase().getUebungDAO();
        spinnerExercises = view.findViewById(R.id.spinnerExercises);
        buttonDeleteExercise = view.findViewById(R.id.buttonDeleteExercise);

        // Spinner mit Übungsliste füllen
        loadExercises();

        buttonDeleteExercise.setOnClickListener(v -> deleteSelectedExercise());

        return view;
    }

    private HashMap<String, Integer> exerciseMap = new HashMap<>(); // Speichert Name → ID

    private void loadExercises() {
        executorService.execute(() -> {
            List<uebung> exercises = exerciseDao.getAllUebungen2();

            requireActivity().runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                exerciseMap.clear(); // Sicherstellen, dass die Map leer ist

                for (uebung exercise : exercises) {
                    adapter.add(exercise.getUebungName()); // Nur Name im Spinner anzeigen
                    exerciseMap.put(exercise.getUebungName(), exercise.getUebungID()); // Speichert Name → ID
                }

                spinnerExercises.setAdapter(adapter);
            });
        });
    }




    private void deleteSelectedExercise() {
        String selectedExerciseName = (String) spinnerExercises.getSelectedItem(); // Holt den Namen

        if (selectedExerciseName != null && exerciseMap.containsKey(selectedExerciseName)) {
            int selectedExerciseID = exerciseMap.get(selectedExerciseName); // Holt die ID

            executorService.execute(() -> {
                exerciseDao.deleteUebung(selectedExerciseID);

                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Übung '" + selectedExerciseName + "' wurde gelöscht!", Toast.LENGTH_SHORT).show();
                    loadExercises(); // Aktualisiert die Liste nach dem Löschen
                });
            });
        } else {
            Toast.makeText(getContext(), "Bitte eine Übung auswählen", Toast.LENGTH_SHORT).show();
        }
    }



}

