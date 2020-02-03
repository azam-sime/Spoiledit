package com.spoiledit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.spoiledit.BuildConfig;

public final class PreferenceUtils {
    public static final String TAG = PreferenceUtils.class.getCanonicalName();

    private static final String PREF_NAME = BuildConfig.APPLICATION_ID;

    public static final String KEY_USERNAME = PREF_NAME + ".username";
    public static final String KEY_PASSWORD = PREF_NAME + ".password";

    private PreferenceUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static void deleteString(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).apply();
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public static void saveInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static void clearPreferences(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }
}
