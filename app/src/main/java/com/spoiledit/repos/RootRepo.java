package com.spoiledit.repos;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Status;
import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.UserModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;

import org.json.JSONObject;

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

    public UserModel getUserModel() {
        return PreferenceUtils.getUserModel(AppUtils.getContext());
    }

    public VolleyProvider getVolleyProvider() {
        return volleyProvider;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return apiStatusModelMutable;
    }

    public void setApiStatus(ApiStatusModel apiStatusModel) {
        apiStatusModelMutable.setValue(apiStatusModel);
    }

    public void postApiStatus(ApiStatusModel apiStatusModel) {
        apiStatusModelMutable.postValue(apiStatusModel);
    }

    public void apiRequestHit(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_HIT, message));
    }

    public void apiRequestSuccess(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_SUCCESS, message));
    }

    public void apiRequestSuccess(int fromScreen, int api, String message) {
        setApiStatus(new ApiStatusModel(fromScreen, api, Status.Request.API_SUCCESS, message));
    }

    public void apiRequestFailure(int api, String message) {
        setApiStatus(new ApiStatusModel(api, Status.Request.API_ERROR, message));
    }

    public boolean isRequestSuccess(JSONObject jsonObject) {
        return jsonObject.optInt("status") == Status.Response.SUCCESS;
    }

    public boolean hasConnection(int api) {
        boolean hasConnection = NetworkUtils.isNetworkAvailable();
        if (!hasConnection)
            apiRequestFailure(api, "No internet connection.");
        return hasConnection;
    }

    public boolean hasData(JSONObject jsonObject) {
        return jsonObject.has("data");
    }

    public boolean hasMessageAsMessage(JSONObject jsonObject) {
        return jsonObject.has("message");
    }

    public boolean hasMessageAsResultData(JSONObject jsonObject) {
        return jsonObject.has("result_data");
    }

    public boolean hasMessageAsErrorDescription(JSONObject jsonObject) {
        return jsonObject.has("error_description");
    }

    public String getMessageFromApi(JSONObject jsonObject) {
        if (hasMessageAsMessage(jsonObject))
            return jsonObject.optString("message");
        else if (hasMessageAsResultData(jsonObject))
            return jsonObject.optString("result_data");
        else if (hasMessageAsErrorDescription(jsonObject))
            return jsonObject.optString("error_description");
        else
            return "No information available";
    }

    public String getMessageFromException(Exception exception) {
        return NetworkUtils.getErrorString(exception);
    }

    public String getMessageFromVolley(VolleyError volleyError) {
        return NetworkUtils.getErrorString(volleyError);
    }

    public String getMessageFromVolleyAsJson(VolleyError volleyError) {
        JSONObject jsonObject = NetworkUtils.getErrorJson(volleyError);
        if (jsonObject != null) {
            return getMessageFromApi(jsonObject);
        }
        return getMessageFromVolley(volleyError);
    }

    public void setDataStatus(int api, JSONObject jsonObject) {
        if (hasData(jsonObject))
            apiRequestSuccess(api, getMessageFromApi(jsonObject));
        else
            apiRequestFailure(api, getMessageFromApi(jsonObject));
    }

    public void setRequestStatusFailed(int api, JSONObject jsonObject) {
        apiRequestFailure(api, getMessageFromApi(jsonObject));
    }

    public void setExceptionOccured(int api, Exception exception) {
        exception.printStackTrace();
        apiRequestFailure(api, getMessageFromException(exception));
    }
}
