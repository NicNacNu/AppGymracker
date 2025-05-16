package com.example.gymtracker.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.gymtracker.tag;


import java.util.List;

@Dao
public interface tagDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(tag tag);

    @Query("SELECT * FROM tage_table ORDER BY datum ASC")
    List<tag> getAllTage();

    @Query("SELECT * FROM tage_table WHERE datum = :datum LIMIT 1")
    tag getTagByDatum(String datum);

    @Query("DELETE FROM tage_table WHERE datum = :datum")
    void deleteTag(String datum);

    @Query("DELETE FROM tage_table")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM tage_table WHERE datum = :datum")
    int countTagByDatum(String datum);
}
