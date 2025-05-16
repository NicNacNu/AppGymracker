package com.example.gymtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gymtracker.db.uebungDAO;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HinzufuegenFragment extends Fragment {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EditText editTextWorkoutName;
    private EditText editTextPersonalRecord;
    private Button buttonSaveWorkout;

    uebungDAO exerciseDao = UebungMainApplication.getAppDatabase().getUebungDAO();

    public HinzufuegenFragment() {
        // Leerer Konstruktor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hinzufuegen, container, false);


        // Toolbar mit Button
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        ImageButton buttonOpenExerciseFragment = view.findViewById(R.id.buttonOpenExerciseFragment);
        buttonOpenExerciseFragment.setOnClickListener(v -> openExerciseFragment());

        editTextWorkoutName = view.findViewById(R.id.editTextWorkoutName);
        editTextPersonalRecord = view.findViewById(R.id.editTextPersonalRecord);
        buttonSaveWorkout = view.findViewById(R.id.buttonSaveWorkout);

        buttonSaveWorkout.setOnClickListener(v -> saveWorkout());

        return view;
    }

    private void saveWorkout() {
        String workoutName = editTextWorkoutName.getText().toString().trim();
        String personalRecord = editTextPersonalRecord.getText().toString().trim();

        if (workoutName.isEmpty() || personalRecord.isEmpty()) {
            Toast.makeText(getContext(), "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show();
            return;
        }

        // Erstelle eine neue Übung mit einem zufälligen ID
        String workoutId = UUID.randomUUID().toString();
        uebung newWorkout = new uebung(workoutName, Integer.parseInt(personalRecord));

        // Aufgaben im Hintergrund ausführen
        executorService.execute(new Runnable()
        {
            @Override
            public void run() {
                // Führe die Datenbankoperation im Hintergrund aus
                exerciseDao.addUebung(newWorkout);

                // Zeige Toast im UI-Thread
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Übung gespeichert! ID: " + workoutId, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    private void openExerciseFragment() {
        // Erstellt ein neues Fragment-Objekt
        ExerciseSelectionFragment exerciseSelectionFragment = new ExerciseSelectionFragment();

        // Öffnet das neue Fragment und ersetzt das aktuelle
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, exerciseSelectionFragment); // Stelle sicher, dass `fragment_container` das ID deines Fragment-Containers ist
        transaction.addToBackStack(null); // Ermöglicht das Zurückgehen mit der Zurück-Taste
        transaction.commit();
    }

}
