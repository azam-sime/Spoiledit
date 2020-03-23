package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.UserParser;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

public class AppRepo extends RootRepo {
    public static final String TAG = LoginRepo.class.getCanonicalName();

    private static AppRepo appRepo;

    private Context context;

    public static synchronized AppRepo initialise(Context context) {
        synchronized (TAG) {
            if (appRepo == null)
                appRepo = new AppRepo(context);
        }

        return appRepo;
    }

    private AppRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void requestToken() {
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
                                if (isRequestSuccess(jsonObject))
                                    apiRequestSuccess(api, jsonObject.optString("data"));
                                else
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
                    }, false, false);

        } catch (Exception e) {
            onException(api, e);
        }
    }
}
