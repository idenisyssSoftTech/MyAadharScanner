package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

public class GenerateQRorBarActivity extends AppCompatActivity {

    ImageView qr_bar_imv;
    TextView data_content_tv2;

    Button save_but,share_but;
    private static final String TAG_NAME = GenerateQRorBarActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qror_bar);
        Bitmap receivedBitmap  = getIntent().getParcelableExtra(AppConstants.GENERATE);
        Bitmap receivedBitmap_type  = getIntent().getParcelableExtra(AppConstants.GENERATE_CODE_TYPE);

        qr_bar_imv = findViewById(R.id.qr_bar_imv);
        data_content_tv2 = findViewById(R.id.data_content_tv2);
        save_but = findViewById(R.id.save_but);
        share_but = findViewById(R.id.share_but);

        qr_bar_imv.setImageBitmap(receivedBitmap);

    }
}