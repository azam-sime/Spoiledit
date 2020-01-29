package com.spoiledit.utils;

import android.util.Log;

public final class LogUtils {
    private static final String TAG = LogUtils.class.getCanonicalName();
    private static final boolean SHOULD_LOG = true;

    private LogUtils() {

    }

    public static void logInfo(String message) {
        if (SHOULD_LOG)
            Log.d(TAG, "logInfo: " + message);
    }

    public static void logInfo(String TAG, String message) {
        if (SHOULD_LOG)
            Log.d(TAG, "logInfo: " + message);
    }

    public static void logRequest(String message) {
        if (SHOULD_LOG)
            Log.i(TAG, "logRequest: " + message);
    }

    public static void logRequest(String TAG, String message) {
        if (SHOULD_LOG)
            Log.i(TAG, "logRequest: " + message);
    }

    public static void logResponse(String message) {
        if (SHOULD_LOG)
            Log.i(TAG, "logResponse: " + message);
    }

    public static void logResponse(String TAG, String message) {
        if (SHOULD_LOG)
            Log.i(TAG, "logResponse: " + message);
    }

    public static void logError(String message) {
        if (SHOULD_LOG)
            Log.e(TAG, "logError: " + message);
    }

    public static void logError(String TAG, String message) {
        if (SHOULD_LOG)
            Log.e(TAG, "logError: " + message);
    }

    public static void println(String message) {
        if (SHOULD_LOG)
            System.out.println(TAG + "println: " + message);
    }

    public static void println(String TAG, String message) {
        if (SHOULD_LOG)
            System.out.println(TAG + "println: " + message);
    }
}
