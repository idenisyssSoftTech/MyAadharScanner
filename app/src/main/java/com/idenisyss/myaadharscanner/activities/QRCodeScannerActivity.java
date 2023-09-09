package com.idenisyss.myaadharscanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.util.Arrays;
import java.util.Objects;

public class QRCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_NAME = QRCodeScannerActivity.class.getName();
    private CodeScanner mCodeScanner;

    private CodeScannerView scannerView;
    private ImageButton cancelBtn;
    private FloatingActionButton Barcode, QRCode, gallery;

    private LinearLayout QrLinear, BarcodeLinear, galleryLinear;

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

        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        QrLinear = findViewById(R.id.QRcodeLinear);
        QrLinear.setVisibility(View.GONE);

        BarcodeLinear = findViewById(R.id.BarcodeLinear);
        BarcodeLinear.setVisibility(View.VISIBLE);

        galleryLinear = findViewById(R.id.GalleryLinear);
        galleryLinear.setVisibility(View.VISIBLE);

        QRCode = findViewById(R.id.QRScannerBtn);
        QRCode.setOnClickListener(this);

        Barcode = findViewById(R.id.barcodeBtn);
        Barcode.setOnClickListener(this);

        gallery = findViewById(R.id.BtnchooseQR);
        gallery.setOnClickListener(this);

        cancelBtn = findViewById(R.id.cancelScanner);
        cancelBtn.setOnClickListener(this);


        startScanning();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelScanner:
                finish();
                break;
            case R.id.barcodeBtn:
                galleryLinear.setVisibility(View.GONE);
                BarcodeLinear.setVisibility(View.GONE);
                QrLinear.setVisibility(View.VISIBLE);
                startBarcodeScanning();
                break;
            case R.id.QRScannerBtn:
                startScanning();
                break;
        }
    }


    private void startScanning() {
        int orangeResourceId = R.color.orange;
        Resources.Theme theme = getTheme();
        int color;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getResources().getColor(orangeResourceId, theme);
        } else {
            // If your app targets versions earlier than Android 6.0, use the deprecated method
            color = getResources().getColor(orangeResourceId);
        }

        scannerView.setFrameColor(color);
        BarcodeLinear.setVisibility(View.VISIBLE);
        QrLinear.setVisibility(View.GONE);
        galleryLinear.setVisibility(View.VISIBLE);
        BarcodeFormat[] QRformats = new BarcodeFormat[]{
                BarcodeFormat.QR_CODE,
                BarcodeFormat.DATA_MATRIX,
                BarcodeFormat.AZTEC,
                BarcodeFormat.MAXICODE,
                BarcodeFormat.PDF_417
        };

        // setUp QRScanner FrameStyle
        scannerView.setFrameCornersSize(60);
        scannerView.setFrameCornersRadius(20);
        scannerView.setFrameSize(0.65f);
        scannerView.setFrameAspectRatio(1f, 1f);
        scannerView.setFrameVerticalBias(0.3f);
        scannerView.setFrameThickness(8);

        mCodeScanner.setFormats(Arrays.asList(QRformats));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    // Handle the scanned QR code data here
                    String scannedData = result.getText();

                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra("result", scannedData);
                    i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.QR_CODE);
                    i.putExtra(AppConstants.HISTORY_PAGE,false);
                    startActivity(i);
                    finish();

                });
            }
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    private void startBarcodeScanning() {
        BarcodeFormat[] Barcodeformats = new BarcodeFormat[]{
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_39,
                BarcodeFormat.CODABAR,
                BarcodeFormat.EAN_8,
                BarcodeFormat.EAN_13,
                BarcodeFormat.ITF,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.UPC_EAN_EXTENSION};
        //setUp BarcodeScanner FrameStyle

        int yellowResourceId = R.color.yellow;
        Resources.Theme theme = getTheme();
        int color;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getResources().getColor(yellowResourceId, theme);
        } else {
            // If your app targets versions earlier than Android 6.0, use the deprecated method
            color = getResources().getColor(yellowResourceId);
        }

        scannerView.setFrameColor(color);
        scannerView.setFrameCornersSize(40);
        scannerView.setFrameCornersRadius(10);
        scannerView.setFrameSize(0.77f);
        // Set frame aspect ratio width and height
        scannerView.setFrameAspectRatio(1.6f, 1f);
        scannerView.setFrameThickness(5);
        scannerView.setFrameVerticalBias(0.4f);

        mCodeScanner.setFormats(Arrays.asList(Barcodeformats));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    // Handle the scanned QR code data here
                    String scannedData = result.getText();

                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra("result", scannedData);
                    i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.BARCODE);
                    startActivity(i);
                    finish();

                });
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
//        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }
}