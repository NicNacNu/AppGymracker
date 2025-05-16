package com.example.gymtracker;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymtracker.db.WochenUebungsCorssRefDAO;
import com.example.gymtracker.db.uebungDAO;
import com.example.gymtracker.db.wochenDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditDayFragment extends Fragment {

    private static final String ARG_DAY_NAME = "day_name";
    private String dayName;
    private List<uebung> uebungsList;
    private ListView listView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private uebungDAO uebungDAO;
    private WochenUebungsCorssRefDAO crossRefDAO;
    private int wochenID;

    wochenDAO wochenDAO = UebungMainApplication.getAppDatabase().getWochenDAO();

    public static EditDayFragment newInstance(String dayName) {
        EditDayFragment fragment = new EditDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY_NAME, dayName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayName = getArguments().getString(ARG_DAY_NAME);
        }

        uebungDAO = UebungMainApplication.getAppDatabase().getUebungDAO();
        crossRefDAO = UebungMainApplication.getAppDatabase().getWochenUebungsCorssRefDAO();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_day, container, false);

        TextView textViewDay = view.findViewById(R.id.textViewDay);
        listView = view.findViewById(R.id.uebungListView);
        textViewDay.setText(dayName);

        executorService.execute(() -> {
            wochenID = wochenDAO.getWocheID(dayName);

            loadUebungen();
        });

        return view;
    }

    private void loadUebungen() {
        executorService.execute(() -> {
            List<uebung> trainings = uebungDAO.getAllUebungen2();

            requireActivity().runOnUiThread(() -> {
                if (trainings != null) {
                    UebungAdapter adapter = new UebungAdapter(requireContext(), trainings, wochenID);
                    listView.setAdapter(adapter);
                }
            });
        });
    }
}
