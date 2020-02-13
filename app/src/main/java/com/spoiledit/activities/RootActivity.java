package com.spoiledit.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.StringUtils;

abstract public class RootActivity extends AppCompatActivity implements View.OnClickListener {

    private ConnectivityManager connectivityManager;
    private ContentLoadingProgressBar progressBar;
    private Snackbar snackbar;

    private MutableLiveData<Boolean> networkMutable;

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            if (networkMutable != null)
                networkMutable.postValue(true);
            NetworkUtils.setNetworkAvailable(true);
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();

            if (networkMutable != null)
                networkMutable.postValue(false);
            NetworkUtils.setNetworkAvailable(false);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        progressBar = new ContentLoadingProgressBar(this);

        networkMutable = new MutableLiveData<>();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        progressBar = findViewById(R.id.pb_loading);

        setUpToolBar();

        initUi();
        initialiseListener();
        setData();

        setUpRecycler();
        setUpViewPager();

        addObservers();

        if (progressBar != null)
            progressBar.hide();
    }

    public abstract void setUpToolBar();

    public abstract void initUi();

    public abstract void initialiseListener();

    public abstract void setData();

    public void setUpRecycler() {

    }

    public void setUpViewPager() {

    }

    public void addObservers() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            onBackPressed();
        else if (v.getId() == R.id.iv_popcorn)
            showInterrupt("Will be soon implmented...", true);
    }

    public void setupToolBar(String title) {
        TextView tvToolbar = findViewById(R.id.tv_toolbar);
        tvToolbar.setText(title);

        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ImageView ivPopcorn = findViewById(R.id.iv_popcorn);
        ivPopcorn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .build(), networkCallback
        );
    }

    public MutableLiveData<Boolean> getNetworkMutable() {
        return networkMutable;
    }

    public boolean isRequestValid() {
        return true;
    }

    public void toggleViews(boolean enable) {

    }

    public void showLoader(String message) {
        if (StringUtils.isInvalid(message))
            showInterrupt(message, false);

        if (progressBar != null)
            progressBar.show();
    }

    public void showKeyboard(View view) {
        InputUtils.showKeyboard(view);
    }

    public void hideKeyboard(View view) {
        InputUtils.hideKeyboard(view);
    }

    public void runOnUiAfterMillis(Runnable runnable, long delayMillis) {
        ExecutorUtils.getInstance().executeOnMainDelayed(runnable, delayMillis);
    }


    public void showInterrupt(String message, boolean definite) {
        showInterrupt(message, definite, null, null);
    }

    public void showInterrupt(String message, boolean definite, String actionText, Runnable action) {
        if (snackbar == null) {
            snackbar = Snackbar.make(findViewById(R.id.root), message,
                    definite ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_INDEFINITE);
        } else {
            snackbar.setText(message);
            snackbar.setDuration(definite ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_INDEFINITE);
        }
        if (action != null) {
            snackbar.setAction(actionText, v -> action.run());
        }
        snackbar.show();
    }

    public void showFailure(String message) {
        showFailure(true, message);
    }

    public void showWarning(String message) {
        showWarning(true, message);
    }

    public void showSuccess(String message) {
        showSuccess(true, message);
    }

    public void showFailure(boolean soft, String message) {
        showFailure(soft, message, null);
    }

    public void showWarning(boolean soft, String message) {
        showWarning(soft, message, null);
    }

    public void showSuccess(boolean soft, String message) {
        showSuccess(soft, message, null);
    }

    public void showFailure(boolean soft, String message, Runnable action) {
        if (action != null) {
            if (soft)
                showInterrupt(message, true, "Okay", action);
            else
                DialogUtils.showFailure(this, message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showFailure(this, message);
        }
    }

    public void showWarning(boolean soft, String message, Runnable action) {
        if (action != null) {
            if (soft)
                showInterrupt(message, true, "Change", action);
            else
                DialogUtils.showWarning(this, message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showWarning(this, message);
        }
    }

    public void showSuccess(boolean soft, String message, Runnable action) {
        if (action != null) {
            if (soft)
                showInterrupt(message, true, "Proceed", action);
            else
                DialogUtils.showSuccess(this, message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showSuccess(this, message);
        }
    }


    public void hideLoader() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();

        if (progressBar != null && progressBar.isShown())
            progressBar.hide();
    }

    public void gotoNextScreen() {

    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    public String getResString(int resId) {
        return AppUtils.getString(this, resId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
