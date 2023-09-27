package com.abhiram.qrbarscanner;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.abhiram.qrbarscanner.databinding.ActivityHomeBinding;
import com.abhiram.qrbarscanner.receivers.GPSReceiver;
import com.abhiram.qrbarscanner.utilities.AppConstants;
import com.abhiram.qrbarscanner.utilities.PermissionUtils;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG_NAME = HomeActivity.class.getName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private GPSReceiver gpsReceivers;
    boolean isGPSEnabled = false;
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gpsReceivers = new GPSReceiver();
        registerReceiver(gpsReceivers, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        

        // NightMode off
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        version = findViewById(R.id.BuildVersion);
        version.setText(BuildConfig.VERSION_NAME);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_contact_us,R.id.nav_about_us,R.id.nav_terms_conditions,R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        checkPermissionMethod();
    }

    private boolean checkPermissionMethod() {
        boolean isGranted = false;
        if(PermissionUtils.hasCameraPermission(this)){
            PermissionUtils.checkGPSEnabled(this);
            // If you have access to the external storage, do whatever you need
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // If you don't have access, launch a new activity to show the user the system's dialog
                    // to allow access to the external storage

                    isGranted = true;
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }

        }else {
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) ||
                    shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) ||
                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) ||
                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                showPermissionRationaleDialog();
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    multiPermissionLancher.launch(new String[] {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_MEDIA_IMAGES,
                            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION});
                }else {
                    multiPermissionLancher.launch(new String[] {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                }
            }
        }
        return isGranted;
    }

    private final ActivityResultLauncher<String[]> multiPermissionLancher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {

                boolean allGranted = true;
                for (String key : result.keySet()) {
                    allGranted = allGranted && Boolean.TRUE.equals(result.get(key));
                }
                if (allGranted) {
                    PermissionUtils.checkGPSEnabled(HomeActivity.this);
                    Log.d(TAG_NAME,"ALL Permissions granted");
                } else {
                    showPermissionSettingsDialog();
                }

            });
    private void showPermissionRationaleDialog() {
        PermissionUtils.showCustomDialog(this, "Camera and File & media and Location Permission",
                "This app needs the camera and File & media and Location permission. Please allow the permission.",
                getString(R.string.OK), (dialog, which) -> {
//                        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                    }else {
                        multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                    }
                }, getString(R.string.cancel),null);
    }


    private void showPermissionSettingsDialog() {
        PermissionUtils.showCustomDialog(this, "Camera & File media and Location Permission!..",
                "The app needs permission to function. Please allow this permission in the app settings. ",
                "Go To Settings", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:"+ BuildConfig.APPLICATION_ID));
                    startActivity(intent);
                },
                getString(R.string.cancel),null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_CHECK_SETTING) {
            isGPSEnabled = resultCode == Activity.RESULT_OK;
            if (!isGPSEnabled) {
                Log.d(TAG_NAME,"GPS OnActivityResult "+"Please Enable GPS!..");
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}