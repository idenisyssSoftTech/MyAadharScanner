package com.idenisyss.myaadharscanner.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.idenisyss.myaadharscanner.R;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG_NAME = IntroActivity.class.getName();

    private int currentPage = 0;
   // private int[] introLayouts = { R.layout.activity_intro,R.layout.fragment_intro1, R.layout.fragment_intro2, /* Add more layouts */ };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(introLayouts[currentPage]);

        Button nextButton = findViewById(R.id.nextButton);
        Button skipButton = findViewById(R.id.skipButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (currentPage < introLayouts.length - 1) {
//                    Log.d("Main1", String.valueOf(currentPage));
//                    currentPage++;
//                    setContentView(introLayouts[currentPage]);
//                } else {
//                    Log.d("Main2", String.valueOf(currentPage));
//                    // Move to the main activity or next screen
//                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
//                    finish();
//                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the main activity or next screen
                startActivity(new Intent(IntroActivity.this, SettingActivity.class));
                finish();
            }
        });
    }
}