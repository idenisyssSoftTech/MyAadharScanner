package com.abhiram.qrbarscanner.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceSingleton {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferenceSingleton instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceSingleton(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPreferenceSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceSingleton(context);
        }
        return instance;
    }

    // Store a boolean value in shared preferences
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Retrieve a boolean value from shared preferences
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }
}
