package com.idenisyss.myaadharscanner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.adapters.HomeviewsAdapter;
import com.idenisyss.myaadharscanner.databinding.FragmentHomeBinding;

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

        list = new ArrayList<>();
        list.add("Aadhar"+"\nQr Scan");
        list.add("History");
        list.add("Call");
        list.add("Settings");

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