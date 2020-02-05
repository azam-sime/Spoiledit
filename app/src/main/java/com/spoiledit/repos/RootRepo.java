package com.spoiledit.repos;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.spoiledit.constants.Status;
import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.networks.VolleyProvider;

public abstract class RootRepo {
    private VolleyProvider volleyProvider;
    private MutableLiveData<ApiStatusModel> apiStatusModelMutable;

    @CallSuper
    public void init(Context context) {
        volleyProvider = VolleyProvider.getProvider(context);
        apiStatusModelMutable = new MutableLiveData<>();
    }

    public VolleyProvider getVolleyProvider() {
        return volleyProvider;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return apiStatusModelMutable;
    }

    public void setApiStatus(ApiStatusModel apiStatusModel) {
        apiStatusModelMutable.postValue(apiStatusModel);
    }

    public void apiRequestHit(String message) {
        setApiStatus(new ApiStatusModel(Status.Request.API_HIT, message));
    }

    public void apiRequestSuccess(String message) {
        setApiStatus(new ApiStatusModel(Status.Request.API_SUCCESS, message));
    }

    public void apiRequestFailure(String message) {
        setApiStatus(new ApiStatusModel(Status.Request.API_ERROR, message));
    }
}
