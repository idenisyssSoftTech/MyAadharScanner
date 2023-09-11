package com.idenisyss.myaadharscanner.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;

import java.util.List;

@Dao
public interface ScanHistoryDAO {

    @Insert
    void insert(ScannedHistory contact);

    @Update
    void update(ScannedHistory contact);

    @Delete
    void delete(ScannedHistory contact);

    @Query("SELECT * FROM scanneddata")
    LiveData<List<ScannedHistory>> getAllScannedHistory();

    @Query("DELETE FROM scanneddata WHERE uid = :id")
    void deleteById(int id);

}