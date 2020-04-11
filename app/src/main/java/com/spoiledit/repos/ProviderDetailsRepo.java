package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

public class ProviderDetailsRepo extends RootRepo {
    public static final String TAG = ProviderDetailsRepo.class.getCanonicalName();

    private MutableLiveData<String> detailsMutableLiveData;

    public ProviderDetailsRepo() {
        detailsMutableLiveData = new MutableLiveData<>();

        init();
    }

    public MutableLiveData<String> getDetailsMutableLiveData() {
        return detailsMutableLiveData;
    }

    public void requestTermsConditions() {
        int api = Constants.Api.PROVIDER_TERMS_CONDITIONS;
        try {
            apiRequestHit(api, "Requesting terms and conditions...");
            getVolleyProvider().executeGetRequest(
                    Urls.PROVIDER_TERMS_CONDITIONS.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    detailsMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
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

    public void requestPrivacyPolicies() {
        int api = Constants.Api.PROVIDER_PRIVACY_POLICIES;
        try {
            apiRequestHit(api, "Requesting privacy policies...");
            getVolleyProvider().executeGetRequest(
                    Urls.PROVIDER_PRIVACY_POLICIES.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    detailsMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
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

    public void requestCookiesPolicy() {
        int api = Constants.Api.PROVIDER_COOKIES_POLICY;
        try {
            apiRequestHit(api, "Requesting cookies policy...");
            getVolleyProvider().executeGetRequest(
                    Urls.PROVIDER_COOKIES_POLICY.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    detailsMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
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

    public void requestAboutUs() {
        int api = Constants.Api.PROVIDER_ABOUT_US;
        try {
            apiRequestHit(api, "Requesting about us...");
            getVolleyProvider().executeGetRequest(
                    Urls.PROVIDER_ABOUT_US.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");

                                if (isRequestSuccess(jsonObject)) {
                                    detailsMutableLiveData.postValue(jsonObject.optJSONObject("data")
                                            .optString("post_content"));
                                    apiRequestSuccess(api, message);
                                } else
                                    apiRequestFailure(api, message);

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
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
}
