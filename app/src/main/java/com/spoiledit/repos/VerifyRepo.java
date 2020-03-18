package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
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
        int api = Constants.Api.USER_REGISTER_OTP;
        try {
            apiRequestHit(api, "Registering otp...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_REGISTER_OTP.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    PreferenceUtils.saveInt(context, PreferenceUtils.USER_ID,
                                            jsonObject.optInt("user_id"));
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

    public void requestOtpVerification(String[] values) {
        int api = Constants.Api.PASSWORD_VERIFY_OTP;
        try {
            apiRequestHit(api, "Confirming otp...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_REGISTER_OTP.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    PreferenceUtils.saveInt(context, PreferenceUtils.USER_ID,
                                            jsonObject.optInt("user_id"));
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

    public void requestUpdatePassword(String[] values) {
        int api = Constants.Api.PASSWORD_UPDATE;
        try {
            apiRequestHit(api, "Requesting password change...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.PASSWORD_UPDATE.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
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
            if (api == Constants.Api.USER_REGISTER_OTP) {
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
