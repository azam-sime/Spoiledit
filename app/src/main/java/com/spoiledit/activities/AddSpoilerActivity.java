package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.spoiledit.R;
import com.spoiledit.constants.Status;
import com.spoiledit.fragments.ForgotPasswordFragment;
import com.spoiledit.repos.LoginRepo;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.LoginViewModel;

public class AddSpoilerActivity extends RootActivity {
    public static final String TAG = AddSpoilerActivity.class.getCanonicalName();

    private LoginViewModel loginViewModel;

    private EditText etUsername, etPassword;
    private MaterialCheckBox cbRemember;
    private MaterialButton btnLogin;
    private TextView tvForgot, tvSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this,
                new LoginViewModel.LoginViewModelFactory(new LoginRepo(this)))
                .get(LoginViewModel.class);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        cbRemember = findViewById(R.id.cb_remember_me);
        tvForgot = findViewById(R.id.tv_forgot_password);

        btnLogin = findViewById(R.id.btn_login);

        tvSignUp = findViewById(R.id.tv_sign_up);
    }

    @Override
    public void initialiseListener() {
        tvForgot.setOnClickListener(this);

        btnLogin.setOnClickListener(this);

        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void setData() {
        int loginStatus = PreferenceUtils.loginStatus(this);
        String[] credentials = PreferenceUtils.credentials(this);

        if (loginStatus == Status.Login.REQUIRE_SIGN_IN_NOT_CREDS
                && credentials[0] != null && credentials[1] != null) {
            etUsername.setText(credentials[0]);
            etPassword.setText(credentials[1]);

            cbRemember.setChecked(true);
        }
    }

    @Override
    public void addObservers() {
        loginViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
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
                PreferenceUtils.saveLoginStatus(this,
                        cbRemember.isChecked() ? Status.Login.REQUIRE_SIGN_IN_NOT_CREDS
                                : Status.Login.REQUIRE_SIGN_IN_AND_CREDS);
                showSuccess(false, apiStatusModel.getMessage(), this::gotoNextScreen);
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etUsername, etPassword, cbRemember, btnLogin, tvForgot);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etUsername.getText().toString())) {
            showWarning("Please enter a valid username.");
            etUsername.requestFocus();
            etUsername.setSelection(etUsername.getText().length());
            return false;

        } else if (StringUtils.isInvalid(etPassword.getText().toString())) {
            showWarning("Please enter a valid password.");
            etPassword.requestFocus();
            etPassword.setSelection(etPassword.getText().length());
            return false;
        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (isRequestValid()) {
                String[] credentials = new String[] {etUsername.getText().toString(),
                        etPassword.getText().toString()};
                PreferenceUtils.saveCredentials(this, credentials);
                loginViewModel.requestLogin(credentials);
            }
            return;

        } else if (v.getId() == R.id.tv_sign_up) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
            return;

        } else if (v.getId() == R.id.tv_forgot_password) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ForgotPasswordFragment fragment = new ForgotPasswordFragment();
            fragmentTransaction.setCustomAnimations(R.anim.rise_from_bottom, R.anim.sink_to_bottom);
            fragmentTransaction.add(R.id.ll_container, fragment);
            fragmentTransaction.addToBackStack(ForgotPasswordFragment.TAG);
            fragmentTransaction.commit();
            return;
        }
        super.onClick(v);
    }

    @Override
    public void gotoNextScreen() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
