package com.abhiram.qrbarscanner.databases.callbacksinterfaces;

import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;

public interface DeleteHistoryItemCallback {
    void onDeleteItem(ScannedHistory entity);
}
