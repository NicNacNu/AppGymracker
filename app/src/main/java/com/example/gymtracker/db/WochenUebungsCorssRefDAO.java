package com.example.gymtracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymtracker.WocheUebungCrossRef;

import java.util.List;
@Dao
public interface WochenUebungsCorssRefDAO
{

    @Query("SELECT * FROM woche_uebung_cross_ref")
    LiveData<List<WocheUebungCrossRef>> getAllCrossRef();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCrossRef(WocheUebungCrossRef wocheUebungCrossRef);

    @Query("SELECT u.uebungName FROM uebung_list u " +
            "JOIN woche_uebung_cross_ref wuv ON u.uebungID = wuv.uebungID " +
            "JOIN wochen_list w ON wuv.wocheID = w.wocheID " +
            "WHERE w.wochentag = :tag")
    List<String> getUebungenFuerTag(String tag);

    @Query("SELECT COUNT(*) FROM woche_uebung_cross_ref WHERE wocheID = :wocheID AND uebungID = :uebungID")
    boolean isUebungInWoche(int wocheID, int uebungID);

    @Query("DELETE FROM woche_uebung_cross_ref WHERE wocheID = :wocheID AND uebungID = :uebungID")
    void deleteCrossRef(int wocheID, int uebungID);

    @Query("SELECT u.uebungID FROM uebung_list u " +
            "JOIN woche_uebung_cross_ref wuv ON u.uebungID = wuv.uebungID " +
            "JOIN wochen_list w ON wuv.wocheID = w.wocheID " +
            "WHERE u.uebungName = :uebungsName")
    int getUebungsIDFuerTag(String uebungsName);


}
