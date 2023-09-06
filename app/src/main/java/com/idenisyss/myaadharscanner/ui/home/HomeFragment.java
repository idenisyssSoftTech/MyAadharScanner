package com.idenisyss.myaadharscanner.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idenisyss.myaadharscanner.QRCodeScanner;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.adapters.HomeviewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView  home_recycler_view;
    GridLayoutManager gridLayoutManager;
    HomeviewsAdapter homeviewsAdapter;
    List<String> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);
              fab.setOnClickListener(view -> {
//                Intent j = new Intent(getApplicationContext(), AadhaarScannerActivity.class);
//                startActivity(j);

                  Intent i = new Intent(requireContext(),QRCodeScanner.class);
                  startActivity(i);
              });
        list = new ArrayList<>();
        list.add("BarCode \n Data");
        list.add("QR code \n Data");
        list.add("Call");
        list.add("Message");

        home_recycler_view = root.findViewById(R.id.home_recycler_view);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        homeviewsAdapter = new HomeviewsAdapter(getActivity(),list);

        home_recycler_view.setLayoutManager(gridLayoutManager);
        home_recycler_view.setAdapter(homeviewsAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}