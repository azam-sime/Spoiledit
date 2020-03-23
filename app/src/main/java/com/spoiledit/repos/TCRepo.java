package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TCRepo extends RootRepo {
    public static final String TAG = TCRepo.class.getCanonicalName();

    private Context context;
    private MutableLiveData<String> stringTCMutableLiveData;

    public TCRepo(Context context) {
        this.context = context;
        stringTCMutableLiveData = new MutableLiveData<>();

        init(context);
    }

    public MutableLiveData<String> getStringTCMutableLiveData() {
        return stringTCMutableLiveData;
    }

    public void requestTAndC() {
        int api = Constants.Api.T_AND_C;
        try {
            apiRequestHit(api, "Requesting T&C details...");
            getVolleyProvider().executeGetRequest(
                    Urls.T_AND_C.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    stringTCMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, NetworkUtils.getErrorString(volleyError));
                        }
                    },false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void requestCookies() {
        int api = Constants.Api.COOKIES;
        try {
            apiRequestHit(api, "Requesting cookies details...");
            getVolleyProvider().executeGetRequest(
                    Urls.COOKIES.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    stringTCMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, NetworkUtils.getErrorString(volleyError));
                        }
                    },false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }
}
