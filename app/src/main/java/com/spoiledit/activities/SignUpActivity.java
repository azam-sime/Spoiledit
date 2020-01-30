package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.spoiledit.R;

public class SignUpActivity extends RootActivity {
    public static final String TAG = SignUpActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {

    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_sign_up).setOnClickListener(
                v -> startActivity(new Intent(this, HomeActivity.class))
        );

        findViewById(R.id.tv_sign_in).setOnClickListener(
                v -> startActivity(new Intent(this, SignInActivity.class))
        );
    }

    @Override
    public void setData() {

    }
}
