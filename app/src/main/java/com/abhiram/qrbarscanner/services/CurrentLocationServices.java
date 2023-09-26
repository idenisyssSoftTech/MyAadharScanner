package com.abhiram.qrbarscanner.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.abhiram.qrbarscanner.utilities.AppConstants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationServices extends Service {
    private final String TAG_NAME = CurrentLocationServices.class.getName();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        fusedLocationProviderClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        startLocationUpdates(context);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startLocationUpdates(Context context) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Log.d(TAG_NAME,"krishna enter");
            if (task.isSuccessful() && task.getResult() != null) {
                Location location = task.getResult();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.d("getLocat Values is  :", latitude + longitude + "");

                // Get address using Geocoder
                getFullAddress(location);
            } else {

                // Handle the case where location retrieval was unsuccessful
                Exception exception = task.getException();
                if (exception != null) {
                    Log.e("Location Error", "Error: " + exception.getMessage());
                    Toast.makeText(CurrentLocationServices.this.context, "location retrieval exception"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Location Error", "Unknown error occurred");
                }

                Toast.makeText(context, "location retrieval exception", Toast.LENGTH_SHORT).show();
                // Handle the case where location retrieval was unsuccessful
            }
        });
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@Nullable LocationResult locationResult) {
            if (locationResult != null) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    getFullAddress(location);
                }
                else{
                    Toast.makeText(context, "location not available", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void getFullAddress(Location location) {
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);

            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);
                Log.d("LocationService", "full address service calss:" + fullAddress);

                // Send broadcast to MainActivity with the full address
                Intent intent = new Intent(AppConstants.ACTION_ADDRESS);
                intent.putExtra(AppConstants.MYLOCATION, fullAddress);
                sendBroadcast(intent);
//                Toast.makeText(LocationService.this, "Address : " + fullAddress, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }
}
