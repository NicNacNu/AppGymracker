package com.example.gymtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Heutiges Datum
    static Calendar kalender = Calendar.getInstance();
    static Date datum = kalender.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    String formatiertesDatum = formatter.format(datum);

    SimpleDateFormat formatterWochentag = new SimpleDateFormat("EEEE", Locale.getDefault());
    String wochentag = formatterWochentag.format(datum);

    ListView listView;

   ActivityMainBinding binding;
   ListAdapter listAdapter;
   ListData listData;
   ArrayList<ListData> dataArrayList = new ArrayList<>();
    ArrayList<TrainingsDaten> trainingsDatenList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new heuteFragment());

        // Footer Menü
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Footer Menü Auswahl
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.Id_Heute)
            {
                replaceFragment(new heuteFragment());
            } else if (item.getItemId() == R.id.Id_Wochenansicht)
            {
                replaceFragment(new WocheFragment());
            } else if (item.getItemId() == R.id.ID_hinzufuegen)
            {
                replaceFragment(new HinzufuegenFragment());
            } else if (item.getItemId() == R.id.ID_mehr)
            {
                replaceFragment(new MehrFragment());
            }

            return true;
        });

        // Testdaten
        ArrayList<Trainingstag> trainingstage = DeafultTrainingstagListe.getTrainingstageListe();
        ArrayList<uebung> uebungenListe = UebungsTestDaten.getUebungenListe();



        // Heutiges Trainings Liste

            TrainingsDaten trainingsDaten = new TrainingsDaten(2,1,60,12,80,10,datum);
            trainingsDatenList.add(trainingsDaten);

            listData = new ListData(trainingsDatenList.get(0), trainingstage.get(2), uebungenListe.get(1));
            dataArrayList.add(listData);
            listAdapter = new ListAdapter(MainActivity.this,dataArrayList);


        listAdapter = new ListAdapter(MainActivity.this,dataArrayList );
        binding.ListTagView.setAdapter(listAdapter);
        binding.ListTagView.setClickable(true);


       binding.ListTagView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(MainActivity.this, DetatailedActivity.class);
               intent.putExtra("PR_Gewicht", uebungenListe.get(position).getPrGewicht());
               intent.putExtra("PR_Wiederholung", uebungenListe.get(position).getPrWiederholungen());
               intent.putExtra("ANAS",trainingsDatenList.get(position).getArbeitsSatzAnfangsGewicht());
               intent.putExtra("ANASW",trainingsDatenList.get(position).getArbeitsSatzAnfangsWiederholung());
               intent.putExtra("MXAS",trainingsDatenList.get(position).getArbeitsSatzMaxGewicht());
               intent.putExtra("MXASW",trainingsDatenList.get(position).getArbeitsSatzMaxWiederholung());
               startActivity(intent);
           }

       });
    }



    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,fragment);
        fragmentTransaction.commit();
    }




    // Testdaten
    public static class DeafultTrainingstagListe {

        public static ArrayList<Trainingstag> getTrainingstageListe() {
            ArrayList<Trainingstag> trainingstage = new ArrayList<>();

            // Trainingstag-Objekte für die Woche hinzufügen
            trainingstage.add(new Trainingstag(1, "Montag-Training", "Montag"));
            trainingstage.add(new Trainingstag(2, "Dienstag-Training", "Dienstag"));
            trainingstage.add(new Trainingstag(3, "Mittwoch-Training", "Mittwoch"));
            trainingstage.add(new Trainingstag(4, "Donnerstag-Training", "Donnerstag"));
            trainingstage.add(new Trainingstag(5, "Freitag-Training", "Freitag"));
            trainingstage.add(new Trainingstag(6, "Samstag-Training", "Samstag"));
            trainingstage.add(new Trainingstag(7, "Sonntag-Training", "Sonntag"));

            return trainingstage;
        }
    }



    public static class UebungsTestDaten {

        public static ArrayList<uebung> getUebungenListe() {
            ArrayList<uebung> uebungenListe = new ArrayList<>();

            // Beispielübungen hinzufügen
            uebungenListe.add(new uebung(1,"Bankdrücken", 4, 100, 8));
            uebungenListe.add(new uebung(2,"Kniebeugen", 4, 120, 6));
            uebungenListe.add(new uebung(3,"Kreuzheben", 3, 140, 5));
            uebungenListe.add(new uebung(4,"Schulterdrücken", 3, 60, 10));
            uebungenListe.add(new uebung(5,"Bizeps-Curls", 3, 15, 12));
            uebungenListe.add(new uebung(6,"Trizeps-Dips", 3, 20, 10));

            return uebungenListe;
        }
    }
}

