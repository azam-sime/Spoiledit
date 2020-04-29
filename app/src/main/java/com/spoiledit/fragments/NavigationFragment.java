package com.spoiledit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.activities.ChangePasswordActivity;
import com.spoiledit.activities.MyMoviesActivity;
import com.spoiledit.activities.MySpoilersActivity;
import com.spoiledit.activities.ProfileActivity;
import com.spoiledit.activities.ProfileEditActivity;
import com.spoiledit.activities.ProviderDetailsActivity;
import com.spoiledit.constants.App;
import com.spoiledit.models.UserModel;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DashboardViewModel;

public class NavigationFragment extends RootFragment {
    public static final String TAG = NavigationFragment.class.getCanonicalName();

    private DashboardViewModel dashboardViewModel;
    private TextView tvUserLabel, tvUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(getActivity()).get(DashboardViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void initUi(View view) {
        tvUserLabel = view.findViewById(R.id.tv_user_label);
        tvUserName = view.findViewById(R.id.tv_user_name);

        toggleUiElements();
    }

    @Override
    public void initialiseListener(View view) {
        view.findViewById(R.id.v_edit_profile).setOnClickListener(this);
        view.findViewById(R.id.tv_edit_profile).setOnClickListener(this);

        view.findViewById(R.id.ll_my_account).setOnClickListener(this);
        view.findViewById(R.id.ll_my_spoilers).setOnClickListener(this);
        view.findViewById(R.id.ll_my_watchlist).setOnClickListener(this);
        view.findViewById(R.id.ll_change_password).setOnClickListener(this);
        view.findViewById(R.id.ll_tc).setOnClickListener(this);
//        view.findViewById(R.id.ll_pp).setOnClickListener(this);
        view.findViewById(R.id.ll_cp).setOnClickListener(this);
        view.findViewById(R.id.ll_about_us).setOnClickListener(this);
        view.findViewById(R.id.ll_logout).setOnClickListener(this);
    }

    @Override
    public void setData(View view) {
        toggleUiData();
    }

    private void toggleUiElements() {
        View view = getView();
        if (view != null) {
            ViewUtils.toggleViewVisibility(loggedIn(),
                    tvUserName,

                    view.findViewById(R.id.ll_my_account),
                    view.findViewById(R.id.ll_my_spoilers),
                    view.findViewById(R.id.ll_my_watchlist),

                    view.findViewById(R.id.ll_change_password),
                    view.findViewById(R.id.ll_logout));
        }
    }

    private void toggleUiData() {
        if (loggedIn()) {
            UserModel userModel = getUserModel();

            tvUserLabel.setText(userModel.getDisplayName().substring(0, 1));
            tvUserName.setText(userModel.getDisplayName());
        }

        if (getView() != null)
            ((TextView) getView().findViewById(R.id.tv_edit_profile)).setText(loggedIn() ? "Edit Profile" : "Login");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == App.Intent.Request.LOGIN && resultCode == Activity.RESULT_OK) {
            toggleUiElements();
            toggleUiData();

        } else if (requestCode == App.Intent.Request.USER_PROFILE_EDIT && resultCode == Activity.RESULT_OK)
            setData(getView());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.v_edit_profile || v.getId() == R.id.tv_edit_profile) {
            if (loggedIn()) {
                getActivity().startActivityForResult(
                        new android.content.Intent(getContext(), ProfileEditActivity.class),
                        App.Intent.Request.USER_PROFILE_EDIT);
            } else
                onPopcornClick();

        } else if (v.getId() == R.id.ll_my_account) {
            getContext().startActivity(new android.content.Intent(getContext(), ProfileActivity.class));

        } else if (v.getId() == R.id.ll_my_spoilers) {
            getContext().startActivity(new android.content.Intent(getContext(), MySpoilersActivity.class));

        } else if (v.getId() == R.id.ll_my_watchlist) {
            getContext().startActivity(new android.content.Intent(getContext(), MyMoviesActivity.class));

        } else if (v.getId() == R.id.ll_change_password) {
            getContext().startActivity(new android.content.Intent(getContext(), ChangePasswordActivity.class));

        } else if (v.getId() == R.id.ll_tc) {
            android.content.Intent intent = new android.content.Intent(getContext(), ProviderDetailsActivity.class);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS, App.Intent.Value.FOR_TC);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        } /*else if (v.getId() == R.id.ll_pp) {
            android.content.Intent intent = new android.content.Intent(getContext(), ProviderDetailsActivity.class);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS, App.Intent.Value.FOR_PP);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        }*/ else if (v.getId() == R.id.ll_cp) {
            android.content.Intent intent = new android.content.Intent(getContext(), ProviderDetailsActivity.class);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS, App.Intent.Value.FOR_CP);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        } else if (v.getId() == R.id.ll_about_us) {
            android.content.Intent intent = new android.content.Intent(getContext(), ProviderDetailsActivity.class);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS, App.Intent.Value.FOR_AU);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        } else if (v.getId() == R.id.ll_logout) {
            dashboardViewModel.requestLogout();

        }
    }
}
