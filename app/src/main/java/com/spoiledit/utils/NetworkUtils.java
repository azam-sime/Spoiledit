package com.spoiledit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.spoiledit.R;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getCanonicalName();

    private NetworkUtils() {

    }

    public static boolean isNetworkAvailable(final Context context) {
        boolean status = false;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null)
                status = activeNetworkInfo.isAvailable();
        }
        return status;

    }

    public static boolean isNetworkAvailable(Context context, boolean showDialog) {
        boolean isNetworkAvailable = isNetworkAvailable(context);

        if (!isNetworkAvailable && showDialog) {
            DialogUtils.showFailure(context,
                    context.getResources().getString(R.string.no_internet_connection));
        }

        return isNetworkAvailable;
    }
}
