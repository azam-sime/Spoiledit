package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.models.UserModel;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.PreferenceUtils;

public abstract class RootDialogFragment extends DialogFragment {

    private UserModel userModel;
    private boolean isNetworkAvailable;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userModel = PreferenceUtils.getUserModel(getContext());

        initUi(view);
        initListener(view);

        setUpRecycler(view);
        setUpViewPager(view);

        addObservers();

        requestData();
        setData(view);
    }

    public abstract void initUi(View view);

    public abstract void initListener(View view);

    public void setUpRecycler(View view) {

    }

    public void setUpViewPager(View view) {

    }

    public void addObservers() {

    }

    public void requestData() {

    }

    public abstract void setData(View view);

    public void toggleClickViews(boolean enable) {

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

    private void showInterrupt(String message, boolean definite) {
        Snackbar.make(getView().findViewById(R.id.root), message,
                definite ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_INDEFINITE).show();
    }

    private void showFailure(String message) {

    }

    private void showSuccess(String message) {

    }

    public void onNetworkStateChanged(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
}
