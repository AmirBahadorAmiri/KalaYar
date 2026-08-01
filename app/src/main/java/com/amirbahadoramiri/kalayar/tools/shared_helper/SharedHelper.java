package com.amirbahadoramiri.kalayar.tools.shared_helper;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *   Created by: @AmirBahadorAmiri
 *   Update at: 2026-08-01 10:15:00
 *   Github: https://github.com/AmirBahadorAmiri
 */

public class SharedHelper {

    private static volatile SharedHelper sharedHelper;
    private static final String SHARED_KEY = "kalayar";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor sharedPreferencesEditor;

    private SharedHelper(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public static SharedHelper getInstance(Context context) {
        if (sharedHelper == null) {
            synchronized (SharedHelper.class) {
                if (sharedHelper == null) {
                    sharedHelper = new SharedHelper(context);
                }
            }
        }
        return sharedHelper;
    }

    public String readString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int readInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public boolean readBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public long readLong(String key) {
        return sharedPreferences.getLong(key, -1L);
    }

    public void insert(String key, String value) {
        sharedPreferencesEditor.putString(key, value);
        sharedPreferencesEditor.apply();
    }

    public void insert(String key, boolean value) {
        sharedPreferencesEditor.putBoolean(key, value);
        sharedPreferencesEditor.apply();
    }

    public void insert(String key, long value) {
        sharedPreferencesEditor.putLong(key, value);
        sharedPreferencesEditor.apply();
    }

    public void insert(String key, int value) {
        sharedPreferencesEditor.putInt(key, value);
        sharedPreferencesEditor.apply();
    }
}
