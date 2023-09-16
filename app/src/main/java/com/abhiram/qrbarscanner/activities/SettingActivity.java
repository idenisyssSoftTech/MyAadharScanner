package com.abhiram.qrbarscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abhiram.qrbarscanner.R;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG_NAME = SettingActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}