package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.spoiledit.R;

public class SignInActivity extends RootActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG = SignInActivity.class.getCanonicalName();

    private EditText etUsername, etPassword;
    private MaterialCheckBox cbRemember;
    private MaterialButton btnLogin;
    private TextView tvForgot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void initialiseListener() {
        cbRemember.setOnCheckedChangeListener(this);
        tvForgot.setOnClickListener(this);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            startActivity(new Intent(this, HomeActivity.class));
            return;

        }
        super.onClick(v);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
