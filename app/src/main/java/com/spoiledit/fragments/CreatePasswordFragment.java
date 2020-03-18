package com.spoiledit.fragments;

import android.content.Intent;
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
import com.spoiledit.activities.SignInActivity;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.VerifyViewModel;

public class CreatePasswordFragment extends RootFragment {
    public static final String TAG = CreatePasswordFragment.class.getCanonicalName();

    private VerifyViewModel verifyViewModel;

    private EditText etNewPassword, etCPassword;
    private MaterialButton btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyViewModel = ViewModelProviders.of(getActivity()).get(VerifyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_password, container, false);
    }

    @Override
    public void setUpToolbar(View view) {
//        setupToolBar(view, "Create Password");
    }

    @Override
    public void initUi(View view) {
        etNewPassword = view.findViewById(R.id.et_new_password);
        etCPassword = view.findViewById(R.id.et_confirm_password);

        btnSubmit = view.findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener(View view) {

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setData(View view) {

    }

    @Override
    public void addObservers() {
        verifyViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
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
                    showSuccess(false, apiStatusModel.getMessage(), this::gotoNextScreen);
                }
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etNewPassword, etCPassword, btnSubmit);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (isRequestValid()) {
                String[] values = new String[] {etNewPassword.getText().toString()};

                verifyViewModel.requestUpdatePassword(values);
            }
        }
    }

    @Override
    public void gotoNextScreen() {
        startActivity(new Intent(getActivity(), SignInActivity.class));
        getActivity().finish();
    }
}
