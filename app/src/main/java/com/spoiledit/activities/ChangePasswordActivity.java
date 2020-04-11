package com.spoiledit.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.ProfileRepo;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.ChangePasswordViewModel;

public class ChangePasswordActivity extends RootActivity {
    public static final String TAG = ChangePasswordActivity.class.getCanonicalName();

    private ChangePasswordViewModel changePasswordViewModel;

    private EditText etNewPassword, etCPassword;
    private MaterialButton btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changePasswordViewModel = ViewModelProviders.of(this,
                new ChangePasswordViewModel.Factory(new ProfileRepo.ChangePasswordRepo()))
                .get(ChangePasswordViewModel.class);
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Change Password", true);
    }

    @Override
    public void initUi() {
        etNewPassword = findViewById(R.id.et_new_password);
        etCPassword = findViewById(R.id.et_confirm_password);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        hideLoader();

        changePasswordViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.PASSWORD_UPDATE) {
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
                    showSuccess(false, apiStatusModel.getMessage(), this::onBackPressed);
                }
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etNewPassword, etCPassword, btnSubmit);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (isRequestValid()) {
                String[] values = new String[]{etNewPassword.getText().toString()};

                changePasswordViewModel.requestUpdatePassword(values);
            }
        }
        super.onClick(v);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etNewPassword.getText().toString())) {
            showWarning("Please enter a valid password.");
            etNewPassword.requestFocus();
            etNewPassword.setSelection(etNewPassword.getText().length());
            return false;

        } else if (!etNewPassword.getText().toString().equals(etCPassword.getText().toString())) {
            showWarning("Password doesn't match.");
            etCPassword.requestFocus();
            etCPassword.setSelection(etCPassword.getText().length());
            return false;

        }
        return super.isRequestValid();
    }
}
