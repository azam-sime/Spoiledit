package com.spoiledit.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.StringUtils;

public abstract class RootFragment extends Fragment implements View.OnClickListener {

    private ContentLoadingProgressBar progressBar;
    private Snackbar snackbar;
    private boolean fragmentVisibleToUser = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi(view);
        initialiseListener(view);
        setData(view);

        setUpRecycler(view);
        setUpViewPager(view);

        addObservers();
    }

    public abstract void initUi(View view);

    public abstract void initialiseListener(View view);

    public abstract void setData(View view);

    public void setUpRecycler(View view) {

    }

    public void setUpViewPager(View view) {

    }

    public void addObservers() {

    }

    public boolean isRequestValid() {
        return true;
    }

    public void toggleViews(boolean enable) {

    }

    public void showLoader(String message) {
        if (StringUtils.isInvalid(message))
            showInterrupt(message, false);

        progressBar.show();
    }

    public void showKeyboard(EditText editText) {
        InputUtils.showKeyboard(getContext(), editText);
    }

    public void hideKeyboard() {
        InputUtils.hideKeyboard(getActivity());
    }

    public void runOnUiAfterMillis(Runnable runnable, long delayMillis) {
        ExecutorUtils.getInstance().executeOnMainDelayed(runnable, delayMillis);
    }

    public void showInterrupt(String message, boolean definite) {
        showInterrupt(message, definite, null, null);
    }

    public void showInterrupt(String message, boolean definite, String actionText, Runnable action) {
        if (snackbar == null) {
            snackbar = Snackbar.make(getView().findViewById(R.id.root), message,
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
        showInterrupt(message, true);
    }

    public void showWarning(String message) {
        showInterrupt(message, true);
    }

    public void showSuccess(String message, Runnable positive) {
        showInterrupt(message, true, "Proceed", positive);
    }

    public void hideLoader() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();

        if (progressBar != null && progressBar.isShown())
            progressBar.hide();
    }

    public void gotoNextScreen() {

    }

    public String getResString(int resId) {
        return AppUtils.getString(getContext(), resId);
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        fragmentVisibleToUser = menuVisible;
    }

    public boolean isFragmentVisibleToUser() {
        return fragmentVisibleToUser;
    }
}
