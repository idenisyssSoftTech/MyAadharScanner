package com.idenisyss.qrbarscanner.ui.home;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.idenisyss.qrbarscanner.BuildConfig;
import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.activities.QRCodeScannerActivity;
import com.idenisyss.qrbarscanner.adapters.CardSliderAdapter;
import com.idenisyss.qrbarscanner.adapters.HomeviewsAdapter;
import com.idenisyss.qrbarscanner.ui.home.homemodels.CardImagesModel;
import com.idenisyss.qrbarscanner.utilities.AppConstants;
import com.idenisyss.qrbarscanner.utilities.PermissionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final String TAG_NAME = HomeFragment.class.getName();
    RecyclerView  home_recycler_view;
    GridLayoutManager gridLayoutManager;
    HomeviewsAdapter homeviewsAdapter;
    List<HomeModel> list;
    List<CardImagesModel> cardImagesModelList;
    private ViewPager viewPager2;
    private CardSliderAdapter cardSliderAdapter;
    private int currentPage = 0;
    private static final long AUTO_SLIDE_DELAY = 3000; // Auto slide delay in milliseconds
    private final Handler autoSlideHandler = new Handler(Looper.getMainLooper());
    private boolean isClicked = false;

    private final Runnable autoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            if (cardSliderAdapter != null) {
                if (currentPage == cardSliderAdapter.getCount() - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                viewPager2.setCurrentItem(currentPage, true);
                autoSlideHandler.postDelayed(this, AUTO_SLIDE_DELAY);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_home, container, false);
        //Check Camera Permissions
//        checkPermissionMethod();

        list = new ArrayList<>();
        HomeModel hm1 = new HomeModel("1", AppConstants.CLIPBOARD,R.drawable.baseline_copy_all_24,R.drawable.checklist);
        HomeModel hm2 = new HomeModel("2",AppConstants.CALL,R.drawable.baseline_phone_enabled_24,R.drawable.telephone);
        HomeModel hm3 = new HomeModel("3",AppConstants.FACEBOOK,R.drawable.facebook_black,R.drawable.facebook);
        HomeModel hm4 = new HomeModel("4",AppConstants.MESSAGE,R.drawable.baseline_chat_24,R.drawable.comments);
        HomeModel hm5 = new HomeModel("5",AppConstants.MYCARD,R.drawable.id_card_black,R.drawable.id_card);
        HomeModel hm6 = new HomeModel("6",AppConstants.EMAIL,R.drawable.baseline_mail_24,R.drawable.gmail);
        HomeModel hm7 = new HomeModel("7",AppConstants.WHATSAPP,R.drawable.phone,R.drawable.whatsapp);
        HomeModel hm8 = new HomeModel("8",AppConstants.URL,R.drawable.link_black,R.drawable.link);
        HomeModel hm9 = new HomeModel("9",AppConstants.MYLOCATION,R.drawable.baseline_my_location_24,R.drawable.map);
        list.add(hm1);
        list.add(hm2);
        list.add(hm3);
        list.add(hm4);
        list.add(hm5);
        list.add(hm6);
        list.add(hm7);
        list.add(hm8);
        list.add(hm9);

        cardImagesModelList = new ArrayList<>();
        CardImagesModel c1 = new CardImagesModel(R.drawable.slide0);
        CardImagesModel c2 = new CardImagesModel(R.drawable.slide1);
        CardImagesModel c3 = new CardImagesModel(R.drawable.slide2);
        CardImagesModel c4 = new CardImagesModel(R.drawable.slide3);
        CardImagesModel c5 = new CardImagesModel(R.drawable.slide4);
        CardImagesModel c6 = new CardImagesModel(R.drawable.slide5);
        cardImagesModelList.add(c1);
        cardImagesModelList.add(c2);
        cardImagesModelList.add(c3);
        cardImagesModelList.add(c4);
        cardImagesModelList.add(c5);
        cardImagesModelList.add(c6);


        home_recycler_view = root.findViewById(R.id.home_recycler_view);
        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
        viewPager2 = root.findViewById(R.id.viewPager2);
        cardSliderAdapter = new CardSliderAdapter(getActivity(),cardImagesModelList);
        viewPager2.setAdapter(cardSliderAdapter);

        // Start auto-sliding when the fragment is created
        autoSlideHandler.postDelayed(autoSlideRunnable, AUTO_SLIDE_DELAY);

        gridLayoutManager = new GridLayoutManager(getActivity(),3, RecyclerView.VERTICAL,false);
        homeviewsAdapter = new HomeviewsAdapter(getActivity(),list);

        home_recycler_view.setLayoutManager(gridLayoutManager);
        home_recycler_view.setAdapter(homeviewsAdapter);

        fab.setOnClickListener(view -> {
            isClicked = true;
            if(checkPermissionMethod()){
                Intent i = new Intent(requireContext(), QRCodeScannerActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

    private boolean checkPermissionMethod() {
        boolean isGranted = false;
        if(PermissionUtils.checkCameraPermission(getContext())){
            Log.d(TAG_NAME,"checkPermissionMethod");
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {

                // Check if the Android version is below Android 10 (API level 29)
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE); // Replace REQUEST_CODE with an appropriate request code
                } else {
                    // Permissions are already granted, you can proceed with file access
                    if (isClicked){
                        Intent i = new Intent(requireContext(), QRCodeScannerActivity.class);
                        startActivity(i);
                    }
                }
            } else {
                // Android 10 and above, file access permissions are handled differently
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
            }

        }else {
            Log.d(TAG_NAME,"checkPermissionMethod");
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
            boolean allGranted = true;
            for (String key : result.keySet()) {
                allGranted = allGranted && Boolean.TRUE.equals(result.get(key));
            }
            if (allGranted) {
                Log.d(TAG_NAME,"ALL Permissions granted");
            } else {
                showPermissionSettingsDialog();
                Log.d(TAG_NAME,"showPermissionSettingsDialog();\n");
            }

        }
    });


    private void showPermissionRationaleDialog() {
        PermissionUtils.showCustomDialog(getContext(), "Camera & File and media Permission",
                "This app needs the camera & File and media permission. Please allow the permission.",
                getString(R.string.OK), (dialog, which) -> {
//                        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES});
                    }else {
                        multiPermissionLancher.launch(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE});
                    }
                }, getString(R.string.cancel),null);
    }


    private void showPermissionSettingsDialog() {
        PermissionUtils.showCustomDialog(getContext(), "Camera & File media Permission!..",
                "The app needs camera permission to function. Please allow this permission in the app settings. ",
                "Go To Settings", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:"+ BuildConfig.APPLICATION_ID));
                    startActivity(intent);
                },
                getString(R.string.cancel),null);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoSlideHandler.removeCallbacks(autoSlideRunnable);
    }
}
