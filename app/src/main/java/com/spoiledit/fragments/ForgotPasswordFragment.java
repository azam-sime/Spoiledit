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
import com.spoiledit.activities.VerifyOtpActivity;
import com.spoiledit.constants.App;
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
    public void setData(View view) {

    }

    @Override
    public void addObservers() {
        loginViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.PASSWORD_FORGOT) {
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
            if (isRequestValid()) {
                String[] values = new String[]{etEmail.getText().toString()};
                loginViewModel.requestPassword(values);
            }
        }
    }

    @Override
    public void gotoNextScreen() {
        android.content.Intent intent = new android.content.Intent(getActivity(), VerifyOtpActivity.class);
        intent.putExtra(App.Intent.Extra.OTP_FOR, App.Intent.Value.OTP_FOR_VERIFICATION);
        intent.putExtra(App.Intent.Extra.OTP_SENT_TO, App.Intent.Value.OTP_SENT_TO_MAIL);
        intent.putExtra(App.Intent.Extra.OTP_SENT_TO_ADDRESS, etEmail.getText().toString().trim());
        startActivity(intent);

        getActivity().finish();
    }
}
