package com.idenisyss.qrbarscanner.ui.history;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.adapters.HistoryAdapter;
import com.idenisyss.qrbarscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.qrbarscanner.databases.livedatamodel.ScannedLivedData;

import java.util.List;

public class HistoryFragment extends Fragment {


    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    ScannedLivedData scannedLivedData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);
        historyAdapter = new HistoryAdapter();
        historyAdapter.setScannedLivedData(scannedLivedData);

        // Enable options menu in this fragment
        setHasOptionsMenu(true);
    }
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root  = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.history_recyelerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(historyAdapter);


        scannedLivedData.getAllScannedHistory().observe(getViewLifecycleOwner(), new Observer<List<ScannedHistory>>() {
            @Override
            public void onChanged(List<ScannedHistory> contacts) {
                historyAdapter.submitList(contacts);
                historyAdapter.getAppContext(getActivity());
            }
        });

        return root;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_deleteAll) {
            // Handle the "Delete All" menu item click here
            confirmDeleteAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteAllData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Deletion!...");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call the deleteAll method in your ViewModel to delete all data
                scannedLivedData.deleteAll();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu); // Inflate your menu resource
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}