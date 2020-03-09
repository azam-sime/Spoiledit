package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;
import com.spoiledit.R;

public class SettingsFragment extends RootFragment {
    public static final String TAG = SettingsFragment.class.getCanonicalName();

    private RoundedImageView rivUser;
    private TextView tvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void initUi(View view) {
        rivUser = view.findViewById(R.id.riv_user);
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

    }

    @Override
    public void setUpViewPager(View view) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.v_edit_profile) {
            Toast.makeText(getContext(), "Implementing soon...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_my_account) {
            Toast.makeText(getContext(), "My account...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_my_spoilers) {
            Toast.makeText(getContext(), "My spoilers...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_my_watchlist) {
            Toast.makeText(getContext(), "My watchlist...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_change_password) {
            Toast.makeText(getContext(), "Change password...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_tc) {
            Toast.makeText(getContext(), "Terms and conditions...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_pp) {
            Toast.makeText(getContext(), "Privacy policy...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_about_us) {
            Toast.makeText(getContext(), "About us...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_faqs) {
            Toast.makeText(getContext(), "FAQ...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_contact_us) {
            Toast.makeText(getContext(), "Contact us...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.ll_logout) {
            Toast.makeText(getContext(), "Logout...", Toast.LENGTH_SHORT).show();
        }
    }
}
