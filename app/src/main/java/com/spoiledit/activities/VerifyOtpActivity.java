package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.listeners.TextChangeListener;
import com.spoiledit.repos.VerifyRepo;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.VerifyViewModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends RootActivity {
    public static final String TAG = VerifyOtpActivity.class.getCanonicalName();

    private VerifyViewModel verifyViewModel;

    private EditText et1, et2, et3, et4, et5, et6;
    private TextView tvResend, tvCountDown, tvCreateNew;
    private MaterialButton btnSubmit;

    private int extraSentTo, extraSentFor;
    private String extraSentToAddress;

    private CountDownTimer countDownTimer = new CountDownTimer(
            TimeUnit.MINUTES.toMillis(2),
            TimeUnit.SECONDS.toMillis(1)) {
        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

            if (seconds < 10)
                tvCountDown.setText(String.format(Locale.getDefault(), "%d:0%d", minutes, seconds));
            else
                tvCountDown.setText(String.format(Locale.getDefault(), "%d:%d", minutes, seconds));
        }

        @Override
        public void onFinish() {
            onTimeOut();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyViewModel = ViewModelProviders.of(this,
                new VerifyViewModel.Factory(new VerifyRepo(this))).get(VerifyViewModel.class);

        extraSentFor = getIntent().getIntExtra(App.Intent.Extra.OTP_FOR, App.Intent.Value.OTP_FOR_REGISTRATION);
        extraSentTo = getIntent().getIntExtra(App.Intent.Extra.OTP_SENT_TO, App.Intent.Value.OTP_SENT_TO_MAIL);
        extraSentToAddress = getIntent().getStringExtra(App.Intent.Extra.OTP_SENT_TO_ADDRESS);

        setContentView(R.layout.activity_verify_phone);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Verify Otp");
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
        tvCreateNew = findViewById(R.id.tv_create_password);
        ViewUtils.toggleViewVisibility(extraSentFor != App.Intent.Value.OTP_FOR_REGISTRATION, tvCreateNew);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener() {
        et1.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et1.getText().length() == 1) {
                    et2.requestFocus();
                    et2.setSelection(et2.getText().length());
                }
            }
        });
        et2.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et2.getText().length() == 1) {
                    et3.requestFocus();
                    et3.setSelection(et3.getText().length());

                } else if (et2.getText().length() == 0) {
                    et1.requestFocus();
                    et1.setSelection(et1.getText().length());
                }
            }
        });
        et3.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et3.getText().length() == 1) {
                    et4.requestFocus();
                    et4.setSelection(et4.getText().length());

                } else if (et3.getText().length() == 0) {
                    et2.requestFocus();
                    et2.setSelection(et2.getText().length());
                }
            }
        });
        et4.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et4.getText().length() == 1) {
                    et5.requestFocus();
                    et5.setSelection(et5.getText().length());

                } else if (et4.getText().length() == 0) {
                    et3.requestFocus();
                    et3.setSelection(et3.getText().length());
                }
            }
        });
        et5.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et5.getText().length() == 1) {
                    et6.requestFocus();
                    et6.setSelection(et6.getText().length());

                } else if (et5.getText().length() == 0) {
                    et4.requestFocus();
                    et4.setSelection(et4.getText().length());
                }
            }
        });
        et6.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et6.getText().length() == 1) {
                    hideKeyboard(et6);

                } else if (et6.getText().length() == 0) {
                    et5.requestFocus();
                    et5.setSelection(et5.getText().length());
                }
            }
        });

        tvResend.setOnClickListener(this);
        tvCountDown.setOnClickListener(this);
        tvCreateNew.setOnClickListener(this);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        verifyViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
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

                if (apiStatusModel.getApi() == Constants.Api.OTP_USER_REGISTRATION) {
                    PreferenceUtils.saveLoginStatus(this, Status.Login.REQUIRE_SIGN_IN_AND_CREDS);
                    startActivity(new android.content.Intent(this, DashboardActivity.class));
                    finish();
                } else if (apiStatusModel.getApi() == Constants.Api.OTP_FORGOT_PASSWORD) {
                    startActivity(new android.content.Intent(this, SignInActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void setData() {
        boolean mail = extraSentTo == App.Intent.Value.OTP_SENT_TO_MAIL;

        String otpHeader1 = "A 6 digit confirmation code has been sent to ";
        String otpHeader2 = " via ";
        ((TextView) findViewById(R.id.tv_otp_prompt)).setText(
                new StringBuilder()
                        .append(otpHeader1)
                        .append(StringUtils.hideChars(extraSentToAddress))
                        .append(otpHeader2)
                        .append(mail ? "mail" : "phone")
                        .toString()
        );
        onRequestOtp();
    }

    private void onRequestOtp() {
        ViewUtils.showViews(tvCountDown);
        ViewUtils.hideViews(tvResend);
        countDownTimer.start();
    }

    private void onTimeOut() {
        ViewUtils.showViews(tvResend);
        ViewUtils.hideViews(tvCountDown);

        countDownTimer.cancel();
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, btnSubmit, tvCreateNew, tvResend);
    }

    private String combineOtp() {
        return et1.getText().toString()
                + et2.getText().toString()
                + et3.getText().toString()
                + et4.getText().toString()
                + et5.getText().toString()
                + et6.getText().toString();
    }

    @Override
    public boolean isRequestValid() {
        String otp = combineOtp();

        if (StringUtils.isInvalid(otp) || otp.length() < 6) {
            showWarning(false, "Please enter a valid otp!");
            return false;
        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_resend) {
            onRequestOtp();

        } else if (v.getId() == R.id.btn_submit) {
            if (isRequestValid()) {
                if (extraSentFor == App.Intent.Value.OTP_FOR_PASSWORD_VERIFICATION)
                    verifyViewModel.verifyForgotPasswordOtp(new String[]{extraSentToAddress, combineOtp()});
                else
                    verifyViewModel.verifyRegistrationOtp(new String[]{extraSentToAddress, combineOtp()});
            }

        } else if (v.getId() == R.id.tv_create_password) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
            finish();

        } else
            super.onClick(v);
    }
}
