package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.CommentParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentsRepo extends RootRepo {
    public static final String TAG = CommentsRepo.class.getCanonicalName();

    public static CommentsRepo instance;

    public static synchronized void initialise(MovieSpoilerModel movieDetailsModel) {
        synchronized (TAG) {
            instance = new CommentsRepo(movieDetailsModel);
        }
    }

    public static CommentsRepo getInstance() {
        return instance;
    }

    private MovieSpoilerModel movieSpoilerModel;
    private MutableLiveData<List<CommentModel>> commentsModelMutable;

    public CommentsRepo(MovieSpoilerModel movieSpoilerModel) {
        init();
        this.movieSpoilerModel = movieSpoilerModel;
        commentsModelMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<CommentModel>> getCommentsModelMutable() {
        return commentsModelMutable;
    }

    public MovieSpoilerModel getMovieSpoilerModel() {
        return movieSpoilerModel;
    }

    public void sendComment(int commentId, String message) {

    }

    public void requestComments() {
        int api = Constants.Api.MOVIE_COMMENTS;
        try {
            apiRequestHit(api, "Requesting comments...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENTS.getUrl(), getCommentsParams(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    commentsModelMutable.postValue(new CommentParser().execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("message"));
                                } else
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
                    }, false, true);

        } catch (Exception e) {
            onException(api, e);
        }
    }

    public void addComment() {

    }

    public void replyComment() {

    }

    private Map<String, String> getCommentsParams() {
        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));

        return map;
    }
}