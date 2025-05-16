package com.example.gymtracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "uebung_list")
public class uebung
{

    @PrimaryKey(autoGenerate = true)
    public int uebungID;



    public String uebungName;
    public double uebungPR;


    public uebung(String uebungName, double uebungPR)
    {
        this.uebungName = uebungName;
        this.uebungPR = uebungPR;
    }
    public String getUebungName() {
        return uebungName;
    }

    public void setUebungName(String uebungName) {
        this.uebungName = uebungName;
    }

    public int getUebungID() {
        return uebungID;
    }

    public void setUebungID(int uebungID) {
        this.uebungID = uebungID;
    }

    @Override
    public String toString() {
        return this.uebungName;
    }

}
