package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfileRepo extends RootRepo {
    public static final String TAG = ProfileRepo.class.getCanonicalName();

    private MutableLiveData<ProfileModel> profileModelMutable;

    public ProfileRepo() {
        init();

        profileModelMutable = new MutableLiveData<>();
    }

    public MutableLiveData<ProfileModel> getProfileModelMutable() {
        return profileModelMutable;
    }

    public void getProfileDetails() {
        int api = Constants.Api.USER_PROFILE_GET;
        try {
            apiRequestHit(api, "Requesting profile details...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.USER_PROFILE_GET.getUrl() + getUserModel().getId(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    profileModelMutable.postValue(new UserParser.ProfileParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                } else
                                    postError(api, jsonObject);

                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            try {
                                apiRequestFailure(api, getErrorFromVolleyAsJson(volleyError));
                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }
                    }, false, true);

        } catch (Exception e) {
            onException(api, e);
        }
    }

    public void updateProfile(String name, String email, String phone) {
        int api = Constants.Api.USER_PROFILE_UPDATE;
        try {
            apiRequestHit(api, "Updating details....");
            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_PROFILE_UPDATE.getUrl(),
                    getParams(name, email, phone),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                } else
                                    postError(api, jsonObject);

                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            try {
                                apiRequestFailure(api, getErrorFromVolleyAsJson(volleyError));
                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }
                    }, false, true);

        } catch (Exception e) {
            onException(api, e);
        }
    }

    public Map<String, String> getParams(String name, String email, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("phone", phone);

        return map;
    }

    public void updateProfilePic(String filePath) {
        int api = Constants.Api.USER_AVATAR_UPDATE;
        try {
            apiRequestHit(api, "Updating details....");

            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", StringUtils.asString(getUserModel().getId()));

            HashMap<String, File> files = new HashMap<>();
            files.put("profile-pic", new File(filePath));

            getVolleyProvider().executeMultipartRequest(
                    Urls.USER_AVATAR_UPDATE.getUrl(),
                    params, files,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString("data"));
                                } else
                                    postError(api, jsonObject);

                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            try {
                                apiRequestFailure(api, getErrorFromVolleyAsJson(volleyError));
                            } catch (Exception e) {
                                onException(api, e);
                            }
                        }
                    }, false, true);

        } catch (Exception e) {
            onException(api, e);
        }
    }
}