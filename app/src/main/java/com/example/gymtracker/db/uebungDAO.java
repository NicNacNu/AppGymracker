package com.example.gymtracker.db;



import com.example.gymtracker.uebung;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface uebungDAO
{
    @Query("SELECT * FROM uebung_list")
    LiveData<List<uebung>> getAllUebungen();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUebung(uebung Uebung);

    @Query("DELETE FROM uebung_list WHERE uebungID = :uebungID")
    void deleteUebung(int uebungID);

    @Query("SELECT * FROM uebung_list")
    List<uebung> getAllUebungen2();

    @Query("SELECT EXISTS(SELECT 1 FROM uebung_list WHERE uebungID = :id)")
    boolean exists(int id);

    @Query("SELECT uebungPR FROM uebung_list WHERE uebungID = :id")
    double getUebungPR(int id);

    @Query("UPDATE uebung_list SET uebungPR = :newPR WHERE uebungID = :id")
    void setUebungPR(int id, double newPR);

}
