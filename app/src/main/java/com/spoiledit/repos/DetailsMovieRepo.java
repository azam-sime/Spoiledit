package com.spoiledit.repos;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MovieDetailsModel;
import com.spoiledit.networks.VolleyProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsMovieRepo extends RootRepo {
    public static final String TAG = DetailsMovieRepo.class.getCanonicalName();

    public static DetailsMovieRepo instance;

    public static synchronized void initialise(MovieDetailsModel movieDetailsModel) {
        synchronized (TAG) {
            instance = new DetailsMovieRepo(movieDetailsModel);
            DetailsSpoilerRepo.initialise(movieDetailsModel.getId());
        }
    }

    public static DetailsMovieRepo getInstance() {
        return instance;
    }

    private MovieDetailsModel movieDetailsModel;

    private DetailsMovieRepo(MovieDetailsModel movieDetailsModel) {
        init();

        this.movieDetailsModel = movieDetailsModel;
    }

    public MovieDetailsModel getMovieDetailsModel() {
        return movieDetailsModel;
    }

    public void addToWatchList() {
        int api = Constants.Api.MY_WATCHLIST_ADD;
        try {
            apiRequestHit(api, "Adding to watchlist...");
            getVolleyProvider().executePostRequest(
                    Urls.MY_WATCHLIST_ADD.getUrl(),
                    addToWatchlistParams(),
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

    private Map<String, String> addToWatchlistParams() {
        Map<String, String> params = new HashMap<>();

        params.put("action", "add");
        params.put("user_id", String.valueOf(getUserModel().getId()));
        params.put("m_id", String.valueOf(movieDetailsModel.getId()));
        params.put("movie_name", movieDetailsModel.getTitle());

        return params;
    }
}
