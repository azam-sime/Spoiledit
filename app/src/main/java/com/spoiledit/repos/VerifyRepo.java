package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyRepo extends RootRepo {
    public static final String TAG = VerifyRepo.class.getCanonicalName();

    private Context context;

    public VerifyRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestOtpRegistration(String[] values) {
        int api = Constants.Api.OTP_USER_REGISTRATION;
        try {
            apiRequestHit(api, "Registering otp...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.OTP_USER_REGISTRATION.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    PreferenceUtils.saveUserModel(context, new UserParser.VerificationParser().execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                } else
                                    setRequestStatusFailed(api, jsonObject);

                            } catch (Exception e) {
                                setExceptionOccured(api, e);
                            }
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

    public void requestOtpVerification(String[] values) {
        int api = Constants.Api.PASSWORD_VERIFY_OTP;
        try {
            apiRequestHit(api, "Confirming otp...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.OTP_USER_REGISTRATION.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    PreferenceUtils.saveUserId(context, jsonObject.optInt("user_id"));
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
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
            if (api == Constants.Api.OTP_USER_REGISTRATION) {
                hashMap.put("email", values[0]);
                hashMap.put("otp", values[1]);

            } else if (api == Constants.Api.PASSWORD_UPDATE) {
//                hashMap.put("new_password", values[0]);
//                hashMap.put("user_id", StringUtils.asString(
//                        PreferenceUtils.getInt(context, PreferenceUtils.USER_ID)));

                hashMap.put("email", values[0]);
                hashMap.put("otp", values[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
