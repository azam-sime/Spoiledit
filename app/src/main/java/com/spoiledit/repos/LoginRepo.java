package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.StringUtils;

import org.json.JSONObject;

public class LoginRepo extends RootRepo {
    public static final String TAG = LoginRepo.class.getCanonicalName();

    private Context context;

    public LoginRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestLogin(String[] credentials) {
        try {
            apiRequestHit("Requesting login...");
            getVolleyProvider().executePostRequest(
                    Urls.USER_SIGN_IN,
                    getSignInRequest(credentials),
                    new VolleyProvider.OnResponseListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(StringUtils.getError(volleyError));
                        }
                    },false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(StringUtils.getError(e));
        }
    }

    private JSONObject getSignInRequest(String[] credentials) {
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("username", credentials[0]);
            jsonRequest.put("password", credentials[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }

    public void requestPasswordReset(String[] credentials) {

    }
}
