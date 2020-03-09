package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.Status;
import com.spoiledit.fragments.ForgotPasswordFragment;
import com.spoiledit.repos.LoginRepo;
import com.spoiledit.repos.ProfileRepo;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.LoginViewModel;
import com.spoiledit.viewmodels.ProfileViewModel;

public class ProfileActivity extends RootActivity {
    public static final String TAG = ProfileActivity.class.getCanonicalName();

    private ProfileViewModel profileViewModel;

    private TextView tvUserName, tvUserEmail, tvUserPhone, tvUserDob, tvUserAddress;
    private ImageView ivUserPhoto, ivAddPhoto;
    private View vWishlist, vSpoilers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = ViewModelProviders.of(this,
                new ProfileViewModel.ProfileFactory(new ProfileRepo(this)))
                .get(ProfileViewModel.class);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar(null, false);
    }

    @Override
    public void initUi() {
        tvUserName = findViewById(R.id.tv_name);
        tvUserEmail = findViewById(R.id.tv_email_address);
        tvUserPhone = findViewById(R.id.tv_phone_number);
        tvUserDob = findViewById(R.id.tv_dob);
        tvUserAddress = findViewById(R.id.tv_address);

        ivUserPhoto = findViewById(R.id.riv_user);
        ivAddPhoto = findViewById(R.id.iv_add_photo);

        vWishlist = findViewById(R.id.ll_wishlist);
        vSpoilers = findViewById(R.id.ll_spoilers);
    }

    @Override
    public void initialiseListener() {
        vWishlist.setOnClickListener(this);
        vSpoilers.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void addObservers() {
        profileViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                toggleViews(false);
                showLoader(apiStatusModel.getMessage());

            } else if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                toggleViews(true);
                hideLoader();
                showFailure(false, apiStatusModel.getMessage());

            } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                toggleViews(false);
                hideLoader();

                showSuccess(false, apiStatusModel.getMessage(), this::gotoNextScreen);
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_wishlist) {

        } else if (v.getId() == R.id.ll_spoilers) {

        }
        super.onClick(v);
    }

    @Override
    public void gotoNextScreen() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
