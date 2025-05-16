package com.example.gymtracker;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gymtracker.databinding.ActivityMainBinding;
import com.example.gymtracker.db.wochenDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    ListView listView;

   ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new heuteFragment());
        prepopulateDatabase();

        // Footer Menü
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        executorService.execute(() -> {


        // Footer Menü Auswahl
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            removeAllFragments();

            if(item.getItemId() == R.id.Id_Heute)
            {
                replaceFragment(new heuteFragment());
            } else if (item.getItemId() == R.id.Id_Wochenansicht)
            {
                replaceFragment(new TrainingsWocheFragment());
            } else if (item.getItemId() == R.id.ID_hinzufuegen)
            {
                replaceFragment(new HinzufuegenFragment());
            } else if (item.getItemId() == R.id.ID_mehr)
            {
                replaceFragment(new MehrFragment());
            }


            return true;
        });

    });

    }

    void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // Entfernt alle vorherigen Fragmente

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void prepopulateDatabase() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("first_run", true);

        if (isFirstRun) {
            wochenDAO wochenDAO = UebungMainApplication.getAppDatabase().getWochenDAO();
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                wochenDAO.addWoche(new woche("Montag", ""));
                wochenDAO.addWoche(new woche("Dienstag", ""));
                wochenDAO.addWoche(new woche("Mittwoch", ""));
                wochenDAO.addWoche(new woche("Donnerstag", ""));
                wochenDAO.addWoche(new woche("Freitag", ""));
                wochenDAO.addWoche(new woche("Samstag", ""));
                wochenDAO.addWoche(new woche("Sontag", ""));

                // Setze das Flag, damit es nicht erneut ausgeführt wird
                prefs.edit().putBoolean("first_run", false).apply();
            });
        }
    }

    public void removeAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager(); // für eine Activity, für Fragment ebenfalls möglich
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Alle Fragmente aus dem FragmentManager holen
        List<Fragment> fragments = fragmentManager.getFragments();

        // Alle Fragmente entfernen
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    transaction.remove(fragment);
                }
            }
        }

        // Alle Transaktionen committen
        transaction.commitAllowingStateLoss();
    }
}

