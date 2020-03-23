package com.spoiledit.fragments;


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
import com.spoiledit.models.UserModel;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ExecutorUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;

public abstract class RootFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private UserModel userModel;
    private ContentLoadingProgressBar progressBar;
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

        userModel = PreferenceUtils.getUserModel(getContext());
        progressBar = view.findViewById(R.id.pb_loading);

        tvNoData = view.findViewById(R.id.tv_error);

        setUpToolbar(view);
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

    public void setUpToolbar(View view) {

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
            showInterrupt("Will be soon implmented...", true);
    }

    @Override
    public void onRefresh() {

    }

    public void setupToolBar(View view, String title) {
        TextView tvToolbar = view.findViewById(R.id.tv_toolbar);
        tvToolbar.setText(title);

        ImageView ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ImageView ivPopcorn = view.findViewById(R.id.iv_popcorn);
        ivPopcorn.setOnClickListener(this);
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
                DialogUtils.showWarning(getActivity(), message, action);
        } else {
            if (soft)
                showInterrupt(message, true);
            else
                DialogUtils.showWarning(getActivity(), message);
        }
    }

    public void showSuccess(boolean soft, String message, Runnable action) {
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

    String getResString(int resId) {
        return AppUtils.getString(getContext(), resId);
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    UserModel getUserModel() {
        return userModel;
    }

    RecyclerView.AdapterDataObserver getAdapterDataObserver() {
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
