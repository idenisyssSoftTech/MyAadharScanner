package com.abhiram.qrbarscanner.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.abhiram.qrbarscanner.R;

public class HelpFragment extends Fragment {


    private  RecyclerView help_recycler_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        help_recycler_view = root.findViewById(R.id.help_recycler_view);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}