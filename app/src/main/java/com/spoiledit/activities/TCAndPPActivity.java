package com.spoiledit.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.TCRepo;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.TCAndPPViewModel;

public class TCAndPPActivity extends RootActivity {
    public static final String TAG = TCAndPPActivity.class.getCanonicalName();

    private TCAndPPViewModel tcAndPPViewModel;
    private TextView tvTandC;
    private boolean tc, actionable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tcAndPPViewModel = ViewModelProviders.of(this,
                new TCAndPPViewModel.TCViewModelFactory(new TCRepo(this)))
                .get(TCAndPPViewModel.class);
        tc = getIntent().getIntExtra(App.Intent.Extra.TC_AND_PP, App.Intent.Value.FOR_TC) == App.Intent.Value.FOR_TC;
        actionable = !getIntent().getBooleanExtra(App.Intent.Extra.TC_AND_PP_DISPLAY_ONLY, false);
        setContentView(R.layout.activity_tc);
    }

    @Override
    public void setUpToolBar() {
        String header = tc ? "Terms and conditions" : "Cookies Policy";
        setupToolBar(header);
        ((TextView) findViewById(R.id.tv_header)).setText(header);
    }

    @Override
    public void initUi() {
        tvTandC = findViewById(R.id.tv_terms_conditions);

        ViewUtils.toggleViewVisibility(actionable, findViewById(R.id.btn_accept_terms), findViewById(R.id.tv_read_later));
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_accept_terms).setOnClickListener(this);
        findViewById(R.id.tv_read_later).setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        tcAndPPViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel == null)
                return;

            hideLoader();
            if (apiStatusModel.getApi() == Constants.Api.T_AND_C) {
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
                }
            }
        });

        tcAndPPViewModel.getStringTCMutableLiveData().observe(this, s -> tvTandC.setText(Html.fromHtml(s)));
    }

    @Override
    public void requestData() {
        showLoader();
        if (tc)
            tcAndPPViewModel.requestTAndC();
        else
            tcAndPPViewModel.requestCookies();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_accept_terms) {
            setResult(RESULT_OK);
            finish();
        } else if (v.getId() == R.id.tv_read_later) {
            setResult(RESULT_CANCELED);
            finish();
        } else
            super.onClick(v);
    }
}
