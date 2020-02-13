package com.spoiledit;

import android.app.Application;

import com.squareup.picasso.Picasso;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso picasso = new Picasso.Builder(this).build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        Picasso.setSingletonInstance(picasso);
    }
}
