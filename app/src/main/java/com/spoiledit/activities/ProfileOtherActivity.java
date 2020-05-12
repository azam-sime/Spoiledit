package com.spoiledit.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.spoiledit.R;
import com.spoiledit.constants.App;
import com.spoiledit.models.SpoilerUserModel;
import com.spoiledit.utils.StringUtils;
import com.squareup.picasso.Picasso;

public class ProfileOtherActivity extends RootActivity {
    public static final String TAG = ProfileOtherActivity.class.getCanonicalName();

    private SpoilerUserModel spoilerUserModel;

    private TextView tvUserName, tvUserEmail, tvUserPhone;
    private ImageView ivUserPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spoilerUserModel = getIntent().getParcelableExtra(App.Intent.Extra.USER_SPOILER);
        setContentView(R.layout.activity_profile_other);
    }

    @Override
    public void setUpToolBar() {
        setupBackIconOnly();
    }

    @Override
    public void initUi() {
        tvUserName = findViewById(R.id.tv_name);
        tvUserEmail = findViewById(R.id.tv_email_address);
        tvUserPhone = findViewById(R.id.tv_phone_number);

        ivUserPhoto = findViewById(R.id.riv_user);
    }

    @Override
    public void initialiseListener() {

    }

    @Override
    public void addObservers() {

    }

    @Override
    public void setData() {
        tvUserName.setText(spoilerUserModel.getDisplayName());
        StringUtils.setText(tvUserEmail, spoilerUserModel.getEmail(), "N/A");
        StringUtils.setText(tvUserPhone, spoilerUserModel.getPhone(), "N/A");

        loadProfileImage(spoilerUserModel.getUrl());
    }

    private void loadProfileImage(String filePath) {
        try {
            Picasso.get()
                    .load(filePath)
                    .placeholder(R.drawable.ic_placeholder)
                    .resize(180, 180)
                    .centerCrop()
                    .into(ivUserPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
