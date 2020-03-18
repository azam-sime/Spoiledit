package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.CreateSpoilerModel;
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

    private SpoilersRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestSpoilers() {
        int api = Constants.Api.SPOILERS_NEW;
        try {
            apiRequestHit(api, "Requesting spoilers...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.SPOILERS_NEW.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
//                                    spoilerFullModelsMutable.postValue(new SpoilerParser.FullsParser()
//                                            .execute(jsonObject).get());
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

    public void addSpoiler(CreateSpoilerModel createSpoilerModel) {
        int api = Constants.Api.TOKEN;
        try {
            apiRequestHit(api, "Requesting token...");
            getVolleyProvider().executeGetRequest(
                    Urls.TOKEN.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("data"));
                                else
                                    apiRequestSuccess(api, jsonObject.optString("data"));

                            } catch (Exception e) {
                                e.printStackTrace();
                                apiRequestFailure(api, NetworkUtils.getErrorString(e));
                            }
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            apiRequestFailure(api, NetworkUtils.getErrorString(volleyError));
                        }
                    }, false, false);

        } catch (Exception e) {
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }
}
