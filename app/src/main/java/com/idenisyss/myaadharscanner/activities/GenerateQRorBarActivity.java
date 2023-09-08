package com.idenisyss.myaadharscanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.databases.livedatamodel.ScannedLivedData;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class GenerateQRorBarActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView qr_bar_imv;
    private TextView data_content_tv2;
    private Bitmap received_bitmap;
    private Button save_but, share_but;
    private String received_bitmap_type, received_string;
    private ScannedLivedData scannedLivedData;
    private static final String TAG_NAME = GenerateQRorBarActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qror_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        received_bitmap = getIntent().getParcelableExtra(AppConstants.GENERATE);
        received_bitmap_type = getIntent().getStringExtra(AppConstants.GENERATE_CODE_TYPE);
        received_string = getIntent().getStringExtra(AppConstants.GENERATE_STRING);
        scannedLivedData = new ViewModelProvider(this).get(ScannedLivedData.class);

        qr_bar_imv = findViewById(R.id.qr_bar_imv);
        data_content_tv2 = findViewById(R.id.data_content_tv2);
        save_but = findViewById(R.id.save_but);
        share_but = findViewById(R.id.share_but);

        data_content_tv2.setText(received_string);
        qr_bar_imv.setImageBitmap(received_bitmap);

        save_but.setOnClickListener(this);
        share_but.setOnClickListener(this);

    }

    // Save the Bitmap to a temporary file
    private File saveBitmapToFile(Bitmap bitmap) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp_image", ".jpg", getCacheDir());
            FileOutputStream outStream = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_but:

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                // Format the current date and time as a string
                String formattedDate = dateFormat.format(currentDate);
                ScannedHistory hm = new ScannedHistory();
                hm.codetype = received_bitmap_type;
                hm.timedate = formattedDate;
                hm.data = received_string;
                scannedLivedData.insert(hm);
                break;

            case R.id.share_but:
                finish();

               /* // Save the Bitmap to a temporary file
                File tempFile = saveBitmapToFile(received_bitmap);
                // Create an Intent to share the image to WhatsApp
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.setPackage("com.whatsapp"); // Set WhatsApp as the target app
                intent.putExtra(Intent.EXTRA_STREAM, tempFile); // Attach the image file
                // Start the sharing process
                startActivity(Intent.createChooser(intent, "Share with"));*/

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