package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.SearchMovieModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.MovieParser;
import com.spoiledit.parsers.SearchParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRepo extends RootRepo {
    public static final String TAG = SearchRepo.class.getCanonicalName();

    public static SearchRepo instance;

    public static synchronized SearchRepo initialise(String searchValue) {
        synchronized (TAG) {
            instance = new SearchRepo(searchValue);
        }
        return instance;
    }

    public static SearchRepo getInstance() {
        return instance;
    }

    private String searchValue;

    private MutableLiveData<List<String>> searchValues;
    private MutableLiveData<List<SearchMovieModel>> moviesByTitleMutable;
    private MutableLiveData<List<SearchMovieModel>> moviesByPersonMutable;
    private MutableLiveData<List<SearchMovieModel>> moviesByKeywordMutable;
    private MutableLiveData<List<SearchMovieModel>> moviesByCompaniesMutable;

    private SearchRepo(String searchValue) {
        init();
        this.searchValue = searchValue;

        searchValues = new MutableLiveData<>();

        moviesByTitleMutable = new MutableLiveData<>();
        moviesByPersonMutable = new MutableLiveData<>();
        moviesByKeywordMutable = new MutableLiveData<>();
        moviesByCompaniesMutable = new MutableLiveData<>();
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public MutableLiveData<List<String>> getSearchValues() {
        return searchValues;
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByTitleMutable() {
        return moviesByTitleMutable;
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByPersonMutable() {
        return moviesByPersonMutable;
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByKeywordMutable() {
        return moviesByKeywordMutable;
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByCompaniesMutable() {
        return moviesByCompaniesMutable;
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
                                    searchValues.postValue(new SearchParser().execute(jsonObject).get());
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

    public void requestMoviesByTitle() {
        int api = Constants.Api.SEARCH_MOVIE_BY_TITLE;
        try {
            apiRequestHit(api, "Requesting movies by title...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.SEARCH_MOVIE.getUrl() + searchValue.replace(" ", "%20") + Constants.Api.SEARCH_TITLE_ADDON,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    moviesByTitleMutable.postValue(new MovieParser.SearchParser()
                                            .execute(jsonObject).get());
                                    setDataStatus(api, jsonObject);
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

    public void requestMoviesByPerson() {
        int api = Constants.Api.SEARCH_MOVIE_BY_PERSON;
        try {
            apiRequestHit(api, "Requesting movies by person...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.SEARCH_MOVIE.getUrl() + searchValue.replace(" ", "%20") + Constants.Api.SEARCH_PERSON_ADDON,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    moviesByPersonMutable.postValue(new MovieParser.SearchParser()
                                            .execute(jsonObject).get());
                                    setDataStatus(api, jsonObject);
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

    public void requestMoviesByKeyword() {
        int api = Constants.Api.SEARCH_MOVIE_BY_KEYWORD;
        try {
            apiRequestHit(api, "Requesting movies by keyword...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.SEARCH_MOVIE.getUrl() + searchValue.replace(" ", "%20") + Constants.Api.SEARCH_KEYWORD_ADDON,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    moviesByKeywordMutable.postValue(new MovieParser.SearchParser()
                                            .execute(jsonObject).get());
                                    setDataStatus(api, jsonObject);
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

    public void requestMoviesByCompanies() {
        int api = Constants.Api.SEARCH_MOVIE_BY_COMPANIES;
        try {
            apiRequestHit(api, "Requesting movies by companies...");
            getVolleyProvider().executeMultipartGetRequest(
                    Urls.SEARCH_MOVIE.getUrl() + searchValue.replace(" ", "%20") + Constants.Api.SEARCH_COMPANIES_ADDON,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    moviesByCompaniesMutable.postValue(new MovieParser.SearchParser()
                                            .execute(jsonObject).get());
                                    setDataStatus(api, jsonObject);
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
