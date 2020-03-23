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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spoiler);
    }

    @Override
    public void setUpToolBar() {

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
    }

    @Override
    public boolean isRequestValid() {
        if (rgSpoilerType.getCheckedRadioButtonId() != R.id.rb_spoiler_full
                && rgSpoilerType.getCheckedRadioButtonId() != R.id.rb_spoiler_brief
                && rgSpoilerType.getCheckedRadioButtonId() != R.id.rb_spoiler_ending) {
            showWarning("Please choose spoiler-type choice");
            return false;
        } else if (rgMidScene.getCheckedRadioButtonId() != R.id.rbtn_mc_ns
                && rgMidScene.getCheckedRadioButtonId() != R.id.rbtn_mc_yes
                && rgMidScene.getCheckedRadioButtonId() != R.id.rbtn_mc_no) {
            showWarning("Please choose mid-credit scene choice");
            return false;
        } else if (rgStringer.getCheckedRadioButtonId() != R.id.rbtn_pc_ns
                && rgStringer.getCheckedRadioButtonId() != R.id.rbtn_pc_yes
                && rgStringer.getCheckedRadioButtonId() != R.id.rbtn_pc_no) {
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
                data.putExtra(AddSpoilerActivity.RESULT_EXTRA_MODEL, new CreateSpoilerModel(
                        ((RadioButton) findViewById(rgSpoilerType.getCheckedRadioButtonId())).getText().toString(),
                        ((RadioButton) findViewById(rgMidScene.getCheckedRadioButtonId())).getText().toString(),
                        ((RadioButton) findViewById(rgStringer.getCheckedRadioButtonId())).getText().toString(),
                        etSpoiler.getText().toString().trim()
                ));
                setResult(RESULT_OK, data);
                finish();
            }
        } else
            super.onClick(v);
    }
}
