package com.spoiledit.utils;

import android.app.Activity;
import android.content.Context;

public final class DialogUtils {
    public static final String TAG = DialogUtils.class.getCanonicalName();

    private DialogUtils() {

    }

    public static void showFailure(Activity activity, int stringResId) {
        showFailure(activity, AppUtils.getString(activity, stringResId));
    }

    public static void showFailure(Activity activity, String message) {

    }

    public static void showSuccess(Activity activity, String message) {

    }

    public static void showWarning(Activity activity, String message) {

    }

    public static void showConfirm(Activity activity, String message) {

    }
}
