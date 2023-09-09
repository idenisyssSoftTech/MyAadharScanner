package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.databases.livedatamodel.ScannedLivedData;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class QRScannerResult extends AppCompatActivity {

    private static final String TAG_NAME = QRScannerResult.class.getName();

    private TextView QRResult,titleQRResult;
    private Button BtnSave, BtnCancel;
    private String result,code_type;
    private ImageView QRCodeImage;
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
        BtnCancel =  findViewById(R.id.cancelBtn);
        QRCodeImage = findViewById(R.id.QRCodeImg);
        linear = findViewById(R.id.linear);
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);
        if(is_history_page){
            linear.setVisibility(View.GONE);
        }
        else {
            linear.setVisibility(View.VISIBLE);
        }

        // toolbar Title
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add New");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QRResult.setText(result);
        if(code_type.equals(AppConstants.QR_CODE)) {
           QRCodeImage.setImageResource(R.drawable.qr_black);
           titleQRResult.setText(getString(R.string.code_type_string,code_type));
        }
        else {
            QRCodeImage.setImageResource(R.drawable.barcode);
            titleQRResult.setText(getString(R.string.code_type_string,code_type));
        }


        // Retrieve the image byte array from the intent
        byte[] imageByteArray =getIntent().getByteArrayExtra("image");
        if (imageByteArray != null) {
            // Convert the byte array back to a Bitmap and set it in the ImageView
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            QRCodeImage.setImageBitmap(imageBitmap);
        }


        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result != null) {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
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

        BtnCancel.setOnClickListener(v-> finish());
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