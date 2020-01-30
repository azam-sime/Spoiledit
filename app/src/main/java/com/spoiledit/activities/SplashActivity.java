package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.spoiledit.R;

public class SplashActivity extends RootActivity {
    public static final String TAG = SplashActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        runOnUiAfterMillis(() -> {
            startActivity(new Intent(this, SignUpActivity.class));
        }, 3000);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {

    }

    @Override
    public void initialiseListener() {

    }

    @Override
    public void setData() {

    }
}
