package com.example.gymtracker;

import androidx.room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


@Entity(tableName = "woche_uebung_cross_ref",
        primaryKeys = {"wocheID", "uebungID"},
        foreignKeys = {
                @ForeignKey(entity = woche.class,
                        parentColumns = "wocheID",
                        childColumns = "wocheID",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = uebung.class,
                        parentColumns = "uebungID",
                        childColumns = "uebungID",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("uebungID")} // Add this line
)
public class WocheUebungCrossRef {
    @ColumnInfo(index = true)
    public int wocheID;
    public int uebungID;

    public WocheUebungCrossRef(int wocheID, int uebungID) {
        this.wocheID = wocheID;
        this.uebungID = uebungID;
    }
}