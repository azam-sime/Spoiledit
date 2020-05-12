package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.networks.VolleyProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepliesRepo extends RootRepo {
    public static final String TAG = RepliesRepo.class.getCanonicalName();

    public static RepliesRepo instance;

    public static synchronized void initialise(MovieSpoilerModel movieDetailsModel, CommentModel commentModel) {
        synchronized (TAG) {
            instance = new RepliesRepo(movieDetailsModel, commentModel);
        }
    }

    public static RepliesRepo getInstance() {
        return instance;
    }

    private MovieSpoilerModel movieSpoilerModel;
    private CommentModel commentModel;
    private MutableLiveData<List<CommentModel>> repliesModelMutable;

    public RepliesRepo(MovieSpoilerModel movieSpoilerModel, CommentModel commentModel) {
        init();
        this.movieSpoilerModel = movieSpoilerModel;
        this.commentModel = commentModel;
        repliesModelMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<CommentModel>> getRepliesModelMutable() {
        return repliesModelMutable;
    }

    public MovieSpoilerModel getMovieSpoilerModel() {
        return movieSpoilerModel;
    }

    public CommentModel getCommentModel() {
        return commentModel;
    }

    public void requestReplies() {
        repliesModelMutable.postValue(commentModel.getReplyModels());

        /*Map<String, String> map = new HashMap<>();
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
        }*/
    }

    public void editReply(int replyId, String newComment) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_comment_id", String.valueOf(commentModel.getId()));
        map.put("comment_id", String.valueOf(replyId));
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

    public void removeReply(int replyId) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_comment_id", String.valueOf(commentModel.getId()));
        map.put("comment_id", String.valueOf(replyId));

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

    public void addReply(String reply) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("comment_id", String.valueOf(commentModel.getId()));
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

    public void reportComment(int reportType, String reportExplanation) {
        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("spoiler_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("report_id", String.valueOf(reportType));
        map.put("parent_comment_id", String.valueOf(commentModel.getParentCommentId()));
        map.put("comment_id", String.valueOf(commentModel.getId()));
        map.put("message", reportExplanation);

        int api = Constants.Api.MOVIE_COMMENT_REPORT_ADD;
        try {
            apiRequestHit(api, "Reporting comment...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_COMMENT_REPORT_ADD.getUrl(), map,
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

    public void thumbsDownComment(boolean add) {
        Urls urls = add ? Urls.THUMBS_DOWN_COMMENT_ADD : Urls.THUMBS_DOWN_COMMENT_REMOVE;

        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("parent_comment_id", String.valueOf(commentModel.getParentCommentId()));
        map.put("comment_id", String.valueOf(commentModel.getId()));

        int api = urls.getApiId();
        try {
            apiRequestHit(api, add ? "Adding thumbs down..." : "Removing thumbs down...");
            getVolleyProvider().executePostRequest(urls.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString("msg"));
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

    public void thumbsUpComment(boolean add) {
        Urls urls = add ? Urls.THUMBS_UP_COMMENT_ADD : Urls.THUMBS_UP_COMMENT_REMOVE;

        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieSpoilerModel.getmId()));
        map.put("user_id", String.valueOf(getUserModel().getId()));
        map.put("sp_id", String.valueOf(movieSpoilerModel.getId()));
        map.put("parent_comment_id", String.valueOf(commentModel.getParentCommentId()));
        map.put("comment_id", String.valueOf(commentModel.getId()));

        int api = urls.getApiId();
        try {
            apiRequestHit(api, add ? "Adding thumbs up..." : "Removing thumbs up...");
            getVolleyProvider().executePostRequest(urls.getUrl(), map,
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    apiRequestSuccess(api, jsonObject.optString("msg"));
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