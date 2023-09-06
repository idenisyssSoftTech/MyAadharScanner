package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.ui.history.HistoryModel;

import java.util.Objects;

public class QRScanSuccessResult extends AppCompatActivity {

    TextView QRResult ;
    Button BtnSave, BtnCancel;
    ImageView QRCodeImage;
    String result;
    Bitmap qrCodeImageBit;


    @Override
    protected void onStart() {
        Log.d("DisplayAadarDetailsActivity","onstart");
        super.onStart();
    }


    @SuppressLint({"MissingInflatedId", "UseSupportActionBar"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_detail_screen);

        QRResult = (TextView) findViewById(R.id.QrResult);
        BtnSave = (Button) findViewById(R.id.SaveBtn);
        BtnCancel = (Button) findViewById(R.id.cancelBtn);
        QRCodeImage = (ImageView) findViewById(R.id.QRCodeImg);

        // toolbar Title
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add New");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        result = i.getStringExtra("result");
        qrCodeImageBit = i.getParcelableExtra("qrCodeImage");
        QRResult.setText(result);
        if (qrCodeImageBit != null) {
            QRCodeImage.setImageBitmap(qrCodeImageBit);
        }

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result != null){
                    HistoryModel hm = new HistoryModel();
                    hm.setData_content(QRResult.getText().toString());
                    QRResult.setText("");
                }

            }
        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) { // Back button in the toolbar
            onBackPressed();
            finish();// Handle the back button click event here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






}