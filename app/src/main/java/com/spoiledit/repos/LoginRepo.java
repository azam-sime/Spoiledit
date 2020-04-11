package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRepo extends RootRepo {
    public static final String TAG = LoginRepo.class.getCanonicalName();

    private Context context;

    public LoginRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestLogin(String[] credentials) {
        int api = Constants.Api.USER_LOGIN;
        try {
            apiRequestHit(api, "Requesting login...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_LOGIN.getUrl(),
                    getParamsMap(api, credentials),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    PreferenceUtils.saveUserModel(context, new UserParser.LoginParser().execute(response).get());
                                    apiRequestSuccess(api, "You have successfully logged in.");

                                } else {
                                    String message = jsonObject.optString("message");

                                    if (jsonObject.optString("code").equals("200"))
                                        apiRequestSuccess(api, message);
                                    else {
                                        if (jsonObject.optString("code").equals("invalid_email"))
                                            message = "You have entered an un-registered username!";
                                        else if (jsonObject.optString("code").equals("incorrect_password"))
                                            message = "You have entered an incorrect password!";
                                        apiRequestFailure(api, message);
                                    }
                                }

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
                    }, false, true);

        } catch (Exception e) {
            setExceptionOccured(api, e);
        }
    }

    public void requestForgotPasswordOtp(String[] values) {
        int api = Constants.Api.OTP_FORGOT_PASSWORD;
        try {
            apiRequestHit(api, "Requesting otp...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.OTP_FORGOT_PASSWORD.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject))
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                else
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
                    }, false, true);

        } catch (Exception e) {
            setExceptionOccured(api, e);
        }
    }

    private Map<String, String> getParamsMap(int api, String[] values) {
        Map<String, String> hashMap = new HashMap<>();
        try {
            if (api == Constants.Api.USER_LOGIN) {
                hashMap.put("username", values[0]);
                hashMap.put("password", values[1]);

            } else if (api == Constants.Api.OTP_FORGOT_PASSWORD) {
                hashMap.put("email", values[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}