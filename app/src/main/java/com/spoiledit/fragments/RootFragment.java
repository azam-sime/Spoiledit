package com.spoiledit.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.DialogUtils;
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

        progressBar = view.findViewById(R.id.pb_loading);

        setUpToolbar(view);
        initUi(view);
        initialiseListener(view);
        setData(view);

        setUpRecycler(view);
        setUpViewPager(view);

        addObservers();

        if (progressBar != null)
            progressBar.hide();
    }

    public void setUpToolbar(View view) {

    }

    public abstract void initUi(View view);

    public abstract void initialiseListener(View view);

    public abstract void setData(View view);

    public void setUpRecycler(View view) {

    }

    public void setUpViewPager(View view) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            getActivity().onBackPressed();
        else if (v.getId() == R.id.iv_popcorn)
            showInterrupt("Will be soon implmented...", true);
    }

    public void setupToolBar(View view, String title) {
        TextView tvToolbar = view.findViewById(R.id.tv_toolbar);
        tvToolbar.setText(title);

        ImageView ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ImageView ivPopcorn = view.findViewById(R.id.iv_popcorn);
        ivPopcorn.setOnClickListener(this);
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
                DialogUtils.showFailure(getActivity(), message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showFailure(getActivity(), message);
        }
    }

    public void showWarning(boolean soft, String message, Runnable action) {
        if (action != null) {
            if (soft)
                showInterrupt(message, true, "Change", action);
            else
                DialogUtils.showFailure(getActivity(), message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showFailure(getActivity(), message);
        }
    }

    public void showSuccess(boolean soft, String message, Runnable action) {
        if (action != null) {
            if (soft)
                showInterrupt(message, true, "Proceed", action);
            else
                DialogUtils.showFailure(getActivity(), message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showFailure(getActivity(), message);
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
