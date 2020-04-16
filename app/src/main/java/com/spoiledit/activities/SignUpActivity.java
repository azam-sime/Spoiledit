package com.spoiledit.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.SignUpRepo;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.SignUpViewModel;

import java.util.regex.Pattern;

public class SignUpActivity extends RootActivity {
    public static final String TAG = SignUpActivity.class.getCanonicalName();

    private SignUpViewModel signUpViewModel;

    private EditText etName, etPhone, etEmail, etPassword, etConfirmP;
    private MaterialCheckBox cbTandC;
    private MaterialButton btnSignUp;
    private TextView tvTandC, tvSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpViewModel = ViewModelProviders.of(this,
                new SignUpViewModel.Factory(new SignUpRepo(this)))
                .get(SignUpViewModel.class);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Register");
    }

    @Override
    public void initUi() {
        etName = findViewById(R.id.tv_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmP = findViewById(R.id.et_confirm_password);

        cbTandC = findViewById(R.id.cb_t_and_c);
        tvTandC = findViewById(R.id.tv_t_and_c);

        btnSignUp = findViewById(R.id.btn_sign_up);

        tvSignIn = findViewById(R.id.tv_sign_in);
    }

    @Override
    public void initialiseListener() {
        etName.setFilters(InputUtils.getLengthFilters(30));
        etPhone.setFilters(InputUtils.getLengthFilters(10));
        etEmail.setFilters(InputUtils.getLengthFilters(50));
        etPassword.setFilters(InputUtils.getLengthFilters(20));
        etConfirmP.setFilters(InputUtils.getLengthFilters(20));

        tvTandC.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void addObservers() {
        signUpViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.USER_REGISTER) {
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
                    PreferenceUtils.saveLoginStatus(this, Status.Login.REQUIRE_SIGN_IN_AND_CREDS);
//                    showSuccess(false, apiStatusModel.getMessage(), this::gotoNextScreen);
                    gotoNextScreen();
                }
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable,
                etName, etPhone, etEmail, etPassword, etConfirmP,
                cbTandC,
                btnSignUp, tvTandC, tvSignIn);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etName.getText().toString())) {
            showWarning("Please enter a valid name.");
            etName.requestFocus();
            etName.setSelection(etName.getText().length());
            return false;

        } else if (StringUtils.isInvalid(etPhone.getText().toString())) {
            showWarning("Please enter a valid phone.");
            etPhone.requestFocus();
            etPhone.setSelection(etPhone.getText().length());
            return false;

        } else if (!Pattern.matches(Patterns.PHONE.pattern(), etPhone.getText().toString())) {
            showFailure(false, "Provided phone number doesn't match a valid phone format.");
            etPhone.requestFocus();
            etPhone.setSelection(etPhone.getText().length());
            return false;

        } else if (StringUtils.isInvalid(etEmail.getText().toString())) {
            showWarning("Please enter a valid email.");
            etEmail.requestFocus();
            etEmail.setSelection(etEmail.getText().length());
            return false;

        } else if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), etEmail.getText().toString())) {
            showFailure(false, "Provided email address doesn't match a valid email format.");
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

        } else if (!cbTandC.isChecked()) {
            showWarning("Please check the box to ensure you have read our terms and conditions.");
            return false;

        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_up) {
            if (isRequestValid()) {
                String[] credentials = new String[]{
                        etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim(),
                        etName.getText().toString().trim(),
                        etPhone.getText().toString().trim()
                };

                PreferenceUtils.saveCredentials(this, credentials);
                signUpViewModel.requestSignUp(credentials);
            }

        } else if (v.getId() == R.id.tv_t_and_c) {
            android.content.Intent intent = new android.content.Intent(this, ProviderDetailsActivity.class);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS, App.Intent.Value.FOR_TC);
            intent.putExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, false);
            startActivityForResult(intent, App.Intent.Result.IS_TC_AGREED);

        } else if (v.getId() == R.id.tv_sign_in) {
            startActivity(new android.content.Intent(this, SignInActivity.class));
            finish();
        } else
            super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == App.Intent.Result.IS_TC_AGREED) {
            cbTandC.setChecked(resultCode == RESULT_OK);
        }
    }

    @Override
    public void gotoNextScreen() {
        PreferenceUtils.saveCredentials(this, new String[]{
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()
        });
        PreferenceUtils.saveLoginStatus(this, Status.Login.REQUIRE_SIGN_IN_AND_CREDS);
        android.content.Intent intent = new android.content.Intent(this, VerifyOtpActivity.class);
        intent.putExtra(App.Intent.Extra.OTP_FOR, App.Intent.Value.OTP_FOR_REGISTRATION);
        intent.putExtra(App.Intent.Extra.OTP_SENT_TO, App.Intent.Value.OTP_SENT_TO_MAIL);
        intent.putExtra(App.Intent.Extra.OTP_SENT_TO_ADDRESS, etEmail.getText().toString().trim());
        startActivity(intent);
        finish();
    }
}
