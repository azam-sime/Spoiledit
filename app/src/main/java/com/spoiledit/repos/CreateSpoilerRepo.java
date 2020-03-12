package com.spoiledit.repos;

import android.content.Context;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

public class CreateSpoilerRepo extends RootRepo {
    public static final String TAG = LoginRepo.class.getCanonicalName();

    private Context context;

    public CreateSpoilerRepo(Context context) {
        this.context = context;

        init(context);
    }

    public void createSpoiler(CreateSpoilerModel createSpoilerModel) {
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
