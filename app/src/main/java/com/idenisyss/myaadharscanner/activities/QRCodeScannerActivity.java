package com.idenisyss.myaadharscanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;
import com.idenisyss.myaadharscanner.R;

import java.util.Objects;

public class QRCodeScannerActivity extends AppCompatActivity {

    private static final String TAG_NAME = QRCodeScannerActivity.class.getName();
    private final int REQUEST_CAMERA_PERMISSION = 123;
    private CodeScanner mCodeScanner;
    ImageButton cancelBtn;
    MaterialButton SelectGallery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_qrcode_scanner);

        SelectGallery = (MaterialButton) findViewById(R.id.chooseQR);

        cancelBtn = (ImageButton) findViewById(R.id.cancelQR);
        cancelBtn.setOnClickListener(view -> finish());

        if (ContextCompat.checkSelfPermission(QRCodeScannerActivity.this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(QRCodeScannerActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startScanning();
        }
    }

    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    // Handle the scanned QR code data here
                    String scannedData = result.getText();

                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra("result",scannedData);
                    startActivity(i);
                    finish();

                });
            }
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
//        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }
}