package com.example.gymtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymtracker.db.WochenUebungsCorssRefDAO;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class heuteFragment extends Fragment {

    static Calendar kalender;
    SimpleDateFormat simpleDateFormat;
    private TextView textDateView;
    private TextView textWeekdayView;
    private TextView textNameView;

    private ListView listView;
    private List<String> workoutList;
    private ArrayAdapter<String> adapter;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    WochenUebungsCorssRefDAO wochenUebungsCorssRefDAO = UebungMainApplication.getAppDatabase().getWochenUebungsCorssRefDAO();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heute, container, false);

        textDateView = view.findViewById(R.id.textDateView);
        textWeekdayView = view.findViewById(R.id.textWeekdayView);
        textNameView = view.findViewById(R.id.textNameView);
        kalender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String aktuellesDatum = simpleDateFormat.format(kalender.getTime());
        textDateView.setText(aktuellesDatum);
        SimpleDateFormat wochentagFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String wochentag = convertWeekday(wochentagFormat.format(kalender.getTime()));
        textWeekdayView.setText(wochentag);


        listView = view.findViewById(R.id.heutListView);
        workoutList = new ArrayList<>();

        // Adapter mit leerer Liste erstellen
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, workoutList);
        listView.setAdapter(adapter);

        // Datenbankabfrage im Hintergrund starten
        executorService.execute(() -> {
            List<String> trainings = wochenUebungsCorssRefDAO.getUebungenFuerTag(wochentag);

            requireActivity().runOnUiThread(() -> {
                workoutList.clear();
                if (trainings != null) {
                    workoutList.addAll(trainings);
                }
                adapter.notifyDataSetChanged();
            });
        });

        listView.setOnItemClickListener((AdapterView<?> parent, View view1, int position, long id) -> {
            String selectedWorkout = workoutList.get(position);

            executorService.execute(() ->
            { // Hintergrund-Thread für die DB-Abfrage
                int uebungsID = wochenUebungsCorssRefDAO.getUebungsIDFuerTag(selectedWorkout);

                requireActivity().runOnUiThread(() -> {
                    replaceFragment(WorkoutDetailFragment.newInstance(uebungsID, selectedWorkout));
                });
            });
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public String  convertWeekday(String wochentag)
    {
        switch (wochentag)
        {
            case "Monday":
                wochentag = "Montag";
                break;
            case "Tuesday":
                wochentag = "Dienstag";
                break;
            case "Wednesday":
                wochentag = "Mittwoch";
                break;
            case "Thursday":
                wochentag = "Donnerstag";
                break;
            case "Friday":
                wochentag = "Freitag";
                break;
            case "Saturday":
                wochentag = "Samstag";
                break;
            case "Sunday":
                wochentag = "Sonntag";
                break;
            default:
                wochentag = "";
        }
        return wochentag;
    }

}
