package com.idenisyss.myaadharscanner.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.idenisyss.myaadharscanner.databases.daos.ScanHistoryDAO;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;

@Database(entities = {ScannedHistory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ScanHistoryDAO scanHistoryDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "myaadhaeapp-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
