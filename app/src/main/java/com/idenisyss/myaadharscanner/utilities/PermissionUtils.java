package com.idenisyss.myaadharscanner.utilities;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import com.idenisyss.myaadharscanner.activities.QRScannerResult;

public class PermissionUtils {

    private static final String TAG_NAME = PermissionUtils.class.getName();
    public static boolean hasCameraPermission(Context context){
        return (checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static void showCustomDialog(Context context, String title, String message,
                                        String positiveBtn, DialogInterface.OnClickListener positiveListener,
                                        String negativeBtn, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveBtn,positiveListener)
                .setNegativeButton(negativeBtn,negativeListener);
        builder.create().show();
    }
}
