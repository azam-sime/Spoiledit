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

    public void requestComments() {
        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));

        int api = Constants.Api.MOVIE_COMMENTS;
        try {
            apiRequestHit(api, "Requesting comments...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENTS.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    commentsModelMutable.postValue(new CommentParser().execute(jsonObject).get());
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

    public void addComment(String comment) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("comment", comment);

        int api = Constants.Api.MOVIE_COMMENT_ADD;
        try {
            apiRequestHit(api, "Adding comment...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENT_ADD.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString(""));
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

    public void editComment(int commentId, String newComment) {
        Map<String, String> map = new HashMap<>();
        map.put("comment_id", String.valueOf(commentId));
        map.put("comment", newComment);

        int api = Constants.Api.MOVIE_COMMENT_EDIT;
        try {
            apiRequestHit(api, "Editing comment...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENT_EDIT.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString(""));
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

    public void removeComment(int commentId) {
        Map<String, String> map = new HashMap<>();
        map.put("comment_id", String.valueOf(commentId));

        int api = Constants.Api.MOVIE_COMMENT_DELETE;
        try {
            apiRequestHit(api, "Deleting comment...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENT_DELETE.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString(""));
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

    public void replyComment(int commentId, String reply) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("comment_id", String.valueOf(commentId));
        map.put("comment", reply);

        int api = Constants.Api.MOVIE_COMMENT_REPLY;
        try {
            apiRequestHit(api, "Replying comment...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENT_REPLY.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString(""));
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