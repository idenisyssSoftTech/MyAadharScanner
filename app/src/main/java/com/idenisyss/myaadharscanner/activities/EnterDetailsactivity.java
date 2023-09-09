package com.idenisyss.myaadharscanner.activities;

import static com.idenisyss.myaadharscanner.utilities.CodeGenerator.qrBarcodeString;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

public class EnterDetailsactivity extends AppCompatActivity {
    private static final String TAG_NAME = EnterDetailsactivity.class.getName();
    private ImageView home_imageView;
    EditText  data_content_tv;
    private Button create_qr_but,create_bar_code_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_views_qrgenerator);
        String homeTitle = getIntent().getStringExtra(AppConstants.INTENT_KEY_HOME_TITLE);
        int drawableResourceId  = getIntent().getIntExtra(AppConstants.INTENT_KEY_HOME_IMAGE,0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(homeTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        data_content_tv = findViewById(R.id.data_content_tv);
        home_imageView = findViewById(R.id.home_imageView);
        create_qr_but = findViewById(R.id.create_qr_but);
        create_bar_code_but = findViewById(R.id.create_bar_code_but);

        home_imageView.setImageResource(drawableResourceId);

        create_qr_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = data_content_tv.getText().toString();
                qrBarcodeString(getApplicationContext(),s,AppConstants.QR_CODE);

            }
        });

        create_bar_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = data_content_tv.getText().toString();
                qrBarcodeString(getApplicationContext(),s,AppConstants.BARCODE);

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}