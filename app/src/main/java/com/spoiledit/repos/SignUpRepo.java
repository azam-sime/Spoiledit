package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.StringUtils;

import org.json.JSONObject;

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
            getVolleyProvider().executePostRequest(
                    Urls.USER_SIGN_UP.getUrl(),
                    getRequestParams(api, credentials),
                    new VolleyProvider.OnResponseListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, StringUtils.getErrorString(volleyError));
                        }
                    },false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, StringUtils.getErrorString(e));
        }
    }

    public void requestTAndC(String[] values) {
        int api = Constants.Api.T_AND_C;
        try {
            apiRequestHit(api, "Updating password...");
            getVolleyProvider().executePostRequest(
                    Urls.T_AND_C.getUrl(),
                    getRequestParams(api, values),
                    new VolleyProvider.OnResponseListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, StringUtils.getErrorString(volleyError));
                        }
                    },false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, StringUtils.getErrorString(e));
        }
    }

    private JSONObject getRequestParams(int api, String[] values) {
        JSONObject jsonRequest = new JSONObject();
        try {
            if (api == Constants.Api.USER_SIGN_UP) {
                jsonRequest.put("username", values[0]);
                jsonRequest.put("email", values[0]);
                jsonRequest.put("password", values[1]);
                jsonRequest.put("name", values[2]);
                jsonRequest.put("phone", values[3]);

            } else if (api == Constants.Api.T_AND_C) {
                jsonRequest.put("email", values[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }
}
