package com.spoiledit.repos;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.spoiledit.constants.Status;
import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.networks.MultipartRequest;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.AppUtils;

public abstract class RootRepo {
    private VolleyProvider volleyProvider;
    private MutableLiveData<ApiStatusModel> apiStatusModelMutable;

    @CallSuper
    public void init(Context context) {
        volleyProvider = VolleyProvider.getProvider(context);

        apiStatusModelMutable = new MutableLiveData<>();
    }

    @CallSuper
    public void init() {
        volleyProvider = VolleyProvider.getProvider(AppUtils.getContext());

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

    public void apiRequestHit(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_HIT, message));
    }

    public void apiRequestSuccess(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_SUCCESS, message));
    }

    public void apiRequestFailure(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_ERROR, message));
    }
}
