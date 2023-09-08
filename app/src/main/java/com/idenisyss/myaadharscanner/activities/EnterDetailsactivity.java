package com.idenisyss.myaadharscanner.activities;

import static com.budiyev.android.codescanner.BarcodeUtils.createBitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private int width = 300; // Width of the QR code image
    private int height = 300; // Height of the QR code image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_views_qrgenerator);
        String homeTitle = getIntent().getStringExtra(AppConstants.HOMETITLE);
        int drawableResourceId  = getIntent().getIntExtra(AppConstants.HOMEIMAGE,0);
        getSupportActionBar().setTitle(homeTitle);

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
                qrBarcodeString(s,AppConstants.Barcode);

            }
        });
    }

    private  void  qrBarcodeString(String data_string,String codetype){
        if(data_string != null) {
            try {
                BitMatrix bitMatrix = CodeGenerator.generateQRCode(data_string, width, height);
                // Convert the BitMatrix to a Bitmap
                Bitmap qrBitmap = createBitmap(bitMatrix);

                Intent l = new Intent(getApplicationContext(),GenerateQRorBarActivity.class);
                l.putExtra(AppConstants.GENERATE,qrBitmap);
                l.putExtra(AppConstants.GENERATE_CODE_TYPE,codetype);
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
}