package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.PreferenceUtils;

import org.json.JSONObject;

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
        int api = Constants.Api.USER_REGISTER;
        try {
            apiRequestHit(api, "Requesting sign up...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_REGISTER.getUrl(),
                    getParamsMap(api, credentials),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    PreferenceUtils.saveOtp(context,
                                            jsonObject.optJSONObject("data").optString("otp"));
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                } else
                                    setRequestStatusFailed(api, jsonObject);

                            } catch (Exception e) {
                                setExceptionOccured(api, e);
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            try {
                                apiRequestFailure(api, getMessageFromVolleyAsJson(volleyError));
                            } catch (Exception e) {
                                setExceptionOccured(api, e);
                            }
                        }
                    },false, true);

        } catch (Exception e) {
            setExceptionOccured(api, e);
        }
    }

    private Map<String, String> getParamsMap(int api, String[] values) {
        Map<String, String> hashMap = new HashMap<>();
        try {
            if (api == Constants.Api.USER_REGISTER) {
                hashMap.put("username", values[0]);
                hashMap.put("email", values[0]);
                hashMap.put("password", values[1]);
                hashMap.put("name", values[2]);
                hashMap.put("phone", values[3]);

            } else if (api == Constants.Api.PROVIDER_TERMS_CONDITIONS) {
                hashMap.put("email", values[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
