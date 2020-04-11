package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import com.spoiledit.R;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.utils.StringUtils;

public class AddSpoilerActivity extends RootActivity {
    public static final String TAG = AddSpoilerActivity.class.getCanonicalName();

    public static final String RESULT_EXTRA_MODEL = TAG + ".extra_result";

    private RadioGroup rgSpoilerType, rgMidScene, rgStringer;
    private EditText etSpoiler;
    private MaterialButton btnSubmit;

    private CreateSpoilerModel spoilerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spoiler);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("Add Spoiler");
    }

    @Override
    public void initUi() {
        etSpoiler = findViewById(R.id.et_spoiler);
        etSpoiler.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(500)
        });

        rgSpoilerType = findViewById(R.id.rg_spoiler_type);
        rgMidScene = findViewById(R.id.rg_mid_credit);
        rgStringer = findViewById(R.id.rg_stringer);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void initialiseListener() {
        btnSubmit.setOnClickListener(this);

        rgSpoilerType.setOnCheckedChangeListener((group, checkedId) -> {
            if (group.getId() == R.id.rg_spoiler_type) {
                if (checkedId == R.id.rbtn_sp_full)
                    spoilerModel.setSpType("1");
                else if (checkedId == R.id.rbtn_sp_brief)
                    spoilerModel.setSpType("2");
                else if (checkedId == R.id.rbtn_sp_ending)
                    spoilerModel.setSpType("3");
            }
        });

        rgMidScene.setOnCheckedChangeListener((group, checkedId) -> {
            if (group.getId() == R.id.rg_mid_credit) {
                if (checkedId == R.id.rbtn_mc_yes)
                    spoilerModel.setMidCredit("Yes");
                else if (checkedId == R.id.rbtn_mc_no)
                    spoilerModel.setMidCredit("No");
                else if (checkedId == R.id.rbtn_mc_ns)
                    spoilerModel.setMidCredit("Not Sure");
            }
        });

        rgStringer.setOnCheckedChangeListener((group, checkedId) -> {
            if (group.getId() == R.id.rg_stringer) {
                if (checkedId == R.id.rbtn_pc_yes)
                    spoilerModel.setStringer("Yes");
                else if (checkedId == R.id.rbtn_pc_no)
                    spoilerModel.setStringer("No");
                else if (checkedId == R.id.rbtn_pc_ns)
                    spoilerModel.setStringer("Not Sure");
            }
        });
    }

    @Override
    public void setData() {
        spoilerModel = new CreateSpoilerModel();
    }

    @Override
    public boolean isRequestValid() {
        if (StringUtils.isInvalid(spoilerModel.getSpType())) {
            showWarning("Please choose spoiler-type choice");
            return false;
        } else if (StringUtils.isInvalid(spoilerModel.getMidCredit())) {
            showWarning("Please choose mid-credit scene choice");
            return false;
        } else if (StringUtils.isInvalid(spoilerModel.getStringer())) {
            showWarning("Please choose post-credit scene choice");
            return false;
        } else if (StringUtils.isInvalid(etSpoiler.getText().toString().trim())) {
            showWarning("Please add a spoiler");
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
                Intent data = new Intent();
                spoilerModel.setSpoiler(etSpoiler.getText().toString().trim());
                data.putExtra(AddSpoilerActivity.RESULT_EXTRA_MODEL, spoilerModel);
                setResult(RESULT_OK, data);
                finish();
            }
        } else
            super.onClick(v);
    }
}
