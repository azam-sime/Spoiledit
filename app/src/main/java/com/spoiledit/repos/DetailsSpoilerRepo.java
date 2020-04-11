package com.spoiledit.repos;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.networks.VolleyProvider;
import com.spoiledit.parsers.SpoilerParser;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsSpoilerRepo extends RootRepo {
    public static final String TAG = DetailsSpoilerRepo.class.getCanonicalName();

    public static DetailsSpoilerRepo instance;

    public static synchronized void initialise(int movieId) {
        synchronized (TAG) {
            instance = new DetailsSpoilerRepo(movieId);
        }
    }

    public static DetailsSpoilerRepo getInstance() {
        return instance;
    }

    private int movieId;
    private MutableLiveData<List<SpoilerFullModel>> spoilerFullModelsMutable;
    private MutableLiveData<List<SpoilerBriefModel>> spoilerBriefModelsMutable;
    private MutableLiveData<List<SpoilerEndingModel>> spoilerEndingModelsMutable;

    private DetailsSpoilerRepo(int movieId) {
        init();

        this.movieId = movieId;

        spoilerFullModelsMutable = new MutableLiveData<>();
        spoilerBriefModelsMutable = new MutableLiveData<>();
        spoilerEndingModelsMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<SpoilerFullModel>> getSpoilerFullModelsMutable() {
        return spoilerFullModelsMutable;
    }

    public MutableLiveData<List<SpoilerBriefModel>> getSpoilerBriefModelsMutable() {
        return spoilerBriefModelsMutable;
    }

    public MutableLiveData<List<SpoilerEndingModel>> getSpoilerEndingModelsMutable() {
        return spoilerEndingModelsMutable;
    }

    public void requestSpoilerFull() {
        int api = Constants.Api.MOVIE_SPOILERS_FULL;
        try {
            apiRequestHit(api, "Requesting full spoilers...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_SPOILERS_FULL.getUrl(), getSpoilersParams(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    spoilerFullModelsMutable.postValue(new SpoilerParser.FullsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("result_data"));
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
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void requestSpoilerBrief() {
        int api = Constants.Api.MOVIE_SPOILERS_BRIEF;
        try {
            apiRequestHit(api, "Requesting brief spoilers...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_SPOILERS_BRIEF.getUrl(), getSpoilersParams(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    spoilerBriefModelsMutable.postValue(new SpoilerParser.BriefsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("result_data"));
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
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void requestSpoilerEnding() {
        int api = Constants.Api.MOVIE_SPOILERS_ENDING;
        try {
            apiRequestHit(api, "Requesting ending spoilers...");
            getVolleyProvider().executePostRequest(
                    Urls.MOVIE_SPOILERS_ENDING.getUrl(), getSpoilersParams(),
                    new VolleyProvider.OnResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (isRequestSuccess(jsonObject)) {
                                    spoilerEndingModelsMutable.postValue(new SpoilerParser.EndingsParser()
                                            .execute(jsonObject).get());
                                    apiRequestSuccess(api, jsonObject.optString("result_data"));
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
            e.printStackTrace();
            apiRequestFailure(api, NetworkUtils.getErrorString(e));
        }
    }

    public void addSpoiler(CreateSpoilerModel createSpoilerModel) {
        int api = Constants.Api.SPOILERS_ADD;
        try {
            apiRequestHit(api, "Requesting spoiler update...");
            getVolleyProvider().executePostRequest(
                    Urls.SPOILERS_ADD.getUrl(), getCreateSpoilersParams(createSpoilerModel),
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
                    }, false, false);

        } catch (Exception e) {
            setExceptionOccured(api, e);
        }
    }

    private Map<String, String> getSpoilersParams() {
        Map<String, String> map = new HashMap<>();
        map.put("m_id", String.valueOf(movieId));

        return map;
    }

    private Map<String, String> getCreateSpoilersParams(CreateSpoilerModel createSpoilerModel) {
        Map<String, String> map = new HashMap<>();
        map.put("select_type", createSpoilerModel.getSpType());
        map.put("mid_credit", createSpoilerModel.getMidCredit());
        map.put("stringer", createSpoilerModel.getStringer());
        map.put("spoiler", createSpoilerModel.getSpoiler());
        map.put("m_id", String.valueOf(movieId));
        map.put("user_id", String.valueOf(getUserModel().getId()));

        return map;
    }
}
