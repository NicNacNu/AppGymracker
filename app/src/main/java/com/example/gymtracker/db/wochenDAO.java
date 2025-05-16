package com.example.gymtracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gymtracker.woche;

import java.util.List;

@Dao
public interface wochenDAO
{

    @Query("SELECT * FROM wochen_list")
    LiveData<List<woche>> getAllWochen();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addWoche(woche Woche);

    @Query("DELETE FROM wochen_list WHERE wocheID = :wocheID")
    void deleteWoche(int wocheID);

    @Query("SELECT EXISTS(SELECT 1 FROM wochen_list WHERE wocheID = :id)")
    boolean exists(int id);

    @Query("SELECT wocheID FROM wochen_list WHERE wochenTag = :wochenTag")
    int getWocheID(String wochenTag);

}
