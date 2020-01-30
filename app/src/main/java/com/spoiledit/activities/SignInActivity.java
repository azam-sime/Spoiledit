package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.spoiledit.R;

public class SignInActivity extends RootActivity {
    public static final String TAG = SignInActivity.class.getCanonicalName();

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

    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_login).setOnClickListener(
                v -> startActivity(new Intent(this, HomeActivity.class))
        );
    }

    @Override
    public void setData() {

    }
}
