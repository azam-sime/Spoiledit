package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.models.UserModel;
import com.spoiledit.repos.ProfileRepo;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.viewmodels.ProfileEditViewModel;

public class ProfileEditActivity extends RootActivity {
    public static final String TAG = ProfileEditActivity.class.getCanonicalName();

    private ProfileEditViewModel profileEditViewModel;

    private UserModel userModel;
    private EditText etName, etEmail, etPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileEditViewModel = ViewModelProviders.of(this,
                new ProfileEditViewModel.Factory(new ProfileRepo.UpdateRepo()))
                .get(ProfileEditViewModel.class);
        setContentView(R.layout.activity_profile_edit);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Edit Profile", true);
    }

    @Override
    public void initUi() {
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void setData() {
        userModel = PreferenceUtils.getUserModel(this);
        etName.setText(userModel.getDisplayName());
        etEmail.setText(userModel.getEmail());
        etPhone.setText(userModel.getPhone());
    }

    @Override
    public void addObservers() {
        hideLoader();

        profileEditViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                toggleViews(false);
                showLoader(apiStatusModel.getMessage());

            } else if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                toggleViews(true);
                hideLoader();
                showFailure(false, apiStatusModel.getMessage());

            } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                if (apiStatusModel.getApi() == Constants.Api.USER_PROFILE_UPDATE) {
                    profileEditViewModel.getProfileDetails();

                } else if (apiStatusModel.getApi() == Constants.Api.USER_PROFILE_GET) {
                    toggleViews(false);
                    hideLoader();
                    setResult(RESULT_OK);
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update) {
            if (isRequestValid()) {
                showLoader();
                profileEditViewModel.updateProfile(
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPhone.getText().toString().trim()
                );
            }
        }
        super.onClick(v);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etName)) {
            showInterrupt("Please use a valid name!", true);
            return false;
        } else if (StringUtils.isInvalid(etPhone)) {
            showInterrupt("Please use a valid phone number!", true);
            return false;
        } else if (StringUtils.isInvalid(etEmail)) {
            showInterrupt("Please use a valid email address!", true);
            return false;
        }

        return super.isRequestValid();
    }
}
