package com.idenisyss.qrbarscanner.activities;


import static com.idenisyss.qrbarscanner.utilities.CodeUtils.qrBarcodeString;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.utilities.AppConstants;
import com.idenisyss.qrbarscanner.utilities.Validation;

public class EnterDetailsactivity extends AppCompatActivity {
    private static final String TAG_NAME = EnterDetailsactivity.class.getName();
    private ImageView home_imageView;
    EditText data_content_tv, my_card_phone_no_tv, my_card_email_tv, my_card_address_tv;
    private Button create_qr_but, create_bar_code_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_views_qrgenerator);
        String homeTitle = getIntent().getStringExtra(AppConstants.INTENT_KEY_HOME_TITLE);
        int drawableResourceId = getIntent().getIntExtra(AppConstants.INTENT_KEY_HOME_IMAGE, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(homeTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        data_content_tv = findViewById(R.id.data_content_tv);
        my_card_phone_no_tv = findViewById(R.id.my_card_phone_no_tv);
        my_card_email_tv = findViewById(R.id.my_card_email_tv);
        my_card_address_tv = findViewById(R.id.my_card_address_tv);
        home_imageView = findViewById(R.id.home_imageView);
        create_qr_but = findViewById(R.id.create_qr_but);
        create_bar_code_but = findViewById(R.id.create_bar_code_but);
        home_imageView.setImageResource(drawableResourceId);

        switch (homeTitle) {

            case AppConstants.CLIPBOARD:
                data_content_tv.setHint("Enter copied Text");
                break;
            case AppConstants.CALL:
                data_content_tv.setHint("Phone number");
                data_content_tv.setMaxLines(1);
                data_content_tv.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case AppConstants.FACEBOOK:
                data_content_tv.setHint("FaceBook URL");
                data_content_tv.setMaxLines(5);

                break;
            case AppConstants.MESSAGE:
                data_content_tv.setHint("Enter message");
                data_content_tv.setMaxLines(100);

                break;
            case AppConstants.MYCARD:
                data_content_tv.setHint("Name");
                my_card_phone_no_tv.setVisibility(View.VISIBLE);
                my_card_email_tv.setVisibility(View.VISIBLE);
                my_card_address_tv.setVisibility(View.VISIBLE);
                create_bar_code_but.setVisibility(View.GONE);

                break;
            case AppConstants.EMAIL:
                data_content_tv.setHint("E-mail");
                data_content_tv.setMaxLines(1);

                break;
            case AppConstants.WHATSAPP:
                data_content_tv.setHint("Whatsapp number");
                data_content_tv.setMaxLines(5);

                break;
            case AppConstants.URL:
                data_content_tv.setHint("Url");

                break;
            default:
                //myloc
                data_content_tv.setHint("Current Location");
                break;
        }

        create_qr_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = data_content_tv.getText().toString();
                if (data.isEmpty()) {
                    data_content_tv.setError("please Enter a value!...");
                    data_content_tv.requestFocus();

                } else {

                    switch (homeTitle) {

                        case AppConstants.CLIPBOARD:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.CALL:
                            if (Validation.isValidPhoneNumber(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }

                            break;
                        case AppConstants.FACEBOOK:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.MESSAGE:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.MYCARD:
                            String myphoneno = my_card_phone_no_tv.getText().toString();
                            String myemail = my_card_email_tv.getText().toString();
                            String myadd = my_card_address_tv.getText().toString();
                            if (!myphoneno.isEmpty() && !myemail.isEmpty() && !myadd.isEmpty()) {
                                //checkPhonenumber & email is valid or not.
                                if (Validation.isValidPhoneNumber(myphoneno) && Validation.isValidEmail(myemail)) {
                                    String mycarddata = "Name : " + data + "\n Phone no : " + myphoneno + "\n Email : " + myemail + "\n Address : " + myadd;
                                    qrBarcodeString(getApplicationContext(), mycarddata, AppConstants.QR_CODE);
                                } else {
                                    if (!Validation.isValidEmail(myemail)) {
                                        my_card_email_tv.setError("Enter valid Email");
                                    }
                                    if (!Validation.isValidPhoneNumber(myphoneno)) {
                                        my_card_phone_no_tv.setError("Enter valid Phone no :");
                                    }
                                }

                            } else {
                                if (myphoneno.isEmpty()) {
                                    my_card_phone_no_tv.setError("Empty");
                                }
                                if (myemail.isEmpty()) {
                                    my_card_email_tv.setError("Empty");
                                }
                                if (myadd.isEmpty()) {
                                    my_card_address_tv.setError("Empty");
                                }
                            }
                            break;
                        case AppConstants.EMAIL:
                            if (Validation.isValidEmail(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }

                            break;
                        case AppConstants.WHATSAPP:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.URL:
                            if (Validation.isValidURL(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }
                            break;
                        default:
                            //mylocation
                            break;
                    }
                }
            }
        });

        create_bar_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = data_content_tv.getText().toString();
                if (!data.isEmpty()) {
                    switch (homeTitle) {

                        case AppConstants.CLIPBOARD:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            break;
                        case AppConstants.CALL:
                            if (Validation.isValidPhoneNumber(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            }

                            break;
                        case AppConstants.FACEBOOK:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            break;
                        case AppConstants.MESSAGE:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            break;
                        case AppConstants.MYCARD:

                            break;
                        case AppConstants.EMAIL:
                            if (Validation.isValidEmail(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            }

                            break;
                        case AppConstants.WHATSAPP:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            break;
                        case AppConstants.URL:
                            if (Validation.isValidURL(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            }
                            break;

                        default:
                            //mylocation
                            break;
                    }

                } else {
                    data_content_tv.setError("please Enter a value!...");
                    data_content_tv.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        data_content_tv.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}