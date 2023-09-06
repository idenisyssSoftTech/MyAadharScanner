package com.idenisyss.myaadharscanner.ui.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.adapters.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {


    HistoryAdapter historyAdapter;
    List<HistoryModel> historyModelLists;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root  = inflater.inflate(R.layout.fragment_gallery, container, false);
        HistoryModel hm = new HistoryModel();
        historyModelLists = new ArrayList<>();
        HistoryModel h1 = new HistoryModel("1","QR code","02-09-2023",hm.getData_content());
        HistoryModel h2 = new HistoryModel("2","Bar code","03-08-2023","Hello djkdvjkndjsbdkkvjhofdivhd dvudfhvnjdfnvfdvj");
        HistoryModel h3 = new HistoryModel("3","code","02-08-2023","jfdbshjvjfdbvnmc vmcx");
        HistoryModel h4 = new HistoryModel("4","Bar code","22-07-2023","jfkdsnvkjfdnvlkdmvdzkvm kxz");
        HistoryModel h5 = new HistoryModel("5","QR code","06-07-2023","Hebjvdfjvkjfdzbvkjcnvz mcnkznllo");
        HistoryModel h6 = new HistoryModel("6","Bar code","02-07-2023","Hello");
        HistoryModel h7 = new HistoryModel("7","QR code","02-06-2023","Hello");
        HistoryModel h8 = new HistoryModel("8","Bar code","02-05-2023","Hejbvcjdnvkjfdnvkfdvfffduigtvjndlsajdpofjaoprifrkfireifopdsjfkdmvkcviodijfdjvgitrlkgnmrdfoivgjrdfllo");
        HistoryModel h9 = new HistoryModel("9","QR code","02-04-2023","jhnfndlskjeofwirpieu");
        HistoryModel h10 = new HistoryModel("10","Bar code","02-02-2023","Hello");
        HistoryModel h11 = new HistoryModel("11","QR code","02-12-2022","Hello");
        historyModelLists.add(h1);
        historyModelLists.add(h2);
        historyModelLists.add(h3);
        historyModelLists.add(h4);
        historyModelLists.add(h5);
        historyModelLists.add(h6);
        historyModelLists.add(h7);
        historyModelLists.add(h8);
        historyModelLists.add(h9);
        historyModelLists.add(h10);
        historyModelLists.add(h11);


        recyclerView = root.findViewById(R.id.history_recyelerview);
        historyAdapter = new HistoryAdapter(getActivity(),historyModelLists);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}