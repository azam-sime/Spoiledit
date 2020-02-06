package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.LoginViewModel;

public class ForgotPasswordFragment extends RootFragment {
    public static final String TAG = ForgotPasswordFragment.class.getCanonicalName();

    private LoginViewModel loginViewModel;

    private EditText etEmail, etPassword, etConfirmP;
    private MaterialButton btnSubmit;

    private boolean skip = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void setUpToolbar(View view) {
//        setupToolBar(view, "Forgot Password?");
    }

    @Override
    public void initUi(View view) {
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmP = view.findViewById(R.id.et_confirm_password);

        btnSubmit = view.findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener(View view) {
        etEmail.setFilters(InputUtils.getLengthFilters(50));
        etPassword.setFilters(InputUtils.getLengthFilters(20));
        etConfirmP.setFilters(InputUtils.getLengthFilters(20));

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setData(View view) {

    }

    @Override
    public void addObservers() {
        loginViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.FORGOT_PASSWORD) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    toggleViews(false);
                    showLoader(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                    toggleViews(true);
                    hideLoader();
                    showFailure(apiStatusModel.getMessage());
                    skip = true;

                } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                    toggleViews(false);
                    hideLoader();
                    showSuccess(apiStatusModel.getMessage(), this::gotoNextScreen);
                }
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etEmail, etPassword, etConfirmP, btnSubmit);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etEmail.getText().toString())) {
            showWarning("Please enter a valid email.");
            etEmail.requestFocus();
            etEmail.setSelection(etEmail.getText().length());
            return false;

        } else if (StringUtils.isInvalid(etPassword.getText().toString())) {
            showWarning("Please enter a valid password.");
            etPassword.requestFocus();
            etPassword.setSelection(etPassword.getText().length());
            return false;

        } else if (!etPassword.getText().toString().equals(etConfirmP.getText().toString())) {
            showWarning("Password doesn't match.");
            etConfirmP.requestFocus();
            etConfirmP.setSelection(etConfirmP.getText().length());
            return false;
        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (isRequestValid()) {
                String[] values = new String[]{etEmail.getText().toString(), etPassword.getText().toString()};
                loginViewModel.requestPassword(values);
            }
        }
    }

    @Override
    public void gotoNextScreen() {

    }
}
