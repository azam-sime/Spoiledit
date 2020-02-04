package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;

public class VerifyPhoneActivity extends RootActivity {
    public static final String TAG = VerifyPhoneActivity.class.getCanonicalName();

    private EditText et1, et2, et3, et4, et5, et6;
    private TextView tvResend, tvCountDown, tvCreateNew;
    private MaterialButton btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Verify Number");
    }

    @Override
    public void initUi() {
        et1 = findViewById(R.id.et_otp_1);
        et2 = findViewById(R.id.et_otp_2);
        et3 = findViewById(R.id.et_otp_3);
        et4 = findViewById(R.id.et_otp_4);
        et5 = findViewById(R.id.et_otp_5);
        et6 = findViewById(R.id.et_otp_6);

        tvResend = findViewById(R.id.tv_resend);
        tvCountDown = findViewById(R.id.tv_count_down);
        tvCreateNew = findViewById(R.id.tv_new_password);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener() {
        tvResend.setOnClickListener(this);
        tvCountDown.setOnClickListener(this);
        tvCreateNew.setOnClickListener(this);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
