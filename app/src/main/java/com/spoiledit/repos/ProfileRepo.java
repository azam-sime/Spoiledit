package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.AppUtils;
import com.spoiledit.utils.NetworkUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class ProfileRepo {
    private ProfileRepo() {
    }

    public static final class DetailsRepo extends RootRepo {
        public static final String TAG = DetailsRepo.class.getCanonicalName();

        private MutableLiveData<ProfileModel> profileModelMutable;

        public DetailsRepo() {
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
                                        profileModelMutable.postValue(new UserParser.DetailsParser().execute(jsonObject).get());
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
                        }, false, true);

            } catch (Exception e) {
                setExceptionOccured(api, e);
            }
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
    }

    public static final class UpdateRepo extends RootRepo {
        public static final String TAG = UpdateRepo.class.getCanonicalName();

        public UpdateRepo() {
            init();
        }

        public void updateProfile(String name, String email, String phone) {
            int api = Constants.Api.USER_PROFILE_UPDATE;
            try {
                apiRequestHit(api, "Updating profile....");
                getVolleyProvider().executeMultipartRequest(
                        Urls.USER_PROFILE_UPDATE.getUrl() + getUserModel().getId(),
                        getParams(name, email, phone),
                        new VolleyProvider.OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (isRequestSuccess(jsonObject)) {
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
                        }, false, true);

            } catch (Exception e) {
                setExceptionOccured(api, e);
            }
        }

        public Map<String, String> getParams(String name, String email, String phone) {
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            map.put("phone", phone);

            return map;
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
                                        PreferenceUtils.saveUserModel(AppUtils.getContext(),
                                                new UserParser.EditParser().execute(jsonObject).get());
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
                        }, false, true);

            } catch (Exception e) {
                setExceptionOccured(api, e);
            }
        }
    }

    public static class ChangePasswordRepo extends RootRepo {
        public static final String TAG = ChangePasswordRepo.class.getCanonicalName();

        public ChangePasswordRepo() {
            init();
        }

        public void requestUpdatePassword(String[] values) {
            int api = Constants.Api.PASSWORD_UPDATE;
            try {
                apiRequestHit(api, "Requesting password change...");
                getVolleyProvider().executePostRequest(
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
                hashMap.put("new_password", values[0]);
                hashMap.put("user_id", String.valueOf(getUserModel().getId()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return hashMap;
        }
    }
}