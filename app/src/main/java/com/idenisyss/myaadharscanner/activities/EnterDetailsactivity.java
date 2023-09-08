package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.common.BitMatrix;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.utilities.AppConstants;
import com.idenisyss.myaadharscanner.utilities.CodeGenerator;

public class EnterDetailsactivity extends AppCompatActivity {
    private static final String TAG_NAME = EnterDetailsactivity.class.getName();
    private ImageView home_imageView;
    EditText  data_content_tv;
    private Button create_qr_but,create_bar_code_but;
    private final int width = 300; // Width of the QR code image
    private final int height = 300; // Height of the QR code image
    private final int width2 = 450; // Width of the QR and Bar code image
    private final int height2 = 150; // Height of the Bar code image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_views_qrgenerator);
        String homeTitle = getIntent().getStringExtra(AppConstants.INTENT_KEY_HOME_TITLE);
        int drawableResourceId  = getIntent().getIntExtra(AppConstants.INTENT_KEY_HOME_IMAGE,0);
        getSupportActionBar().setTitle(homeTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        data_content_tv = findViewById(R.id.data_content_tv);
        home_imageView = findViewById(R.id.home_imageView);
        create_qr_but = findViewById(R.id.create_qr_but);
        create_bar_code_but = findViewById(R.id.create_bar_code_but);

        home_imageView.setImageResource(drawableResourceId);

        create_qr_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = data_content_tv.getText().toString();
                qrBarcodeString(s,AppConstants.QR_CODE);

            }
        });

        create_bar_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = data_content_tv.getText().toString();
                qrBarcodeString(s,AppConstants.BARCODE);

            }
        });
    }

    private  void  qrBarcodeString(String data_string,String codetype){
        Log.d(TAG_NAME,"method : qrBarcodeString");
        BitMatrix bitMatrix;
        if(data_string != null && !data_string.isEmpty()) {
            try {
                if(codetype.equals(AppConstants.QR_CODE)) {
                    bitMatrix = CodeGenerator.generateQRCode(data_string, width, height);
                }
                else {
                     bitMatrix = CodeGenerator.generateBarcode(data_string, width2, height2);
                }
                // Convert the BitMatrix to a Bitmap
                Bitmap qrBitmap = createBitmap(bitMatrix);

                Intent l = new Intent(getApplicationContext(),GenerateQRorBarActivity.class);
                l.putExtra(AppConstants.GENERATE,qrBitmap);
                l.putExtra(AppConstants.GENERATE_CODE_TYPE,codetype);
                l.putExtra(AppConstants.GENERATE_STRING,data_string);
                startActivity(l);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            Toast.makeText(getApplicationContext(), AppConstants.PLEASE_ENTER_TEXT, Toast.LENGTH_SHORT).show();

        }
    }

    // Converts a BitMatrix to a Bitmap
    private Bitmap createBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}