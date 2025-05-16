package com.example.gymtracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tage_table")
public class tag {
    @PrimaryKey
    @NonNull
    private String datum;  // Format: "yyyy-MM-dd"

    public tag(@NonNull String datum) {
        this.datum = datum;
    }

    // Getter & Setter
    @NonNull
    public String getDatum() {
        return datum;
    }

    public void setDatum(@NonNull String datum) {
        this.datum = datum;
    }

}
