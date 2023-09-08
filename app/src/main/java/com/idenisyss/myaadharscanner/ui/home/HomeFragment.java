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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.idenisyss.myaadharscanner.BuildConfig;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.activities.QRCodeScannerActivity;
import com.idenisyss.myaadharscanner.adapters.HomeviewsAdapter;
import com.idenisyss.myaadharscanner.utilities.PermissionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final String TAG_NAME = HomeFragment.class.getName();
    RecyclerView  home_recycler_view;
    GridLayoutManager gridLayoutManager;
    HomeviewsAdapter homeviewsAdapter;
    List<HomeModel> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        //Check Camera Permissions
        checkPermissionMethod();

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
              fab.setOnClickListener(view -> {
                  if(checkPermissionMethod()){
                      Intent i = new Intent(requireContext(), QRCodeScannerActivity.class);
                      startActivity(i);
                  }
              });

        list = new ArrayList<>();
        HomeModel hm1 = new HomeModel("1","Clip Board",R.drawable.baseline_copy_all_24,R.drawable.checklist);
        HomeModel hm2 = new HomeModel("2","Call",R.drawable.baseline_phone_enabled_24,R.drawable.telephone);
        HomeModel hm3 = new HomeModel("3","Facebook",R.drawable.facebook_black,R.drawable.facebook);
        HomeModel hm4 = new HomeModel("4","Message",R.drawable.baseline_chat_24,R.drawable.comments);
        HomeModel hm5 = new HomeModel("5","Mycard",R.drawable.id_card_black,R.drawable.id_card);
        HomeModel hm6 = new HomeModel("6","Email",R.drawable.baseline_mail_24,R.drawable.gmail);
        HomeModel hm7 = new HomeModel("7","Whatsapp",R.drawable.phone,R.drawable.whatsapp);
        HomeModel hm8 = new HomeModel("8","URL",R.drawable.link_black,R.drawable.link);
        HomeModel hm9 = new HomeModel("9","My Location",R.drawable.baseline_my_location_24,R.drawable.map);
        list.add(hm1);
        list.add(hm2);
        list.add(hm3);
        list.add(hm4);
        list.add(hm5);
        list.add(hm6);
        list.add(hm7);
        list.add(hm8);
        list.add(hm9);


        home_recycler_view = root.findViewById(R.id.home_recycler_view);

        gridLayoutManager = new GridLayoutManager(getActivity(),3, RecyclerView.VERTICAL,false);
        homeviewsAdapter = new HomeviewsAdapter(getActivity(),list);

        home_recycler_view.setLayoutManager(gridLayoutManager);
        home_recycler_view.setAdapter(homeviewsAdapter);

        return root;
    }

    private boolean checkPermissionMethod() {
        boolean isGranted = false;
        if(PermissionUtils.hasCameraPermission(getContext())){
            // If you have access to the external storage, do whatever you need
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // If you don't have access, launch a new activity to show the user the system's dialog
                    // to allow access to the external storage
                    isGranted = true;
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }

        }else {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ||
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)){
                showPermissionRationaleDialog();
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES});
                }else {
                    multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE});
                }
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
                allGranted = allGranted && Boolean.TRUE.equals(result.get(key));
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES});
                        }else {
                            multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE});
                        }
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
