package com.idenisyss.qrbarscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.qrbarscanner.databases.livedatamodel.ScannedLivedData;
import com.idenisyss.qrbarscanner.utilities.AppConstants;
import com.idenisyss.qrbarscanner.utilities.Validation;

import java.io.File;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class QRScannerResult extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_NAME = QRScannerResult.class.getName();

    private TextView QRResult,titleQRResult;
    private Button BtnSave, BtnCancel;
    private MaterialButton share_btn;
    private String result,code_type;
    private ImageView QRCodeImage;
    Bitmap imageBitmap;
    byte[] imageByteArray;
    private ScannedLivedData scannedLivedData;
    private  boolean is_history_page;
    LinearLayout linear;


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
        is_history_page = getIntent().getBooleanExtra(AppConstants.HISTORY_PAGE,false);

        QRResult =  findViewById(R.id.QrResult);
        titleQRResult =  findViewById(R.id.titleQRResult);

        BtnSave =  findViewById(R.id.SaveBtn);
        BtnSave.setOnClickListener(this);

        BtnCancel =  findViewById(R.id.cancelBtn);
        BtnCancel.setOnClickListener(this);

        share_btn = findViewById(R.id.history_share_but);
        share_btn.setOnClickListener(this);

        QRCodeImage = findViewById(R.id.QRCodeImg);
        linear = findViewById(R.id.linear);
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);
        if(is_history_page){
            linear.setVisibility(View.GONE);
            share_btn.setVisibility(View.VISIBLE);
        }
        else {
            linear.setVisibility(View.VISIBLE);
            share_btn.setVisibility(View.GONE);
        }

        // toolbar Title
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add New");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QRResult.setText(result);
        titleQRResult.setText(code_type);
        // Retrieve the image byte array from the intent
        imageByteArray =getIntent().getByteArrayExtra("image");
        if (imageByteArray != null) {
            // Convert the byte array back to a Bitmap and set it in the ImageView
            imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            QRCodeImage.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SaveBtn:
                saveQRImage();
                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.history_share_but:
                shareImage();
        }
    }

    private void shareImage() {
        Log.d(TAG_NAME,"krishnaStart");
        // Save the Bitmap to a temporary file
        File tempFile = Validation.saveBitmapToFile(this,imageBitmap);
        // Get a content URI for the temporary file using FileProvider
        Uri contentUri = FileProvider.getUriForFile(this, AppConstants.PACKAGE_NAME, tempFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, contentUri); // Attach the content URI
//                intent.setPackage("com.whatsapp"); // Specify WhatsApp package to ensure it's opened in WhatsApp
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private void saveQRImage() {
        if (result != null) {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            // Format the current date and time as a string
            String formattedDate = dateFormat.format(currentDate);
            ScannedHistory hm = new ScannedHistory();
            hm.codetype = code_type;
            hm.timedate = formattedDate;
            hm.data = QRResult.getText().toString();
            hm.image = imageByteArray;
            scannedLivedData.insert(hm);
            QRResult.setText("");
            finish();
        } else {
            Toast.makeText(QRScannerResult.this, AppConstants.NO_DATA_FROM+AppConstants.QR_CODE, Toast.LENGTH_SHORT).show();
        }
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