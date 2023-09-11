package com.idenisyss.myaadharscanner.ui.termscond;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idenisyss.myaadharscanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class TermsConditionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_terms_conditions, container, false);

        RecyclerView expandableRecyclerView = root.findViewById(R.id.terms_con_recycler);
        List<String> itemList = new ArrayList<>();
        itemList.add("hello");
        itemList.add("Soumya");
     /*   ExpandableListAdapter adapter = new ExpandableListAdapter();
        expandableRecyclerView.setAdapter(adapter);
        expandableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        // Inflate the layout for this fragment
        return root;
    }
}