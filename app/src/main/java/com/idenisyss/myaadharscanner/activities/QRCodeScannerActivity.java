package com.idenisyss.myaadharscanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class QRCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_NAME = QRCodeScannerActivity.class.getName();
    private static final int PICK_IMAGE_REQUEST = 1;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private ImageButton cancelBtn;
    private FloatingActionButton Barcode, QRCode, gallery;
    private BarcodeFormat[] QRformats, Barcodeformats;
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

        QRformats = new BarcodeFormat[]{
                BarcodeFormat.QR_CODE,
                BarcodeFormat.DATA_MATRIX,
                BarcodeFormat.AZTEC,
                BarcodeFormat.MAXICODE,
                BarcodeFormat.PDF_417
        };

        Barcodeformats = new BarcodeFormat[]{
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

        initViews();

            startScanning();

    }

    private void initViews() {
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
                BarcodeLinear.setVisibility(View.GONE);
                QrLinear.setVisibility(View.VISIBLE);
                startBarcodeScanning();
                break;
            case R.id.QRScannerBtn:
                startScanning();
                break;
            case R.id.BtnchooseQR:
                openGallery();
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = null;
                if (selectedImageUri != null) {
                    inputStream = getContentResolver().openInputStream(selectedImageUri);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Convert the Bitmap to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // Attempt to decode the QR code
                String qrCodeData = decodeQRCode(bitmap);
                // Attempt to decode the Barcode
                String barcodeData = decodeBarcode(bitmap);

                if (qrCodeData != null) {
                    // Handle QR code data
                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra("result", qrCodeData);
                    i.putExtra("image", byteArray);
                    i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.QR_CODE);
                    startActivity(i);
                    finish();
                } else if (barcodeData != null) {
                    // Handle barcode data
                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra("result", barcodeData);
                    i.putExtra("image", byteArray);
                    i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.BARCODE);
                    startActivity(i);
                    finish();
                }else{
                    // No QR code or barcode found in the selected image
                    Toast.makeText(this, "No QR Code or Barcode found", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private String decodeQRCode(Bitmap bitmap) {
        try {
            int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
            // Copy pixel data from the Bitmap into the intArray
            bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap);

            for (BarcodeFormat format : QRformats) {
                if (result.getBarcodeFormat() == format) {
                    // Return the decoded QR code data
                    return result.getText();
                }
            }
                // The scanned code is not a QR code
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decodeBarcode(Bitmap bitmap) {
        try {
            int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
            // Copy pixel data from the Bitmap into the intArray
            bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap);

            for (BarcodeFormat format : Barcodeformats) {
                if (result.getBarcodeFormat() == format) {
                    // Return the decoded barcode data
                    return result.getText();
                }
            }
            // The scanned code is not a barcode
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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