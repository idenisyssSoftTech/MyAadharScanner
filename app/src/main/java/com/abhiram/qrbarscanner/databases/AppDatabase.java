package com.abhiram.qrbarscanner.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhiram.qrbarscanner.databases.daos.ScanHistoryDAO;
import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ScannedHistory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static AppDatabase instance;

    public abstract ScanHistoryDAO scanHistoryDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "myaadharapp.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
