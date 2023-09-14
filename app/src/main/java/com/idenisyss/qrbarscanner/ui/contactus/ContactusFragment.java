package com.idenisyss.qrbarscanner.ui.contactus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idenisyss.qrbarscanner.BuildConfig;
import com.idenisyss.qrbarscanner.R;

/**

 * create an instance of this fragment.
 */
public class ContactusFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        // Inflate the layout for this fragment
        TextView version = root.findViewById(R.id.Cntversions);
        version.setText(BuildConfig.VERSION_NAME);
        return root;
    }
}