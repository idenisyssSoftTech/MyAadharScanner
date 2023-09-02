package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.util.Log;

import com.idenisyss.myaadharscanner.R;

import java.util.LinkedList;
import java.util.List;

public class DisplayAadarDetailsActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        Log.d("DisplayAadarDetailsActivity","onstart");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_aadar_details);

    }
}