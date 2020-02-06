package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.StringUtils;

import org.json.JSONObject;

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
            getVolleyProvider().executePostRequest(
                    Urls.NEW_PASSWORD.getUrl(),
                    getRequestParams(api, values),
                    new VolleyProvider.OnResponseListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {

                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, StringUtils.getErrorString(volleyError));
                        }
                    }, false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, StringUtils.getErrorString(e));
        }
    }

    private JSONObject getRequestParams(int api, String[] values) {
        JSONObject jsonRequest = new JSONObject();
        try {
            if (api == Constants.Api.NEW_PASSWORD) {
                jsonRequest.put("email", values[0]);
                jsonRequest.put("password", values[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }
}
