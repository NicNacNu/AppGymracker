package com.example.gymtracker.db;

import android.content.Context;

import com.example.gymtracker.TagUebungsCrossRef;
import com.example.gymtracker.WocheUebungCrossRef;
import com.example.gymtracker.tag;
import com.example.gymtracker.uebung;
import com.example.gymtracker.woche;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;
//Data Access Object = DAO
@Database(entities = {uebung.class, woche.class, WocheUebungCrossRef.class, tag.class, TagUebungsCrossRef.class}, version = 7,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public static final String NAME = "AppDatabase";
    public abstract uebungDAO getUebungDAO();
    public abstract WochenUebungsCorssRefDAO getWochenUebungsCorssRefDAO();
    public abstract wochenDAO getWochenDAO();
    public abstract tagDAO getTagDAO();
    public abstract TagUebungsCrossRefDAO getTagUebungsCrossRefDAO();

    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "gymtracker_db")
                            .addCallback(prepopulateCallback) // Hier wird der Callback hinzugefügt
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback prepopulateCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase database = getInstance(null); // Hol dir die Instanz
                wochenDAO wochenDAO = database.getWochenDAO();

                // Standard-Wochen einfügen
                woche montag = new woche("Montag", "");
                woche dienstag = new woche("Dienstag", "");
                woche mittwoch = new woche("Mittwoch","");
                woche donnerstag = new woche("Donnerstag","");
                woche freitag = new woche("Freitag","");
                woche samstag = new woche( "Samstag","");
                woche sontag = new woche("Sontag","");

                wochenDAO.addWoche(montag);
                wochenDAO.addWoche(dienstag);
                wochenDAO.addWoche(mittwoch);
                wochenDAO.addWoche(donnerstag);
                wochenDAO.addWoche(freitag);
                wochenDAO.addWoche(samstag);
                wochenDAO.addWoche(sontag);

            });
        }
    };
}