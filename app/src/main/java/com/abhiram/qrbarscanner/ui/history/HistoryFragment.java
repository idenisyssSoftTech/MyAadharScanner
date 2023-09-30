package com.abhiram.qrbarscanner.ui.history;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abhiram.qrbarscanner.R;
import com.abhiram.qrbarscanner.adapters.HistoryAdapter;
import com.abhiram.qrbarscanner.databases.callbacksinterfaces.DeleteHistoryItemCallback;
import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;
import com.abhiram.qrbarscanner.databases.livedatamodel.ScannedLivedData;
import com.abhiram.qrbarscanner.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements DeleteHistoryItemCallback {

    private static final String TAG_NAME = HistoryFragment.class.getName();

    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    LinearLayout linear_history_no_data;
    ScannedLivedData scannedLivedData;
    List<ScannedHistory> contactsList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);
        historyAdapter = new HistoryAdapter();
        historyAdapter.setDeleteHistoryItemCallback(this);
        historyAdapter.setScannedLivedData(scannedLivedData);
        contactsList = new ArrayList<>();
        // Enable options menu in this fragment
        setHasOptionsMenu(true);
    }
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.history_recyelerview);
        linear_history_no_data = root.findViewById(R.id.linear_history_no_data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(historyAdapter);
        scannedLivedData.getAllScannedHistory().observe(getViewLifecycleOwner(), contacts -> {
            contactsList = contacts;
            historyAdapter.submitList(contacts);
            historyAdapter.getAppContext(getActivity());
            Log.d(TAG_NAME,String.valueOf( historyAdapter.getListSize(getActivity())));
            if(contacts.size() >0){
                recyclerView.setVisibility(View.VISIBLE);
                linear_history_no_data.setVisibility(View.GONE);
            }
            else {
                recyclerView.setVisibility(View.GONE);
                linear_history_no_data.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_deleteAll) {
            // Handle the "Delete All" menu item click here
            if(contactsList.size() >0){
                showConfirmDeleteDialog();
            }
            else {
                Toast.makeText(getActivity(), AppConstants.no_data_found, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(AppConstants.showDeleteTitle);
        builder.setMessage(AppConstants.showDeleteMessage);
        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            scannedLivedData.deleteAll();
            dialog.dismiss();
        });
        builder.setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu); // Inflate your menu resource
    }

    @Override
    public void onDeleteItem(ScannedHistory itemToDelete) {
        scannedLivedData.deleteById(itemToDelete.getId());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}