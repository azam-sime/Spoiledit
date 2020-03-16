package com.spoiledit.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import com.spoiledit.R;
import com.spoiledit.constants.Status;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.repos.CreateSpoilerRepo;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.CreateSpoilerViewModel;

public class AddSpoilerActivity extends RootActivity {
    public static final String TAG = AddSpoilerActivity.class.getCanonicalName();

    private CreateSpoilerViewModel createSpoilerViewModel;

    private RadioGroup rgSpoilerType, rgMidScene, rgStringer;
    private EditText etSpoiler;
    private MaterialButton btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createSpoilerViewModel = ViewModelProviders.of(this,
                new CreateSpoilerViewModel.CreateSpoilerViewModelFactory(new CreateSpoilerRepo(this)))
                .get(CreateSpoilerViewModel.class);
        setContentView(R.layout.activity_add_spoiler);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {
        etSpoiler = findViewById(R.id.et_username);
        etSpoiler.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(500)
        });

        rgSpoilerType = findViewById(R.id.rg_spoiler_type);
        rgMidScene = findViewById(R.id.rg_mid_credit);
        rgStringer = findViewById(R.id.rg_stringer);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener() {
//        rgSpoilerType.setOnCheckedChangeListener(this);
//        rgMidScene.setOnCheckedChangeListener(this);
//        rgStringer.setOnCheckedChangeListener(this);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void addObservers() {
        createSpoilerViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
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


                showSuccess(false, apiStatusModel.getMessage(), this::gotoNextScreen);
            }
        });
    }

    @Override
    public void toggleViews(boolean enable) {
        ViewUtils.toggleViewAbility(enable, etSpoiler, btnSubmit);
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(etSpoiler.getText().toString())) {
            showWarning("Please enter a valid username.");
            etSpoiler.requestFocus();
            etSpoiler.setSelection(etSpoiler.getText().length());
            return false;
        }
        return super.isRequestValid();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (isRequestValid()) {
                createSpoilerViewModel.createSpoiler(
                        new CreateSpoilerModel(
                                0,
                                0,
                                0,
                                ""
                        )
                );
            }
        }
        super.onClick(v);
    }

    @Override
    public void gotoNextScreen() {
        setResult(RESULT_OK);
        finish();
    }
}
