package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.parsers.SpoilerParser;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.List;

public class SpoilersRepo extends RootRepo {
    public static final String TAG = SpoilersRepo.class.getCanonicalName();

    public static SpoilersRepo spoilersRepo;

    public static synchronized SpoilersRepo initialise(Context context) {
        synchronized (TAG) {
            if (spoilersRepo == null)
                spoilersRepo = new SpoilersRepo(context);
        }

        return spoilersRepo;
    }

    private Context context;

    private MutableLiveData<List<SpoilerFullModel>> spoilerFullModelsMutable;
    private MutableLiveData<List<SpoilerBriefModel>> spoilerBriefModelsMutable;
    private MutableLiveData<List<SpoilerEndingModel>> spoilerEndingModelsMutable;

    private SpoilersRepo(Context context) {
        this.context = context;

        init(context);

        spoilerFullModelsMutable = new MutableLiveData<>();
        spoilerBriefModelsMutable = new MutableLiveData<>();
        spoilerEndingModelsMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<SpoilerFullModel>> getSpoilerFullModelsMutable() {
        return spoilerFullModelsMutable;
    }

    public MutableLiveData<List<SpoilerBriefModel>> getSpoilerBriefModelsMutable() {
        return spoilerBriefModelsMutable;
    }

    public MutableLiveData<List<SpoilerEndingModel>> getSpoilerEndingModelsMutable() {
        return spoilerEndingModelsMutable;
    }

    public void requestSpoilerFull() {
        int api = Constants.Api.SPOILERS_FULL;
        try {
            apiRequestHit(api, "Requesting full spoilers...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.MOVIES_POPULAR.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    spoilerFullModelsMutable.postValue(new SpoilerParser.FullsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
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
                                if (jsonObject != null) {
                                    if (jsonObject.optString("code").equals("200"))
                                        apiRequestSuccess(api, jsonObject.optString("message"));
                                    else
                                        apiRequestFailure(api, jsonObject.optString("error_description"));
                                } else
                                    apiRequestFailure(api, "Couldn't read response");

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

    public void requestSpoilerBrief() {
        int api = Constants.Api.SPOILERS_BRIEF;
        try {
            apiRequestHit(api, "Requesting brief spoilers...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.MOVIES_RECENT_RELEASE.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    spoilerBriefModelsMutable.postValue(new SpoilerParser.BriefsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
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
                                if (jsonObject != null) {
                                    if (jsonObject.optString("code").equals("200"))
                                        apiRequestSuccess(api, jsonObject.optString("message"));
                                    else
                                        apiRequestFailure(api, jsonObject.optString("error_description"));
                                } else
                                    apiRequestFailure(api, "Couldn't read response");

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

    public void requestSpoilerEnding() {
        int api = Constants.Api.SPOILERS_ENDING;
        try {
            apiRequestHit(api, "Requesting ending spoilers...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.MOVIES_RECENT_RELEASE.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    spoilerEndingModelsMutable.postValue(new SpoilerParser.EndingsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
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
                                if (jsonObject != null) {
                                    if (jsonObject.optString("code").equals("200"))
                                        apiRequestSuccess(api, jsonObject.optString("message"));
                                    else
                                        apiRequestFailure(api, jsonObject.optString("error_description"));
                                } else
                                    apiRequestFailure(api, "Couldn't read response");

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
}
