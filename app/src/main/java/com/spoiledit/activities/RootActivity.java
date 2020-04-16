package com.spoiledit.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.models.UserModel;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;

abstract public class RootActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ConnectivityManager connectivityManager;

    private TextView tvToolbar;
    private ImageView ivBack, ivPopcorn;

    private TextView tvNoData;
    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            onAdapterDataChanged();
        }
    };

    private ContentLoadingProgressBar progressBar;
    private Snackbar snanckbarNetwork, snackbar;

    private MutableLiveData<Boolean> networkMutable;

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            if (networkMutable != null)
                networkMutable.postValue(true);

            if (snanckbarNetwork != null && snanckbarNetwork.isShown())
                snanckbarNetwork.dismiss();
            NetworkUtils.setNetworkAvailable(true);
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();

            if (networkMutable != null)
                networkMutable.postValue(false);

            snanckbarNetwork = Snackbar.make(findViewById(R.id.root),
                    "No internet connection!", Snackbar.LENGTH_INDEFINITE);
            snanckbarNetwork.show();
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

        tvToolbar = findViewById(R.id.tv_toolbar);
        ivBack = findViewById(R.id.iv_back);
        ivPopcorn = findViewById(R.id.iv_popcorn);

        progressBar = findViewById(R.id.pb_loading);
        tvNoData = findViewById(R.id.tv_error);

        setUpToolBar();

        initUi();
        initialiseListener();
        setUpRecycler();
        setUpViewPager();

        if (progressBar != null)
            progressBar.hide();

        addObservers();
        requestData();
        setData();
    }

    public abstract void setUpToolBar();

    public abstract void initUi();

    public abstract void initialiseListener();

    public void setUpRecycler() {

    }

    public void setUpViewPager() {

    }

    public void addObservers() {

    }

    public void requestData() {

    }

    public void setData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            onBackPressed();
        else if (v.getId() == R.id.iv_popcorn)
            onPopcornClick();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                        .build(), networkCallback
        );
    }

    public void setupToolBar(String title) {
        setupToolBar(title, false);
    }

    public void setupBackIconOnly() {
        setupToolBar(null, false);
    }

    public void setupToolBar(String title, boolean showPopcorn) {
        if (tvToolbar != null)
            tvToolbar.setText(title == null ? "" : title);

        if (ivPopcorn != null) {
            ivPopcorn.setVisibility(showPopcorn ? View.VISIBLE : View.INVISIBLE);
            ivPopcorn.setOnClickListener(showPopcorn ? this : null);
        }

        if (ivBack != null)
            ivBack.setOnClickListener(this);
    }

    public void onPopcornClick() {

    }

    public MutableLiveData<Boolean> getNetworkMutable() {
        return networkMutable;
    }

    public boolean isRequestValid() {
        return true;
    }

    public void toggleViews(boolean enable) {

    }

    public void showLoader() {
        showLoader(null);
    }

    public void showLoader(String message) {
        if (!StringUtils.isInvalid(message))
            showInterrupt(message, false);

        if (progressBar != null)
            progressBar.show();
    }

    public void showLoader(boolean showProgress, String message) {
        if (!StringUtils.isInvalid(message))
            showInterrupt(message, false);

        if (showProgress && progressBar != null)
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

    public void setError(String message) {
        if (tvNoData != null)
            tvNoData.setText(message);
    }

    void toggleTvNoData(boolean show) {
        if (tvNoData != null)
            ViewUtils.toggleViewVisibility(show, tvNoData);
    }

    RecyclerView.AdapterDataObserver getAdapterDataObserver() {
        return adapterDataObserver;
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    public String getResString(int resId) {
        return AppUtils.getString(this, resId);
    }

    public void onAdapterDataChanged() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
