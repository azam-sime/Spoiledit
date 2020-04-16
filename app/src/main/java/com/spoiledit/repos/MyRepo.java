package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MyMovieModel;
import com.spoiledit.models.MySpoilerModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.parsers.SpoilerParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRepo {
    public static final class MySpoilersRepo extends RootRepo {
        public static final String TAG = MySpoilersRepo.class.getCanonicalName();

        private MutableLiveData<List<MySpoilerModel>> mySpoilersMutable;

        public MySpoilersRepo() {
            init();

            mySpoilersMutable = new MutableLiveData<>();
        }

        public MutableLiveData<List<MySpoilerModel>> getMySpoilersMutable() {
            return mySpoilersMutable;
        }

        public void requestMySpoilers() {
            int api = Constants.Api.MY_SPOILERS;
            try {
                apiRequestHit(api, "Requesting my spoilers...");
                getVolleyProvider().executePostRequest(
                        Urls.MY_SPOILERS.getUrl(), getParams(),
                        new VolleyProvider.OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (isRequestSuccess(jsonObject)) {
                                        List<MySpoilerModel> mySpoilerModels = new SpoilerParser.MySpoilersParser().execute(jsonObject).get();
                                        mySpoilersMutable.postValue(mySpoilerModels);
                                        if (mySpoilerModels.size() > 0)
                                            apiRequestSuccess(api, jsonObject.optString("mssg"));
                                        else
                                            apiRequestFailure(api, jsonObject.optString("msg"));
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

        private Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(getUserModel().getId()));
            return params;
        }
    }

    public static final class MyMoviesRepo extends RootRepo {
        public static final String TAG = MySpoilersRepo.class.getCanonicalName();

        private MutableLiveData<List<MyMovieModel>> myMoviesMutable;

        public MyMoviesRepo() {
            init();

            myMoviesMutable = new MutableLiveData<>();
        }

        public MutableLiveData<List<MyMovieModel>> getMyMoviesMutable() {
            return myMoviesMutable;
        }

        public void requestMyMovies() {
            int api = Constants.Api.MY_WATCHLIST;
            try {
                apiRequestHit(api, "Requesting my movies...");
                getVolleyProvider().executePostRequest(
                        Urls.MY_WATCHLIST.getUrl(), getParams(),
                        new VolleyProvider.OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (isRequestSuccess(jsonObject)) {
                                        List<MyMovieModel> myMovieModels = new MovieParser.MyMoviesParser().execute(jsonObject).get();
                                        myMoviesMutable.postValue(myMovieModels);
                                        if (myMovieModels.size() > 0)
                                            apiRequestSuccess(api, jsonObject.optString("mssg"));
                                        else
                                            apiRequestFailure(api, jsonObject.optString("msg"));
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

        private Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "all");
            params.put("user_id", String.valueOf(getUserModel().getId()));
            return params;
        }

        public void requestMovieDetails(int movieId) {
            int api = Constants.Api.MOVIES_DETAILS;
            try {
                apiRequestHit(api, "Requesting movie details...");
                getVolleyProvider().executeMultipartGetRequest(
                        Urls.MOVIE_DETAILS.getUrl() + movieId,
                        new VolleyProvider.OnResponseListener<String>() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (isRequestSuccess(jsonObject)) {
                                        DetailsMovieRepo.initialise(
                                                new MovieParser.DetailsParser().execute(jsonObject).get()
                                        );
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
}
