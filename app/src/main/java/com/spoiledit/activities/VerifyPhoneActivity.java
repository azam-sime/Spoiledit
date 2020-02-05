package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.listeners.TextChangeListener;
import com.spoiledit.utils.ViewUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends RootActivity {
    public static final String TAG = VerifyPhoneActivity.class.getCanonicalName();

    private EditText et1, et2, et3, et4, et5, et6;
    private TextView tvResend, tvCountDown, tvCreateNew;
    private MaterialButton btnSubmit;

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
                    hideKeyboard();

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
    public void onClick(View v) {
        if (v.getId() == R.id.tv_resend) {
            onRequestOtp();
            return;
        }
        super.onClick(v);
    }
}
