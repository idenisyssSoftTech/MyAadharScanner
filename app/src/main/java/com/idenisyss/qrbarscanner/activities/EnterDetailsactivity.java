package com.idenisyss.qrbarscanner.activities;


import static com.idenisyss.qrbarscanner.utilities.CodeUtils.qrBarcodeString;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.idenisyss.qrbarscanner.BuildConfig;
import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.receivers.GPSReceiver;
import com.idenisyss.qrbarscanner.services.LocationServices;
import com.idenisyss.qrbarscanner.utilities.AppConstants;
import com.idenisyss.qrbarscanner.utilities.PermissionUtils;
import com.idenisyss.qrbarscanner.utilities.Validation;


public class EnterDetailsactivity extends AppCompatActivity {
    private static final String TAG_NAME = EnterDetailsactivity.class.getName();
    private ImageView home_imageView;
    private static final String ACTION_ADDRESS = "com.idenisyss.qrbarscanner.services.ACTION_ADDRESS";
    EditText data_content_tv, my_card_phone_no_tv, my_card_email_tv, my_card_address_tv;
    private Button create_qr_but, create_bar_code_but;
    private GPSReceiver gpsReceivers;
    private Intent serviceIntent;
    private String fullAddress, homeTitle;
    boolean isGPSEnabled = false;

