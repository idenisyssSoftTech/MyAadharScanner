package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.databases.livedatamodel.ScannedLivedData;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.util.Date;
import java.util.Objects;

public class QRScannerResult extends AppCompatActivity {

    private static final String TAG_NAME = QRScannerResult.class.getName();

    private TextView QRResult;
    private Button BtnSave, BtnCancel;
    private String result,code_type;
    private ScannedLivedData scannedLivedData;


    @Override
    protected void onStart() {
        Log.d(TAG_NAME, AppConstants.ON_START);
        super.onStart();
    }


    @SuppressLint({"MissingInflatedId", "UseSupportActionBar"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_detail_screen);
        code_type = getIntent().getStringExtra(AppConstants.GENERATE_CODE_TYPE);
        result = getIntent().getStringExtra(AppConstants.RESULT);

        QRResult =  findViewById(R.id.QrResult);
        BtnSave =  findViewById(R.id.SaveBtn);
        BtnCancel =  findViewById(R.id.cancelBtn);
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);

        // toolbar Title
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add New");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QRResult.setText(result);

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result != null) {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    // Format the current date and time as a string
                    String formattedDate = dateFormat.format(currentDate);
                    ScannedHistory hm = new ScannedHistory();
                    hm.codetype = code_type;
                    hm.timedate = formattedDate;
                    hm.data = QRResult.getText().toString();
                    scannedLivedData.insert(hm);
                    QRResult.setText("");
                    finish();
                } else {
                    Toast.makeText(QRScannerResult.this, AppConstants.NO_DATA_FROM+AppConstants.QR_CODE, Toast.LENGTH_SHORT).show();
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