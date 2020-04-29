package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SpoilersNewModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.parsers.SearchParser;
import com.spoiledit.parsers.SpoilerParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardRepo extends RootRepo {
    public static final String TAG = DashboardRepo.class.getCanonicalName();

    private MutableLiveData<List<MoviePopularModel>> moviePopularModelsMutable;
    private MutableLiveData<List<MovieRecentModel>> movieRecentModelsMutable;
    private MutableLiveData<List<MovieUpcomingModel>> movieUpcomingModelsMutable;
    private MutableLiveData<List<SpoilersNewModel>> spoilerModelsMutable;

    private MutableLiveData<List<String>> searchValues;

    public DashboardRepo() {
        init();

        moviePopularModelsMutable = new MutableLiveData<>();
        movieRecentModelsMutable = new MutableLiveData<>();
        movieUpcomingModelsMutable = new MutableLiveData<>();
        spoilerModelsMutable = new MutableLiveData<>();

        searchValues = new MutableLiveData<>();
    }

    public MutableLiveData<List<MoviePopularModel>> getMoviePopularModelsMutable() {
        return moviePopularModelsMutable;
    }

    public MutableLiveData<List<MovieRecentModel>> getMovieRecentModelsMutable() {
        return movieRecentModelsMutable;
    }

    public MutableLiveData<List<MovieUpcomingModel>> getMovieUpcomingModelsMutable() {
        return movieUpcomingModelsMutable;
    }

    public MutableLiveData<List<SpoilersNewModel>> getSpoilerModelsMutable() {
        return spoilerModelsMutable;
    }

    public MutableLiveData<List<String>> getSearchValues() {
        return searchValues;
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
                                if (isRequestSuccess(jsonObject)) {
                                    moviePopularModelsMutable.postValue(new MovieParser.PopularsParser()
                                            .execute(jsonObject).get());
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

    public void requestMoviesRecent() {
        int api = Constants.Api.MOVIES_RECENTS;
        try {
            apiRequestHit(api, "Requesting recent releases...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.MOVIES_RECENTS.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    movieRecentModelsMutable.postValue(new MovieParser.RecentsParser()
                                            .execute(jsonObject).get());
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

    public void requestMoviesSoon() {
        int api = Constants.Api.MOVIES_UPCOMING;
        try {
            apiRequestHit(api, "Requesting upcomings...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.MOVIES_UPCOMING.getUrl(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    movieUpcomingModelsMutable.postValue(new MovieParser.UpcomingsParser()
                                            .execute(jsonObject).get());
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
                                if (isRequestSuccess(jsonObject)) {
                                    spoilerModelsMutable.postValue(new SpoilerParser.NewParser()
                                            .execute(jsonObject).get());
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

    public void requestMovieDetails(int movieId, int fromTab) {
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
                                    apiRequestSuccess(fromTab, api, jsonObject.optString("message"));
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

    public void requestSearchAutoCompleteValues(String searchValue) {
        int api = Constants.Api.SEARCH_AUTO_COMPLETE;
        try {
            apiRequestHit(api, "Requesting values...");
            getVolleyProvider().executePostRequest(
                    Urls.SEARCH_AUTO_COMPLETE.getUrl(), getAutoCompleteParams(searchValue),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    searchValues.postValue(new SearchParser.QueryParser().execute(jsonObject).get());
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

    private Map<String, String> getAutoCompleteParams(String searchValue) {
        Map<String, String> map = new HashMap<>();
        map.put("searchtext", searchValue);
        return map;
    }

    public void requestLogout() {
        int api = Constants.Api.USER_LOGOUT;
        try {
            apiRequestHit(api, "Requesting logout...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.USER_LOGOUT.getUrl() + getUserModel().getId(),
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
}
