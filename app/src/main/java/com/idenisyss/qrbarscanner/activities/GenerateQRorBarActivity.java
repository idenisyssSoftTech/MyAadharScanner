package com.idenisyss.qrbarscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.idenisyss.qrbarscanner.BuildConfig;
import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.qrbarscanner.databases.livedatamodel.ScannedLivedData;
import com.idenisyss.qrbarscanner.utilities.AppConstants;
import com.idenisyss.qrbarscanner.utilities.Validation;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public class GenerateQRorBarActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG_NAME = GenerateQRorBarActivity.class.getName();
    private ImageView qr_bar_imv;
    private TextView data_content_tv2;
    private Bitmap received_bitmap;
   private byte[] imageByteArray;
    private MaterialButton save_but, share_but;
    private String received_bitmap_type, received_string;
    private ScannedLivedData scannedLivedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qror_bar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        qr_bar_imv = findViewById(R.id.qr_bar_imv);
        data_content_tv2 = findViewById(R.id.data_content_tv2);
        save_but = findViewById(R.id.save_but);
        share_but = findViewById(R.id.share_but);

        // Retrieve the image byte array from the intent
        imageByteArray =getIntent().getByteArrayExtra(AppConstants.GENERATE);
        if (imageByteArray != null) {
            // Convert the byte array back to a Bitmap and set it in the ImageView
            received_bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            qr_bar_imv.setImageBitmap(received_bitmap);
        }
        received_bitmap_type = getIntent().getStringExtra(AppConstants.GENERATE_CODE_TYPE);
        received_string = getIntent().getStringExtra(AppConstants.GENERATE_STRING);
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);

        data_content_tv2.setText(received_string);
        data_content_tv2.setMovementMethod(new ScrollingMovementMethod());
        save_but.setOnClickListener(this);
        share_but.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_but:

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATETIME_PATTERN, Locale.getDefault());
                // Format the current date and time as a string
                String formattedDate = dateFormat.format(currentDate);
                ScannedHistory hm = new ScannedHistory();
                hm.codetype = received_bitmap_type;
                hm.timedate = formattedDate;
                hm.data = received_string;
                hm.image = imageByteArray;
                scannedLivedData.insert(hm);
                finish();
                break;

            case R.id.share_but:

                Log.d(TAG_NAME,"krishnaStart");
                // Save the Bitmap to a temporary file
                File tempFile = Validation.saveBitmapToFile(this,received_bitmap);
                // Get a content URI for the temporary file using FileProvider
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, tempFile);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, contentUri); // Attach the content URI
//                intent.setPackage("com.whatsapp"); // Specify WhatsApp package to ensure it's opened in WhatsApp
                startActivity(Intent.createChooser(intent, "Share with"));

                break;
            // Add more cases for additional buttons if needed

            default:
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}