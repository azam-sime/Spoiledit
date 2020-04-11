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

    private EditText etEmail;
    private MaterialButton btnSubmit;

    private OnVerifyOtpListener onVerifyOtpListener;

    public ForgotPasswordFragment(OnVerifyOtpListener onVerifyOtpListener) {
        this.onVerifyOtpListener = onVerifyOtpListener;
    }

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
    public void setUpToolbar() {

    }

    @Override
    public void initUi(View view) {
        etEmail = view.findViewById(R.id.et_email);

        btnSubmit = view.findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener(View view) {
        etEmail.setFilters(InputUtils.getLengthFilters(50));

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        loginViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.OTP_FORGOT_PASSWORD) {
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
                    showSuccess(false, apiStatusModel.getMessage(), () -> {
                        if (onVerifyOtpListener != null)
                            onVerifyOtpListener.onOtpVerificationClicked(true, etEmail.getText().toString().trim());
                    });
                }
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etEmail, btnSubmit);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etEmail.getText().toString())) {
            showWarning("Please enter a valid email.");
            etEmail.requestFocus();
            etEmail.setSelection(etEmail.getText().length());
            return false;

        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (isRequestValid())
                loginViewModel.requestForgotPasswordOtp(new String[]{etEmail.getText().toString()});
        }
    }

    public interface OnVerifyOtpListener {
        void onOtpVerificationClicked(boolean sentToMail, String sentToAddress);
    }
}
