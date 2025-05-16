package com.example.gymtracker;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "tag_uebung_cross_ref",
        primaryKeys = {"datum", "uebungID"},
        foreignKeys = {
                @ForeignKey(entity = tag.class,
                        parentColumns = "datum",
                        childColumns = "datum",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = uebung.class,
                        parentColumns = "uebungID",
                        childColumns = "uebungID",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("uebungID")}
)
public class TagUebungsCrossRef
{
    @ColumnInfo(index = true)
    @NonNull
    public String datum;
    public int uebungID;

    public int arbeitsSets;

    public int wiederholungen;

    public double maxGewichtStr;

    public TagUebungsCrossRef(@NonNull String datum, int uebungID, int arbeitsSets, int wiederholungen, double maxGewichtStr)
    {
        this.datum = datum;
        this.uebungID = uebungID;
        this.arbeitsSets = arbeitsSets;
        this.wiederholungen = wiederholungen;
        this.maxGewichtStr = maxGewichtStr;
    }
}
