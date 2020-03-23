package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.manager.FileManager;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.repos.ProfileRepo;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends RootActivity {
    public static final String TAG = ProfileActivity.class.getCanonicalName();

    private ProfileViewModel profileViewModel;
    private ProfileModel profileModel;

    private EditText etUserName, etUserEmail, etUserPhone;
    private TextView tvUserDob, tvUserAddress;
    private ImageView ivUserPhoto, ivAddPhoto;
    private View vWishlist, vSpoilers;

    private FileManager fileManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = ViewModelProviders.of(this,
                new ProfileViewModel.ProfileViewModelFactory(new ProfileRepo()))
                .get(ProfileViewModel.class);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void setUpToolBar() {
        setupBackIconOnly();
    }

    @Override
    public void initUi() {
        fileManager = new FileManager(this, com.spoiledit.constants.File.From.PROFILE);

        etUserName = findViewById(R.id.et_name);
        etUserEmail = findViewById(R.id.et_email_address);
        etUserPhone = findViewById(R.id.et_phone_number);

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
        ivAddPhoto.setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        profileViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.USER_PROFILE_GET
                    || apiStatusModel.getApi() == Constants.Api.USER_PROFILE_UPDATE
                    || apiStatusModel.getApi() == Constants.Api.USER_AVATAR_UPDATE) {
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

                    if (apiStatusModel.getApi() == Constants.Api.USER_AVATAR_UPDATE) {
                        PreferenceUtils.updateProfilePicPath(this, apiStatusModel.getMessage());
                        setData();
                    }
                }
            }
        });
    }

    @Override
    public void setData() {
        profileModel = ProfileModel.fromUserModel(PreferenceUtils.getUserModel(this));

        etUserName.setText(profileModel.getDisplayName());
        StringUtils.setText(etUserEmail, profileModel.getEmail(), "N/A");
        StringUtils.setText(etUserPhone, profileModel.getPhone(), "N/A");
        StringUtils.setText(tvUserDob, profileModel.getRegistered(), "N/A");
        StringUtils.setText(tvUserAddress, profileModel.getActivationKey(), "N/A");

        loadProfileImage(profileModel.getUrl());
    }

    private void loadProfileImage(String filePath) {
        try {
            Picasso.get()
                    .load(filePath)
                    .resize(180, 180)
                    .centerCrop()
                    .error(R.drawable.popcorn)
                    .into(ivUserPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_wishlist) {

        } else if (v.getId() == R.id.ll_spoilers) {

        } else if (v.getId() == R.id.iv_add_photo) {
            fileManager.showFileSources();

        } else if (v.getId() == R.id.btn_update) {
            if (isRequestValid()) {
                showLoader();
                profileViewModel.updateProfile(
                        etUserName.getText().toString().trim(),
                        etUserEmail.getText().toString().trim(),
                        etUserPhone.getText().toString().trim()
                );
            }
        }
        super.onClick(v);
    }

    private void updateProfilePic(String filePath) {
        LogUtils.logInfo(TAG, filePath);
        showLoader();
        profileViewModel.updateProfilePic(filePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == App.Usage.BROWSER) {
            fileManager.gotoFileBrowser();
        } else if (requestCode == App.Usage.GALLERY) {
            fileManager.gotoGallery();
        } else if (requestCode == App.Usage.CAMERA) {
            fileManager.gotoCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.SystemIntent.GALLERY) {
            if (resultCode == RESULT_OK) {
                updateProfilePic(fileManager.getFileDataFromIntent(data));
            }
        } else if (requestCode == App.SystemIntent.BROWSER) {
            if (resultCode == RESULT_OK) {
                updateProfilePic(fileManager.getFileDataFromIntent(data));
            }
        } else if (requestCode == App.SystemIntent.CAMERA) {
            if (resultCode == RESULT_OK) {
                updateProfilePic(fileManager.getImagePathForCamera());
            }
        }
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.compareInput(etUserName, profileModel.getDisplayName())
                && StringUtils.compareInput(etUserEmail, profileModel.getEmail())
                && StringUtils.compareInput(etUserPhone, profileModel.getPhone())) {
            showInterrupt("No changes found to update!", true);
            return false;
        }

        return super.isRequestValid();
    }

    @Override
    public void gotoNextScreen() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
