package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.fragments.CreatePasswordFragment;
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

    public static final String KEY_SENT_TO = TAG + ".key_sent_to";
    public static final String SENT_TO_MAIL = TAG + ".sent_to_mail";
    public static final String SENT_TO_PHONE = TAG + ".sent_to_phone";
    public static final String KEY_SENT_ADDRESS = TAG + ".sent_address";

    private VerifyViewModel verifyViewModel;

    private EditText et1, et2, et3, et4, et5, et6;
    private TextView tvResend, tvCountDown, tvCreateNew;
    private MaterialButton btnSubmit;

    private String sentTo, sentAddress;
    private final String otpHeader1 = "A 6 digit confirmation code has been sent to ";
    private final String otpHeader2 = " via ";

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
                new VerifyViewModel.VerifyFactory(new VerifyRepo(this))).get(VerifyViewModel.class);

        sentTo = getIntent().getStringExtra(KEY_SENT_TO);
        sentAddress = getIntent().getStringExtra(KEY_SENT_ADDRESS);

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
        tvCreateNew = findViewById(R.id.tv_new_password);

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
    public void setData() {
        boolean mail = sentTo.equals(SENT_TO_MAIL);
        ((TextView) findViewById(R.id.tv_otp_prompt)).setText(
                new StringBuilder()
                        .append(otpHeader1)
                        .append(StringUtils.hideChars(sentAddress))
                        .append(otpHeader2)
                        .append(mail ? "mail" : "phone")
                        .toString()
        );
        onRequestOtp();
    }

    @Override
    public void addObservers() {
        verifyViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.VERIFY_OTP) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    toggleViews(false);
                    showLoader(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                    toggleViews(true);
                    hideLoader();
                    showFailure(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                    toggleViews(false);
                    hideLoader();
                    PreferenceUtils.saveLoginStatus(this, Status.Login.REQUIRE_SIGN_IN_AND_CREDS);
                    showSuccess(apiStatusModel.getMessage(), this::gotoNextScreen);
                }
            }
        });
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

    @Override
    public boolean isRequestValid() {
        if (!verifyViewModel.isOtpValid()) {
            showWarning("Otp doesn't match!");
            return false;
        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_resend) {
            onRequestOtp();
            return;

        } else if (v.getId() == R.id.btn_submit) {
            if (isRequestValid())
                gotoNextScreen();

            return;

        } else if (v.getId() == R.id.tv_new_password) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CreatePasswordFragment fragment = new CreatePasswordFragment();
            fragmentTransaction.setCustomAnimations(R.anim.rise_from_bottom, R.anim.sink_to_bottom);
            fragmentTransaction.add(R.id.ll_container, fragment);
            fragmentTransaction.addToBackStack(CreatePasswordFragment.TAG);
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
