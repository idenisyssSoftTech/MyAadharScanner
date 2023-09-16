package com.abhiram.qrbarscanner.databases.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;

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

    @Query("DELETE FROM scanneddata")
    void deleteAll();

    @Query("UPDATE scanneddata SET codetype = :codetype, data = :newdata, title = :newtitle,timedate = :newtimedate, images = :newimage WHERE uid = :entityId")
    void updateAllValuesbyUid(int entityId, String codetype, String newdata,String newtitle, String newtimedate,byte[] newimage);

}