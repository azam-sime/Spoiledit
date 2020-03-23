package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spoiledit.R;
import com.spoiledit.activities.DashboardActivity;
import com.spoiledit.activities.ProfileActivity;
import com.spoiledit.activities.TCAndPPActivity;
import com.spoiledit.constants.App;

public class NavigationFragment extends RootFragment {
    public static final String TAG = NavigationFragment.class.getCanonicalName();

    private TextView tvUserLabel, tvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void initUi(View view) {
        tvUserLabel = view.findViewById(R.id.tv_user_label);
        tvUserName = view.findViewById(R.id.tv_user_name);
    }

    @Override
    public void initialiseListener(View view) {
        view.findViewById(R.id.v_edit_profile).setOnClickListener(this);

        view.findViewById(R.id.ll_my_account).setOnClickListener(this);
        view.findViewById(R.id.ll_my_spoilers).setOnClickListener(this);
        view.findViewById(R.id.ll_my_watchlist).setOnClickListener(this);
        view.findViewById(R.id.ll_change_password).setOnClickListener(this);
        view.findViewById(R.id.ll_tc).setOnClickListener(this);
        view.findViewById(R.id.ll_pp).setOnClickListener(this);
        view.findViewById(R.id.ll_about_us).setOnClickListener(this);
        view.findViewById(R.id.ll_faqs).setOnClickListener(this);
        view.findViewById(R.id.ll_contact_us).setOnClickListener(this);
        view.findViewById(R.id.ll_logout).setOnClickListener(this);
    }

    @Override
    public void setData(View view) {
        tvUserLabel.setText(getUserModel().getDisplayName().substring(0, 1));
        tvUserName.setText(getUserModel().getDisplayName());
    }

    @Override
    public void onClick(View v) {
        ((DashboardActivity) getActivity()).closeDrawer();

        if (v.getId() == R.id.v_edit_profile) {
            getContext().startActivity(new android.content.Intent(getContext(), ProfileActivity.class));

        } else if (v.getId() == R.id.ll_my_account) {


        } else if (v.getId() == R.id.ll_my_spoilers) {


        } else if (v.getId() == R.id.ll_my_watchlist) {


        } else if (v.getId() == R.id.ll_change_password) {


        } else if (v.getId() == R.id.ll_tc) {
            android.content.Intent intent = new android.content.Intent(getContext(), TCAndPPActivity.class);
            intent.putExtra(App.Intent.Extra.TC_AND_PP, App.Intent.Value.FOR_TC);
            intent.putExtra(App.Intent.Extra.TC_AND_PP_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        } else if (v.getId() == R.id.ll_pp) {
            android.content.Intent intent = new android.content.Intent(getContext(), TCAndPPActivity.class);
            intent.putExtra(App.Intent.Extra.TC_AND_PP, App.Intent.Value.FOR_PP);
            intent.putExtra(App.Intent.Extra.TC_AND_PP_DISPLAY_ONLY, true);
            getContext().startActivity(intent);

        } else if (v.getId() == R.id.ll_about_us) {


        } else if (v.getId() == R.id.ll_faqs) {


        } else if (v.getId() == R.id.ll_contact_us) {


        } else if (v.getId() == R.id.ll_logout) {


        }
    }
}
