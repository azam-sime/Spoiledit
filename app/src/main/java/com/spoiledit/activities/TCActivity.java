package com.spoiledit.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.TCRepo;
import com.spoiledit.viewmodels.TCViewModel;

public class TCActivity extends RootActivity {
    public static final String TAG = TCActivity.class.getCanonicalName();

    private TCViewModel tcViewModel;
    private TextView tvTandC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tcViewModel = ViewModelProviders.of(this,
                new TCViewModel.TCViewModelFactory(new TCRepo(this)))
                .get(TCViewModel.class);
        setContentView(R.layout.activity_tc);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Terms and conditions");
    }

    @Override
    public void initUi() {
        tvTandC = findViewById(R.id.tv_terms_conditions);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_accept_terms).setOnClickListener(this);
        findViewById(R.id.tv_read_later).setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        tcViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel == null)
                return;

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

        tcViewModel.getStringTCMutableLiveData().observe(this, s -> tvTandC.setText(Html.fromHtml(s)));
    }

    @Override
    public void requestData() {
        tcViewModel.requestTAndC();
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
