package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.spoiledit.R;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.PreferenceUtils;

public class SplashActivity extends RootActivity {
    public static final String TAG = SplashActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        runOnUiAfterMillis(() -> {
            switch (PreferenceUtils.loginStatus(this)) {
                case Status.Login.REQUIRE_NOTHING:
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                    break;
                case Status.Login.REQUIRE_SIGN_IN_NOT_CREDS:
                case Status.Login.REQUIRE_SIGN_IN_AND_CREDS:
                case Status.Login.REQUIRE_SIGN_UP:
                    startActivity(new Intent(this, SignInActivity.class));
                    finish();
                    break;
//                case Status.Login.REQUIRE_SIGN_UP:
//                    startActivity(new Intent(this, SignUpActivity.class));
//                    finish();
//                    break;
            }
        }, 3000);
    }
}
