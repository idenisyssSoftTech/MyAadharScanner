package com.abhiram.qrbarscanner.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.abhiram.qrbarscanner.utilities.PermissionUtils;

public class GPSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())){
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager != null &&
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(isGPSEnabled){
                PermissionUtils.checkGPSEnabled(context);
            }
        }
    }
}
