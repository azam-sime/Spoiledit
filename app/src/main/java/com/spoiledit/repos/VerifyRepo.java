package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class VerifyRepo extends RootRepo {
    public static final String TAG = VerifyRepo.class.getCanonicalName();

    private Context context;

    public VerifyRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestNewPassword(String[] values) {
        int api = Constants.Api.NEW_PASSWORD;
        try {
            apiRequestHit(api, "Requesting password change...");
            getVolleyProvider().executeUrlEncodedRequest(
                    Urls.NEW_PASSWORD.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, NetworkUtils.getErrorString(volleyError));
                        }
                    }, false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    private Map<String, String> getParamsMap(int api, String[] values) {
        Map<String, String> hashMap = new HashMap<>();
        try {
            if (api == Constants.Api.NEW_PASSWORD) {
                hashMap.put("email", values[0]);
                hashMap.put("password", values[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
