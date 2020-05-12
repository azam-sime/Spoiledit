package com.spoiledit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.spoiledit.R;
import com.spoiledit.activities.RootActivity;
import com.spoiledit.constants.Type;
import com.spoiledit.models.UserModel;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;

public abstract class RootFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ContentLoadingProgressBar progressBar;

    private TextView tvToolbar;
    private ImageView ivBack, ivPopcorn;

    private Snackbar snackbar;
    private boolean fragmentVisibleToUser = false;

    private TextView tvNoData;
    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            onAdapterDataChanged();
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvToolbar = view.findViewById(R.id.tv_toolbar);
        ivBack = view.findViewById(R.id.iv_back);
        ivPopcorn = view.findViewById(R.id.iv_popcorn);

        progressBar = view.findViewById(R.id.pb_loading);
        tvNoData = view.findViewById(R.id.tv_error);

        setUpToolbar();
        initUi(view);
        initialiseListener(view);

        setUpRecycler(view);
        setUpViewPager(view);

        if (progressBar != null)
            progressBar.hide();

        addObservers();

        requestData();
        setData(view);
    }

    public void setUpToolbar() {

    }

    public abstract void initUi(View view);

    public abstract void initialiseListener(View view);

    public void requestData() {

    }

    public void setData(View view) {

    }

    public void setUpRecycler(View view) {

    }

    public void setUpViewPager(View view) {

    }

    public void addObservers() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            getActivity().onBackPressed();
        else if (v.getId() == R.id.iv_popcorn)
            onPopcornClick();
    }

    @Override
    public void onRefresh() {

    }

    public void setupToolBar(String title) {
        setupToolBar(title, false);
    }

    public void setupBackIconOnly() {
        setupToolBar(null, false);
    }

    public void setupToolBar(String title, boolean showPopcorn) {
        if (tvToolbar != null)
            StringUtils.setText(tvToolbar, title);

        if (ivPopcorn != null) {
            ivPopcorn.setVisibility(showPopcorn ? View.VISIBLE : View.GONE);
            ivPopcorn.setOnClickListener(this);
        }

        if (ivBack != null)
            ivBack.setOnClickListener(this);
    }

    public void onPopcornClick() {
        ((RootActivity) getActivity()).onPopcornClick();
    }

    public boolean isRequestValid() {
        return true;
    }

    public void toggleViews(boolean enable) {

    }

    public void showLoader() {
        showLoader(true, null);
    }

    public void showLoader(String message) {
        showLoader(true, message);
    }

    public void showLoader(boolean showProgress, String message) {
        if (!StringUtils.isInvalid(message))
            showInterrupt(message, false);

        if (showProgress && progressBar != null)
            progressBar.show();

//        if (rlLoading != null)
//            rlLoading.setBackgroundColor(getResources().getColor(R.color.colorBlack_alpha55));
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
        if (isFragmentVisibleToUser()) {
            if (snackbar == null) {
                snackbar = Snackbar.make(getActivity().findViewById(R.id.root), message,
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
        if (isFragmentVisibleToUser()) {
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
    }

    public void showWarning(boolean soft, String message, Runnable action) {
        if (isFragmentVisibleToUser()) {
            if (action != null) {
                if (soft)
                    showInterrupt(message, true, "Change", action);
                else
                    DialogUtils.showWarning(getActivity(), message, action);
            } else {
                if (soft)
                    showInterrupt(message, true);
                else
                    DialogUtils.showWarning(getActivity(), message);
            }
        }
    }

    public void showSuccess(boolean soft, String message, Runnable action) {
        if (isFragmentVisibleToUser()) {
            if (action != null) {
                if (soft)
                    showInterrupt(message, true, "Proceed", action);
                else
                    DialogUtils.showSuccess(getActivity(), message, action);
            } else {
                if (soft)
                    showInterrupt(message, true);
                else
                    DialogUtils.showSuccess(getActivity(), message);
            }
        }
    }

    public void showNotLoginError() {
        ((RootActivity) getActivity()).showNotLoginError();
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

    public String getResString(int resId) {
        return AppUtils.getString(getContext(), resId);
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    public boolean loggedIn() {
        return ((RootActivity) getActivity()).loggedIn();
    }

    public boolean shouldGoto() {
        if (loggedIn()) {
            return true;
        } else {
            DialogUtils.createNonCancelableDialog(getContext(), Type.Info.HEY,
                    "You're not logged in. Please login or register yourself to enjoy this feature.",
                    "Login", this::onPopcornClick, "Cancel", null);
            return false;
        }
    }

    public UserModel getUserModel() {
        return ((RootActivity) getActivity()).getUserModel();
    }

    public RecyclerView.AdapterDataObserver getAdapterDataObserver() {
        return adapterDataObserver;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        fragmentVisibleToUser = menuVisible;
    }

    public boolean isFragmentVisibleToUser() {
        return fragmentVisibleToUser;
    }

    public void onAdapterDataChanged() {

    }
}
