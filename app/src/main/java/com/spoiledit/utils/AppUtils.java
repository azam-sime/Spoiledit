package com.spoiledit.utils;

import android.content.Context;

import com.spoiledit.MyApplication;

public final class AppUtils {
    public static final String TAG = AppUtils.class.getCanonicalName();

    private AppUtils() {

    }

    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }
}
