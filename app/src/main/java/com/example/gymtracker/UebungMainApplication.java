package com.example.gymtracker;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.gymtracker.db.AppDatabase;

public class UebungMainApplication extends Application {
    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        // Stelle sicher, dass die Datenbank beim Start der Anwendung initialisiert wird
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "uebung_database")
                .fallbackToDestructiveMigration()  // Falls Migration nötig ist
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;  // Stelle sicher, dass appDatabase nicht null ist
    }
}
