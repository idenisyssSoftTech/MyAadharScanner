package com.abhiram.qrbarscanner.activities;

import static com.abhiram.qrbarscanner.utilities.CodeUtils.convertBitTobyte;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.Result;
import com.abhiram.qrbarscanner.R;
import com.abhiram.qrbarscanner.utilities.AppConstants;
import com.abhiram.qrbarscanner.utilities.CodeUtils;

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
    private TextView title, subTitle;
    private Vibrator vibrator;
    private boolean isCameraAvailable;
    private CameraManager cameraManager;
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

        initViews();
        // Initialize the camera manager
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        isCameraAvailable = checkCameraAvailability();
        if (isCameraAvailable) {
            // Camera is available, you can proceed with scanning
            startScanning();
        } else {
            // No camera available on this device
            Toast.makeText(this, "No camera available on this device!...", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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

        title = findViewById(R.id.Title);
        subTitle = findViewById(R.id.subTitle);

    }

    private boolean checkCameraAvailability() {
        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            return cameraIds != null && cameraIds.length > 0;
        } catch (CameraAccessException e) {
            // An error occurred while accessing the camera
            e.printStackTrace();
            // Handle this error
            return false;
        }
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
                title.setText(AppConstants.BarCodeScanner_TITLE);
                subTitle.setText(AppConstants.BarCodeScanner_subTITLE);
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
        title.setText(AppConstants.QRScanner_TITLE);
        subTitle.setText(AppConstants.QRScanner_subTITLE);

        // setUp QRScanner FrameStyle
        scannerView.setFrameCornersSize(60);
        scannerView.setFrameCornersRadius(20);
        scannerView.setFrameSize(0.65f);
        scannerView.setFrameAspectRatio(1f, 1f);
        scannerView.setFrameVerticalBias(0.3f);
        scannerView.setFrameThickness(8);

        mCodeScanner.setFormats(Arrays.asList(CodeUtils.QRformats));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    // Handle the scanned QR code data here
                    String scannedData = result.getText();
                    // Vibrate when scan is completed
                    vibrator.vibrate(100);
                    Bitmap scannedImage = CodeUtils.ScanqrBarCodeString(getApplicationContext(),scannedData,AppConstants.QR_CODE);

                    // Convert the Bitmap to a byte array
                    byte[] byteArray =convertBitTobyte(scannedImage);

                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra(AppConstants.RESULT, scannedData);
                    i.putExtra("image",byteArray);
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

        mCodeScanner.setFormats(Arrays.asList(CodeUtils.Barcodeformats));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    // Handle the scanned QR code data here
                    String scannedData = result.getText();
                    // Vibrate when scan is completed
                    vibrator.vibrate(100);
                    Bitmap scannedImage = CodeUtils.ScanqrBarCodeString(getApplicationContext(),scannedData,AppConstants.BARCODE);
                       //convert Bitmap to byte[]Array
                        byte[] byteArray = convertBitTobyte(scannedImage);

                        Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                        i.putExtra(AppConstants.RESULT, scannedData);
                        i.putExtra("image",byteArray);
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
                byte[] byteArray = convertBitTobyte(bitmap);

                // Attempt to decode the QR code
                String qrCodeData = CodeUtils.decodeQRCode(bitmap);
                // Attempt to decode the Barcode
                String barcodeData = CodeUtils.decodeBarcode(bitmap);

                if (qrCodeData != null) {
                    // Handle QR code data
                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra(AppConstants.RESULT, qrCodeData);
                    i.putExtra("image", byteArray);
                    i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.QR_CODE);
                    startActivity(i);
                    // Vibrate when scan is completed
                    vibrator.vibrate(100);
                    finish();
                } else if (barcodeData != null) {
                    // Vibrate when scan is completed
                    vibrator.vibrate(100);
                    // Handle barcode data
                    Intent i = new Intent(QRCodeScannerActivity.this, QRScannerResult.class);
                    i.putExtra(AppConstants.RESULT, barcodeData);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }
}