package com.spoiledit.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.spoiledit.constants.Constants;
import com.spoiledit.listeners.OnNetworkStateChangeListener;
import com.spoiledit.utils.LogUtils;

public class NetworkStateReceiver extends BroadcastReceiver {
    public static final String TAG = NetworkStateReceiver.class.getCanonicalName();

    private OnNetworkStateChangeListener onNetworkStateChangeListener;

    public NetworkStateReceiver(OnNetworkStateChangeListener onNetworkStateChangeListener) {
        this.onNetworkStateChangeListener = onNetworkStateChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Constants.Broadcast.NETWORK_STATE_CHANGE)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean online = connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isConnected();

            LogUtils.logInfo(TAG, "onReceive: " + connectivityManager.getActiveNetworkInfo());

            if (onNetworkStateChangeListener != null)
                onNetworkStateChangeListener.onNetworkStateChanged(online);
        }
    }
}
