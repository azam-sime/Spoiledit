package com.spoiledit.activities;

import android.content.IntentFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.broadcasts.NetworkStateReceiver;
import com.spoiledit.constants.Constants;
import com.spoiledit.listeners.OnNetworkStateChangeListener;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;

abstract public class RootActivity extends AppCompatActivity implements View.OnClickListener,
        OnNetworkStateChangeListener {

    private NetworkStateReceiver networkStateReceiver;

    private boolean isNetworkAvailable = false;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setUpToolBar();

        initUi();
        initialiseListener();
        setData();

        setUpRecycler();
        setUpViewPager();

        networkStateReceiver = new NetworkStateReceiver(this);
        onNetworkStateChanged(isNetworkAvailable());
    }

    public abstract void setUpToolBar();

    public abstract void initUi();

    public abstract void initialiseListener();

    public abstract void setData();

    public void setUpRecycler() {

    }

    public void setUpViewPager() {

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
        registerReceiver(networkStateReceiver, new IntentFilter(Constants.Broadcast.NETWORK_STATE_CHANGE));
    }

    public void toggleClickViews(boolean enable) {

    }

    public void showKeyboard(EditText editText) {
        InputUtils.showKeyboard(this, editText);
    }

    public void hideKeyboard() {
        InputUtils.hideKeyboard(this);
    }

    public void runOnUiAfterMillis(Runnable runnable, long delayMillis) {
        ExecutorUtils.getInstance().executeOnMainDelayed(runnable, delayMillis);
    }

    private void showInterrupt(String message, boolean definite) {
        Snackbar.make(findViewById(R.id.root), message,
                definite ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_INDEFINITE).show();
    }

    private void showFailure(String message) {

    }

    private void showSuccess(String message) {

    }

    @Override
    public void onNetworkStateChanged(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkStateReceiver);
    }
}
