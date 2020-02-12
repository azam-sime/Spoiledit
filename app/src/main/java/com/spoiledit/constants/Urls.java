package com.spoiledit.constants;

import com.android.volley.Request;

public enum Urls {
    USER_URL(Constants.Api.USER_URL, Constants.BASE_URL + "/users", Request.Method.GET)
    , TOKEN(Constants.Api.TOKEN, Constants.BASE_URL + "/authorization?oth=gS56999pPixuB9jA38eh858lznm4bRdn", Request.Method.GET)
    , USER_SIGN_UP(Constants.Api.USER_SIGN_UP, USER_URL.getUrl() + "/register", Request.Method.GET)
    , USER_SIGN_IN(Constants.Api.USER_SIGN_IN, Constants.BASE_URL + "/sp-login", Request.Method.POST)
    , USER_PROFILE(Constants.Api.USER_PROFILE, USER_URL.getUrl() + "/", Request.Method.GET)
    , FORGOT_PASSWORD(Constants.Api.FORGOT_PASSWORD, Constants.BASE_URL + "/forgot-password", Request.Method.POST)
    , NEW_PASSWORD(Constants.Api.NEW_PASSWORD, Constants.BASE_URL + "/forgot-password", Request.Method.POST)
    , T_AND_C(Constants.Api.T_AND_C, Constants.BASE_URL + "/forgot-password", Request.Method.POST)
    , POPULAR_MOVIES(Constants.Api.POPULAR_MOVIES, Constants.BASE_URL + "/popular-movies", Request.Method.POST)
    , MOVIES_RECENT_RELEASE(Constants.Api.MOVIES_RECENT_RELEASE, Constants.BASE_URL + "/movies-recent-releses", Request.Method.POST)
    , MOVIES_COMING_SOON(Constants.Api.MOVIES_COMING_SOON, Constants.BASE_URL + "/movies-coming-soon", Request.Method.POST)
    , MOVIES_THIS_WEEK(Constants.Api.MOVIES_THIS_WEEK, Constants.BASE_URL + "/new-movies-this-week", Request.Method.POST)
    ;

    private int apiId;
    private String url;
    private int type;

    Urls(int apiId, String url, int type) {
        this.apiId = apiId;
        this.url = url;
        this.type = type;
    }

    public int getApiId() {
        return apiId;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }
}
