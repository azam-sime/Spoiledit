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

public class SignUpActivity extends RootActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG = SignUpActivity.class.getCanonicalName();

    private EditText etName, etPhone, etEmail, etPassword, etConfirmP;
    private MaterialCheckBox cbTandC;
    private MaterialButton btnSignUp;
    private TextView tvTandC, tvSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Register");
    }

    @Override
    public void initUi() {
        etName = findViewById(R.id.et_name);
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
        cbTandC.setOnCheckedChangeListener(this);
        tvTandC.setOnClickListener(this);

        btnSignUp.setOnClickListener(this);

        tvSignIn.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_up) {
            startActivity(new Intent(this, VerifyPhoneActivity.class));
            return;

        } else if (v.getId() == R.id.tv_sign_in) {
            startActivity(new Intent(this, SignInActivity.class));
            return;
        }
        super.onClick(v);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
