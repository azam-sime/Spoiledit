package com.spoiledit.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.List;

public class MoviesRepo extends RootRepo {
    public static final String TAG = MoviesRepo.class.getCanonicalName();

    public static MoviesRepo dashboardRepo;

    public static synchronized MoviesRepo initialise(Context context) {
        synchronized (TAG) {
            if (dashboardRepo == null)
                dashboardRepo = new MoviesRepo(context);
        }

        return dashboardRepo;
    }

    private Context context;

    private MutableLiveData<List<MoviePopularModel>> moviePopularModelsMutable;
    private MutableLiveData<List<MovieRecentModel>> movieRecentModelsMutable;
    private MutableLiveData<List<MovieUpcomingModel>> movieSoonModelsMutable;

    private MoviesRepo(Context context) {
        this.context = context;

        init(context);

        moviePopularModelsMutable = new MutableLiveData<>();
        movieRecentModelsMutable = new MutableLiveData<>();
        movieSoonModelsMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<MoviePopularModel>> getMoviePopularModelsMutable() {
        return moviePopularModelsMutable;
    }

    public MutableLiveData<List<MovieRecentModel>> getMovieRecentModelsMutable() {
        return movieRecentModelsMutable;
    }

    public MutableLiveData<List<MovieUpcomingModel>> getMovieSoonModelsMutable() {
        return movieSoonModelsMutable;
    }

    public void requestMoviesPopular() {
        int api = Constants.Api.MOVIES_POPULAR;
        try {
            apiRequestHit(api, "Requesting populars...");
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
                                    moviePopularModelsMutable.postValue(new MovieParser.PopularsParser()
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

    public void requestMoviesRecent() {
        int api = Constants.Api.MOVIES_RECENTS;
        try {
            apiRequestHit(api, "Requesting recent releases...");
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
                                    movieRecentModelsMutable.postValue(new MovieParser.RecentsParser()
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

    public void requestMoviesSoon() {
        int api = Constants.Api.MOVIES_UPCOMING;
        try {
            apiRequestHit(api, "Requesting upcomings...");
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
                                    movieSoonModelsMutable.postValue(new MovieParser.UpcomingsParser()
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
