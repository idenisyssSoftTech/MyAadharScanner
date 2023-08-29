package com.example.myaadharscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //use postDelayed method from handle class to display the screen for 5sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,AadhaarScannerActivity.class);
                startActivity(i);
                finish();
                /*
                *  boolean isFirstLaunch = /* Check if it's the first launch
                if (isFirstLaunch) {
                    startActivity(new Intent(this, IntroActivity.class));
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
                * */

            }
        },SPLASH_DURATION);
    }
}