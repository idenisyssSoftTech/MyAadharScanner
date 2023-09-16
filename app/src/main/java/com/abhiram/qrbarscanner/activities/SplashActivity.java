package com.abhiram.qrbarscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.abhiram.qrbarscanner.HomeActivity;
import com.abhiram.qrbarscanner.R;
import com.abhiram.qrbarscanner.utilities.AppConstants;
import com.abhiram.qrbarscanner.utilities.SharedPreferenceSingleton;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG_NAME = SplashActivity.class.getName();
    private static final int SPLASH_DURATION = 3000;

    private TextView termsTextView,privacy_textview;
    private CheckBox termsCheckbox;
    private SharedPreferenceSingleton sharedPreferences;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        termsTextView = findViewById(R.id.termstextview);
        privacy_textview = findViewById(R.id.privacy_textview);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        sharedPreferences = SharedPreferenceSingleton.getInstance(this);
        // Retrieve the checkbox state from shared preferences and set it
        boolean isChecked = sharedPreferences.getBoolean(AppConstants.ISCHECKED_STATE, false);
        termsCheckbox.setChecked(isChecked);

        if (isChecked) {
            //use postDelayed method from handle class to display the screen for 5sec
          handler =  new Handler();
          handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_DURATION);
        }
        termsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a web browser with the URL (in this case, Google)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.TERMS_CONDITIONS_LINK));
                startActivity(intent);
            }
        });
        privacy_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a web browser with the URL (in this case, Google)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.PRIVACY_POLICY_LINK));
                startActivity(intent);
            }
        });

        // Set an OnCheckedChangeListener for the checkbox
        termsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.saveBoolean(AppConstants.ISCHECKED_STATE, isChecked);
                    // Checkbox is checked, redirect to AnotherActivity
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    sharedPreferences.saveBoolean(AppConstants.ISCHECKED_STATE, isChecked);
                    handler.removeCallbacksAndMessages(null);
                }
            }
        });


    }

}