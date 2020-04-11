package com.spoiledit.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.ProviderDetailsRepo;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.ProviderDetailsViewModel;

public class ProviderDetailsActivity extends RootActivity {
    public static final String TAG = ProviderDetailsActivity.class.getCanonicalName();

    private ProviderDetailsViewModel detailsProviderViewModel;
    private TextView tvDetails;
    private int detailsType;
    private boolean actionable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsProviderViewModel = ViewModelProviders.of(this,
                new ProviderDetailsViewModel.Factory(new ProviderDetailsRepo()))
                .get(ProviderDetailsViewModel.class);
        detailsType = getIntent().getIntExtra(App.Intent.Extra.PROVIDER_DETAILS, 0);

        if (detailsType == 0)
            finish();

        actionable = !getIntent().getBooleanExtra(App.Intent.Extra.PROVIDER_DETAILS_DISPLAY_ONLY, false);
        setContentView(R.layout.activity_provider_details);
    }

    @Override
    public void setUpToolBar() {
        String header = detailsType == App.Intent.Value.FOR_TC ? "Terms and conditions"
                : detailsType == App.Intent.Value.FOR_PP ? "Privacy Policies"
                : detailsType == App.Intent.Value.FOR_CP ? "Cookies Policy"
                : "About Us";
        setupToolBar(header);
        ((TextView) findViewById(R.id.tv_header)).setText(header);
    }

    @Override
    public void initUi() {
        tvDetails = findViewById(R.id.tv_provider_details);

        ViewUtils.toggleViewVisibility(actionable,
                findViewById(R.id.btn_accept_terms),
                findViewById(R.id.tv_read_later));
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.btn_accept_terms).setOnClickListener(this);
        findViewById(R.id.tv_read_later).setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        detailsProviderViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel == null)
                return;

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
        });

        detailsProviderViewModel.getDetailsMutableLiveData().observe(this,
                s -> tvDetails.setText(Html.fromHtml(s)));
    }

    @Override
    public void requestData() {
        showLoader();

        switch (detailsType) {
            case App.Intent.Value.FOR_TC: {
                detailsProviderViewModel.requestTermsConditions();
            }
            break;
            case App.Intent.Value.FOR_PP: {
                detailsProviderViewModel.requestPrivacyPolicies();
            }
            break;
            case App.Intent.Value.FOR_CP: {
                detailsProviderViewModel.requestCookiesPolicy();
            }
            break;
            case App.Intent.Value.FOR_AU: {
                detailsProviderViewModel.requestAboutUs();
            }
            break;
        }
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
