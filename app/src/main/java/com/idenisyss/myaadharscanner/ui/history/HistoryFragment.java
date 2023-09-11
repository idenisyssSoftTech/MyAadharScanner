package com.idenisyss.myaadharscanner.ui.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.adapters.HistoryAdapter;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.databases.livedatamodel.ScannedLivedData;

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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu); // Inflate your menu resource
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}