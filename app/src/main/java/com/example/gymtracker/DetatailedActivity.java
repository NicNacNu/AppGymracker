package com.example.gymtracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymtracker.databinding.ActivityDetatailedBinding;

public class DetatailedActivity extends AppCompatActivity {

    ActivityDetatailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetatailedBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = this.getIntent();
        if(intent != null){
            String PR_Gewicht = intent.getStringExtra("PR_Gewicht");
            String PR_Wiederholung = intent.getStringExtra("PR_Wiederholung");
            String ANAS = intent.getStringExtra("ANAS");
            String ANASW = intent.getStringExtra("ANASW");
            String MXAS = intent.getStringExtra("MXAS");
            String MXASW = intent.getStringExtra("MXASW");

            binding.detailedPR.setText(PR_Gewicht + "/" +  PR_Wiederholung);
            binding.detailedANAS.setText(ANAS);
            binding.detailedAFASGW.setText(ANASW);
            binding.detailedMXASG.setText(MXAS);
            binding.detailedMXASGW.setText(MXASW);

        }
    }
}