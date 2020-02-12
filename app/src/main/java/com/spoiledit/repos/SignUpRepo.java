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

public class SignUpRepo extends RootRepo {
    public static final String TAG = SignUpRepo.class.getCanonicalName();

    private Context context;

    public SignUpRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestSignUp(String[] credentials) {
        int api = Constants.Api.USER_SIGN_UP;
        try {
            apiRequestHit(api, "Requesting sign up...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_SIGN_UP.getUrl(),
                    getParamsMap(api, credentials),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, NetworkUtils.getErrorString(volleyError));
                        }
                    },false, false);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void requestTAndC(String[] values) {
        int api = Constants.Api.T_AND_C;
        try {
            apiRequestHit(api, "Updating password...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.T_AND_C.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {

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

    private Map<String, String> getParamsMap(int api, String[] values) {
        Map<String, String> hashMap = new HashMap<>();
        try {
            if (api == Constants.Api.USER_SIGN_UP) {
                hashMap.put("username", values[0]);
                hashMap.put("email", values[0]);
                hashMap.put("password", values[1]);
                hashMap.put("name", values[2]);
                hashMap.put("phone", values[3]);

            } else if (api == Constants.Api.T_AND_C) {
                hashMap.put("email", values[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
