package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.UserModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;

import org.json.JSONException;
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
        int api = Constants.Api.USER_SIGN_IN;
        try {
            apiRequestHit(api, "Requesting login...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_SIGN_IN.getUrl(),
                    getParamsMap(api, credentials),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.has("data")) {
                                    PreferenceUtils.saveUserModel(context, new UserParser().execute(response).get());
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
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            // For invalid user creds, there is an 500 server error
                            // hence, trying to fetch coded message from server from volley error
                            try {
                                JSONObject jsonObject = NetworkUtils.getErrorJson(volleyError);
                                String message = "";
                                if (jsonObject != null) {
                                    message = jsonObject.optString("message");

                                    if (jsonObject.optString("code").equals("200"))
                                        apiRequestSuccess(api, message);
                                    else {
                                        if (jsonObject.optString("code").equals("invalid_email"))
                                            message = "You have entered an un-registered username!";
                                        else if (jsonObject.optString("code").equals("incorrect_password"))
                                            message = "You have entered an incorrect password!";
                                    }
                                }
                                apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
                        }
                    }, false, true);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void requestPassword(String[] values) {
        int api = Constants.Api.FORGOT_PASSWORD;
        try {
            apiRequestHit(api, "Requesting password change...");
            getVolleyProvider().executeMultipartRequest(
                    Urls.FORGOT_PASSWORD.getUrl(),
                    getParamsMap(api, values),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else
                                    apiRequestSuccess(api, jsonObject.optString("message"));

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
            if (api == Constants.Api.USER_SIGN_IN) {
                hashMap.put("username", values[0]);
                hashMap.put("password", values[1]);

            } else if (api == Constants.Api.FORGOT_PASSWORD) {
                hashMap.put("email", values[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}