package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;

public abstract class RootDialogFragment extends DialogFragment {

    private boolean isNetworkAvailable;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi(view);
        initListener(view);
        setData(view);
    }

    public abstract void initUi(View view);

    public abstract void initListener(View view);

    public abstract void setData(View view);

    public void toggleClickViews(boolean enable) {

    }

    public void showKeyboard(EditText editText) {
        InputUtils.showKeyboard(getActivity(), editText);
    }

    public void hideKeyboard() {
        InputUtils.hideKeyboard(getActivity());
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
