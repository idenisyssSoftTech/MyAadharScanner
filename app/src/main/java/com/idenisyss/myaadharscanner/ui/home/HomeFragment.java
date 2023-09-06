package com.idenisyss.myaadharscanner.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.idenisyss.myaadharscanner.BuildConfig;
import com.idenisyss.myaadharscanner.QRCodeScanner;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.adapters.HomeviewsAdapter;
import com.idenisyss.myaadharscanner.utilities.PermissionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    RecyclerView  home_recycler_view;
    GridLayoutManager gridLayoutManager;
    HomeviewsAdapter homeviewsAdapter;
    List<String> list;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        //Check Camera Permissions
        checkPermissionMethod();

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
              fab.setOnClickListener(view -> {
                  if(checkPermissionMethod()){
                      Intent i = new Intent(requireContext(),QRCodeScanner.class);
                      startActivity(i);
                  }
              });

        list = new ArrayList<>();
        list.add("BarCode \n Data");
        list.add("QR code \n Data");
        list.add("Call");
        list.add("Message");

        home_recycler_view = root.findViewById(R.id.home_recycler_view);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        homeviewsAdapter = new HomeviewsAdapter(getActivity(),list);

        home_recycler_view.setLayoutManager(gridLayoutManager);
        home_recycler_view.setAdapter(homeviewsAdapter);

        return root;
    }

    private boolean checkPermissionMethod() {
        boolean isGranted = false;
        if(PermissionUtils.hasCameraPermission(getContext())){
            isGranted = true;
        }else {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ||
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                showPermissionRationaleDialog();
            }else{
                multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                            Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        }
        return isGranted;
    }

    private final ActivityResultLauncher<String[]> multiPermissionLancher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            Log.d("vfdcs", "vfc" + result);

            boolean allGranted = true;
            for (String key : result.keySet()) {
                allGranted = allGranted && result.get(key);
            }
            if (allGranted) {
            } else {
                showPermissionSettingsDialog();
            }

        }
    });



    private void showPermissionRationaleDialog() {
        PermissionUtils.showCustomDialog(getContext(), "Camera & File and media Permission",
                "This app needs the camera & File and media permission. Please allow the permission.",
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                        multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE});
                    }
                }, "Cancel",null);
    }


    private void showPermissionSettingsDialog() {
        PermissionUtils.showCustomDialog(getContext(), "Camera & File media Permission!..",
                "The app needs camera permission to function. Please allow this permission in the app settings. ",
                "Go To Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:"+ BuildConfig.APPLICATION_ID));
                        startActivity(intent);
                    }
                },
                "Cancel",null);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}





  /** check only one custom permissison
//    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//            new ActivityResultCallback<Boolean>() {
//                @Override
//                public void onActivityResult(Boolean isGranted) {
//                    if(isGranted){
//                        Log.d("Camera Permissions ","onActivityResult : granted");
//                    }else{
//                        if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
//                            showPermissionSettingsDialog();
//                        }
//                        Log.d("camera permissions ","showRequestPermission Launcher : "+shouldShowRequestPermissionRationale(Manifest.permission.CAMERA));
//                        Log.d("Camera Permissions ","onActivityResult : notGranted");
//                    }
//
//                }
//            });

   **/