package com.abhiram.qrbarscanner.databases.livedatamodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;
import com.abhiram.qrbarscanner.databases.repos.HistoryRepository;

import java.util.List;

public class ScannedLivedData extends AndroidViewModel {

    private HistoryRepository repository;
    private LiveData<List<ScannedHistory>> allhistory;

    public ScannedLivedData(Application application) {
        super(application);
        repository = new HistoryRepository(application);
        allhistory = repository.getAllhistorydata();
    }

    public void insert(ScannedHistory contact) {
        repository.insert(contact);
    }

    public void update(ScannedHistory contact) {
        repository.update(contact);
    }

    public void delete(ScannedHistory contact) {
        repository.delete(contact);
    }

    public LiveData<List<ScannedHistory>> getAllScannedHistory() {
        return allhistory;
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