    private final BroadcastReceiver addressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(ACTION_ADDRESS)) {
                fullAddress = intent.getStringExtra(AppConstants.MYLOCATION);
                updateUIWithAddress(fullAddress);
            }
        }
    };
    private void updateUIWithAddress(String fullAddress) {
        if(fullAddress != null) {
            if(homeTitle.equals(AppConstants.MYLOCATION)) {
                data_content_tv.setText(fullAddress);
            }
        }else {
            showToast("broadcast not found address!...");
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_views_qrgenerator);

        gpsReceivers = new GPSReceiver();
        registerReceiver(gpsReceivers, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        // Register the broadcast receiver
        registerReceiver(addressReceiver,new IntentFilter(ACTION_ADDRESS));
        // Start the LocationService
        serviceIntent = new Intent(EnterDetailsactivity.this, LocationServices.class);

        homeTitle = getIntent().getStringExtra(AppConstants.INTENT_KEY_HOME_TITLE);

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
                data_content_tv.setHint(getString(R.string.enter_clipboard_text));
                break;
            case AppConstants.CALL:
                data_content_tv.setHint(getString(R.string.enter_phoneNumber));
                data_content_tv.setMaxLines(1);
                data_content_tv.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case AppConstants.FACEBOOK:
                data_content_tv.setHint(getString(R.string.enter_facebookUrl));
                data_content_tv.setMaxLines(5);

                break;
            case AppConstants.MESSAGE:
                data_content_tv.setHint(getString(R.string.enter_message));
                data_content_tv.setMaxLines(100);

                break;
            case AppConstants.MYCARD:
                data_content_tv.setHint(getString(R.string.id_name));
                my_card_phone_no_tv.setVisibility(View.VISIBLE);
                my_card_phone_no_tv.setHint(getString(R.string.enter_phoneNumber));

                my_card_email_tv.setVisibility(View.VISIBLE);
                my_card_email_tv.setHint(getString(R.string.id_email));

                my_card_address_tv.setVisibility(View.VISIBLE);
                my_card_address_tv.setHint(getString(R.string.id_address));
                create_bar_code_but.setVisibility(View.GONE);

                break;
            case AppConstants.EMAIL:
                data_content_tv.setHint(getString(R.string.id_email));
                data_content_tv.setMaxLines(1);

                break;
            case AppConstants.WHATSAPP:
                data_content_tv.setHint(getString(R.string.enter_whatsappNo));
                data_content_tv.setMaxLines(5);

                break;
            case AppConstants.URL:
                data_content_tv.setHint(getString(R.string.enter_URL));

                break;
            case AppConstants.MYLOCATION:
                //myloc
                if(checkLocationPermissions()) {
                    startService(serviceIntent);
                    data_content_tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                    data_content_tv.setText(fullAddress);
                    Log.d(TAG_NAME, "full address main calss: " + fullAddress);
                    create_bar_code_but.setVisibility(View.GONE);
                }
                else{
                    Log.d(TAG_NAME,"Please check permissions");
                }

                break;
        }

        create_qr_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = data_content_tv.getText().toString();
                if (data.isEmpty()) {
                    showError(getString(R.string.enter_value));
                } else {

                    switch (homeTitle) {
                        case AppConstants.CLIPBOARD:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.CALL:
                            if (Validation.isValidPhoneNumber(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }else {
                                showError(getString(R.string.enter_validPhoneNo));
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
                                        my_card_email_tv.setError(getString(R.string.enter_validEmail));
                                        my_card_email_tv.requestFocus();
                                    }
                                    if (!Validation.isValidPhoneNumber(myphoneno)) {
                                        my_card_phone_no_tv.setError(getString(R.string.enter_validPhoneNo));
                                        my_card_phone_no_tv.requestFocus();
                                    }
                                }

                            } else {
                                if (myphoneno.isEmpty()) {
                                    my_card_phone_no_tv.setError(getString(R.string.empty_value));
                                    my_card_phone_no_tv.requestFocus();
                                }
                                if (myemail.isEmpty()) {
                                    my_card_email_tv.setError(getString(R.string.empty_value));
                                    my_card_email_tv.requestFocus();
                                }
                                if (myadd.isEmpty()) {
                                    my_card_address_tv.setError(getString(R.string.empty_value));
                                    my_card_address_tv.requestFocus();
                                }
                            }
                            break;
                        case AppConstants.EMAIL:
                            if (Validation.isValidEmail(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }else {
                                showError(getString(R.string.enter_validEmail));
                            }

                            break;
                        case AppConstants.WHATSAPP:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            break;
                        case AppConstants.URL:
                            if (Validation.isValidURL(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.QR_CODE);
                            }
                            else {
                                showError(getString(R.string.enter_validURL));
                            }
                            break;
                        default:
                            //mylocation
                            qrBarcodeString(getApplicationContext(),data,AppConstants.QR_CODE);
                            stopService(serviceIntent);
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
                            else{
                                showError(getString(R.string.enter_validEmail));
                            }

                            break;
                        case AppConstants.WHATSAPP:
                            qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            break;
                        case AppConstants.URL:
                            if (Validation.isValidURL(data)) {
                                qrBarcodeString(getApplicationContext(), data, AppConstants.BARCODE);
                            }  else {
                                showError(getString(R.string.enter_validURL));
                            }
                            break;

                        default:
                            //mylocation
                            break;
                    }

                } else {
                    showError(getString(R.string.enter_value));

                }
            }
        });
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private boolean checkLocationPermissions() {
        boolean isLocationPermissionGranted = PermissionUtils.hasLocationPermission(this);
        if (isLocationPermissionGranted) {
            PermissionUtils.checkGPSEnabled(this);
            startService(serviceIntent);
        } else {
            requestLocationPermissions();
        }
        return isLocationPermissionGranted;
    }

    private void requestLocationPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showPermissionRationaleDialog();
        } else {
            locationPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private final ActivityResultLauncher<String[]> locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        boolean allGranted = true;
        for (String key : result.keySet()) {
            allGranted = allGranted && Boolean.TRUE.equals(result.get(key));
        }
        if (allGranted) {
            PermissionUtils.checkGPSEnabled(EnterDetailsactivity.this);
            startLocationServiceAndSetAddress();
        } else {
            showPermissionSettingsDialog();
        }
    });

    private void showPermissionRationaleDialog() {
        PermissionUtils.showCustomDialog(EnterDetailsactivity.this, "Location Permission",
                "This app needs the location permission. Please allow the permission.",
                getString(R.string.OK), (dialog, which) -> locationPermissionLauncher.launch(new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}),
                getString(R.string.cancel), null);
    }

    private void showPermissionSettingsDialog() {
        PermissionUtils.showCustomDialog(EnterDetailsactivity.this, "Location Permissions!",
                "The app needs location permissions to function. Please allow this permission in the app settings.",
                "Go To Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                    startActivity(intent);
                }, getString(R.string.cancel), null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_CHECK_SETTING) {
            isGPSEnabled = resultCode == Activity.RESULT_OK;
            if (!isGPSEnabled) {
                if (homeTitle.equals(AppConstants.MYLOCATION)) {
                    data_content_tv.setText("");
                    showToast(getString(R.string.GPS_OFF));
                }
            }
            }else {
            startLocationServiceAndSetAddress();
            }
        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        data_content_tv.setError(message);
        data_content_tv.requestFocus();
    }


    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    protected void onStart() {
        super.onStart();
        if (homeTitle.equals(AppConstants.MYLOCATION)) {
            registerReceiver(gpsReceivers, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            // Register the broadcast receiver
            registerReceiver(addressReceiver,new IntentFilter(ACTION_ADDRESS));
            startService(serviceIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (homeTitle.equals(AppConstants.MYLOCATION)) {
            registerReceiver(gpsReceivers, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            startService(serviceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
        stopService(serviceIntent);
    }

    private void unregisterReceivers() {
        if (gpsReceivers != null) {
            unregisterReceiver(gpsReceivers);
            unregisterReceiver(addressReceiver);
        }
    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void startLocationServiceAndSetAddress() {
        startService(serviceIntent);
        registerReceiver(addressReceiver,new IntentFilter(ACTION_ADDRESS));
        if (fullAddress != null) {
            if (homeTitle.equals(AppConstants.MYLOCATION)) {
                data_content_tv.setText(fullAddress);
            }
        } else {
            showToast(getString(R.string.address_not_found));
        }
    }
}