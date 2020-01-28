package com.spoiledit.utils;

import android.util.Log;

public final class LogUtils {
    private static final String TAG = LogUtils.class.getCanonicalName();
    private static final boolean SHOULD_LOG = true;

    private LogUtils() {
    }

    public static void d(String message) {
        if (SHOULD_LOG)
            Log.d(TAG, message);
    }

    public static void d(String TAG, String message) {
        if (SHOULD_LOG)
            Log.d(TAG, message);
    }

    public static void i(String message) {
        if (SHOULD_LOG)
            Log.i(TAG, message);
    }

    public static void i(String TAG, String message) {
        if (SHOULD_LOG)
            Log.i(TAG, message);
    }

    public static void e(String message) {
        if (SHOULD_LOG)
            Log.e(TAG, message);
    }

    public static void e(String TAG, String message) {
        if (SHOULD_LOG)
            Log.e(TAG, message);
    }

    public static void println(String message) {
        if (SHOULD_LOG)
            System.out.println(TAG + message);
    }

    public static void println(String TAG, String message) {
        if (SHOULD_LOG)
            System.out.println(TAG + message);
    }
}
