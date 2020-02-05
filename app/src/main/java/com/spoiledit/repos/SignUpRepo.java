package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
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
        try {
            apiRequestHit("Requesting login...");
            getVolleyProvider().executePostRequest(
                    Urls.USER_SIGN_UP,
                    getSignUpRequest(credentials),
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

    private JSONObject getSignUpRequest(String[] credentials) {
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("username", credentials[0]);
            jsonRequest.put("password", credentials[1]);
            jsonRequest.put("name", credentials[2]);
            jsonRequest.put("phone", credentials[0]);
            jsonRequest.put("email", credentials[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }

    public void requestTandC(String[] credentials) {

    }
}
