package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MovieModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.List;

public class DashboardRepo extends RootRepo {
    public static final String TAG = DashboardRepo.class.getCanonicalName();

    public static DashboardRepo dashboardRepo;

    public static synchronized DashboardRepo initialise(Context context) {
        synchronized (TAG) {
            if (dashboardRepo == null)
                dashboardRepo = new DashboardRepo(context);
        }

        return dashboardRepo;
    }

    private Context context;
    private MutableLiveData<List<MovieModel>> movieModelsMutable;

    private DashboardRepo(Context context) {
        this.context = context;

        init(context);

        movieModelsMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<MovieModel>> getMovieModelsMutable() {
        return movieModelsMutable;
    }

    public void requestPopularMovies() {
        int api = Constants.Api.POPULAR_MOVIES;
        try {
            apiRequestHit(api, "Requesting recent...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.POPULAR_MOVIES.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // key error true returns a successful api
                                if (jsonObject.optBoolean("error"))
                                    apiRequestFailure(api, jsonObject.optString("message"));
                                else {
                                    movieModelsMutable.postValue(new MovieParser().execute(response).get());
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
