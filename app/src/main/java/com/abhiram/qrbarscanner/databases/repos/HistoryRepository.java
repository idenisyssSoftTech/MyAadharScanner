package com.abhiram.qrbarscanner.databases.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.abhiram.qrbarscanner.databases.AppDatabase;
import com.abhiram.qrbarscanner.databases.daos.ScanHistoryDAO;
import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;

import java.util.List;

public class HistoryRepository {

   final private ScanHistoryDAO scanHistoryDAO;
   final private LiveData<List<ScannedHistory>> allhistorydata;

    public HistoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        scanHistoryDAO = db.scanHistoryDAO();
        allhistorydata = scanHistoryDAO.getAllScannedHistory();
    }

    public LiveData<List<ScannedHistory>> getAllhistorydata() {
        return allhistorydata;
    }

    public void insert(ScannedHistory scanhistory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scanHistoryDAO.insert(scanhistory);
        });
    }

    public void update(ScannedHistory scanhistory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scanHistoryDAO.update(scanhistory);
        });
    }

    public void delete(ScannedHistory scanhistory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scanHistoryDAO.delete(scanhistory);
        });
    }
    public void deleteById(int id) {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            scanHistoryDAO.deleteById(id);
//        });
        new DeleteAsyncTask(scanHistoryDAO).execute(id);
    }
    private static class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final ScanHistoryDAO yourDao;

        private DeleteAsyncTask(ScanHistoryDAO yourDao) {
            this.yourDao = yourDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            yourDao.deleteById(ids[0]);
            return null;
        }
    }
    public void deleteAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scanHistoryDAO.deleteAll();
            }
        }).start();
    }
}