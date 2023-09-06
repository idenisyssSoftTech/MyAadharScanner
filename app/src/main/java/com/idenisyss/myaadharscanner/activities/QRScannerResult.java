package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.databases.livedatamodel.ScannedLivedData;

import java.util.Objects;

public class QRScannerResult extends AppCompatActivity {

    private static final String TAG_NAME = QRScannerResult.class.getName();

    private TextView QRResult;
    private Button BtnSave, BtnCancel;
    private ImageView QRCodeImage;
    private String result;
    private Bitmap qrCodeImageBit;
    private ScannedLivedData scannedLivedData;


    @Override
    protected void onStart() {
        Log.d(TAG_NAME, "onstart");
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
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);

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
                if (result != null) {
                    Log.d(TAG_NAME, "start");
                    ScannedHistory hm = new ScannedHistory();
                    hm.codetype = "QR";
                    hm.data = QRResult.getText().toString();
                    scannedLivedData.insert(hm);
                    Log.d(TAG_NAME, "completed");
                    QRResult.setText("");
                    finish();
                } else {
                    Toast.makeText(QRScannerResult.this, "No Data from QR code", Toast.LENGTH_SHORT).show();

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