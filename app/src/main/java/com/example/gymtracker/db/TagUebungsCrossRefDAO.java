package com.example.gymtracker.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymtracker.TagUebungsCrossRef;

import java.util.List;

@Dao
public interface TagUebungsCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTagesUebungCrossRef(TagUebungsCrossRef tagUebungsCrossRef);

    @Query("SELECT COUNT(*) FROM tag_uebung_cross_ref WHERE datum = :datum AND uebungID = :uebungID")
    int countByDatumUndUebung(String datum, int uebungID);

    @Query("SELECT * FROM tag_uebung_cross_ref WHERE datum = :datum AND uebungID = :uebungID LIMIT 1")
    TagUebungsCrossRef getEintragByDatumUndUebung(String datum, int uebungID);
    @Update
    void updateTagesUebungCrossRef(TagUebungsCrossRef tagUebungsCrossRef);

    @Query("SELECT * FROM tag_uebung_cross_ref WHERE datum = :datum")
    List<TagUebungsCrossRef> getTrainingsDataForDate(String datum);


    @Query("SELECT u.uebungName FROM uebung_list u " +
            "JOIN tag_uebung_cross_ref tuv ON u.uebungID = tuv.uebungID " +
            "JOIN tage_table t ON tuv.datum = t.datum " +
            "WHERE tuv.uebungID = :uebungID")
    String getUebungNameById(int uebungID);
}
