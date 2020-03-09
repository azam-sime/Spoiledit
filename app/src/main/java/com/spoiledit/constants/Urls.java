package com.spoiledit.constants;

import com.android.volley.Request;

public enum Urls {
    USER_URL(Constants.Api.USER_URL, Constants.BASE_URL + "/users", Request.Method.GET)
    , TOKEN(Constants.Api.TOKEN, Constants.BASE_URL + "/authorization?oth=gS56999pPixuB9jA38eh858lznm4bRdn", Request.Method.GET)

    , USER_SIGN_UP(Constants.Api.USER_SIGN_UP, USER_URL.getUrl() + "/register", Request.Method.GET)
    , USER_SIGN_IN(Constants.Api.USER_SIGN_IN, Constants.BASE_URL + "/sp-login", Request.Method.POST)
    , USER_PROFILE(Constants.Api.USER_PROFILE, USER_URL.getUrl() + "/", Request.Method.GET)

    , VERIFY_OTP(Constants.Api.VERIFY_OTP, Constants.BASE_URL + "/checkotp-password", Request.Method.POST)
    , FORGOT_PASSWORD(Constants.Api.FORGOT_PASSWORD, Constants.BASE_URL + "/forgot-password", Request.Method.POST)
    , UPDATE_PASSWORD(Constants.Api.UPDATE_PASSWORD, Constants.BASE_URL + "/update-password", Request.Method.POST)
    , T_AND_C(Constants.Api.T_AND_C, Constants.BASE_URL + "/forgot-password", Request.Method.POST)

    , NEW_SPOILERS(Constants.Api.NEW_SPOILERS, Constants.BASE_URL + "/newest-spoiler", Request.Method.GET)

    , MOVIES_POPULAR(Constants.Api.MOVIES_POPULAR, Constants.BASE_URL + "/popular-movies", Request.Method.GET)
    , MOVIES_RECENT_RELEASE(Constants.Api.MOVIES_RECENT_RELEASE, Constants.BASE_URL + "/movies-recent-releses", Request.Method.GET)
    , MOVIES_COMING_SOON(Constants.Api.MOVIES_COMING_SOON, Constants.BASE_URL + "/movies-coming-soon", Request.Method.GET)
    , MOVIES_THIS_WEEK(Constants.Api.MOVIES_THIS_WEEK, Constants.BASE_URL + "/new-movies-this-week", Request.Method.GET)
    , MOVIE_DETAILS(Constants.Api.MOVIES_DETAILS, Constants.BASE_URL + "/movies-details", Request.Method.GET) // append /id

    , AUTO_COMPLETE(Constants.Api.AUTO_COMPLETE, Constants.BASE_URL + "/movies-autocomplete", Request.Method.POST)
    , SEARCH_TITLE(Constants.Api.SEARCH_MOVIE, Constants.BASE_URL + "/movies-search-results?query=", Request.Method.GET)
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
