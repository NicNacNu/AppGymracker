package com.example.gymtracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wochen_list")
public class woche
{
    @PrimaryKey(autoGenerate = true)
    public int wocheID;

    public String wochenTag;
    public String traningsTagName;

    public woche()
    {
    }

    public woche(String wochenTag, String TraningsTagName)
    {
        this.wochenTag = wochenTag;
        this.traningsTagName = TraningsTagName;
    }
}
