package com.spoiledit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.AppRepo;

public class SplashViewModel extends AndroidViewModel {
    private AppRepo appRepo;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.appRepo = AppRepo.initialise(application);
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return appRepo.getApiStatusModelMutable();
    }

    public void requestToken() {
        appRepo.requestToken();
    }
}
