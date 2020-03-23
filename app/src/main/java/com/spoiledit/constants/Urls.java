package com.spoiledit.constants;

import com.android.volley.Request;

public enum Urls {
    TOKEN(Constants.Api.TOKEN, Constants.Api.BASE_URL + "/authorization?oth=gS56999pPixuB9jA38eh858lznm4bRdn", Request.Method.GET)

    , USER_REGISTER(Constants.Api.USER_REGISTER, Constants.Api.USER_URL + "/register", Request.Method.GET)
    , USER_REGISTER_OTP(Constants.Api.USER_REGISTER_OTP, Constants.Api.BASE_URL + "/otp-register", Request.Method.POST)
    , USER_LOGIN(Constants.Api.USER_LOGIN, Constants.Api.BASE_URL + "/sp-login", Request.Method.POST)
    , USER_PROFILE_GET(Constants.Api.USER_PROFILE_GET, Constants.Api.USER_URL + "/", Request.Method.GET)
    , USER_PROFILE_UPDATE(Constants.Api.USER_PROFILE_UPDATE, Constants.Api.USER_URL + "/", Request.Method.POST)
    , USER_AVATAR_UPDATE(Constants.Api.USER_AVATAR_UPDATE, Constants.Api.BASE_URL + "/profile-picture", Request.Method.POST)
    , USER_AVATAR_GET(Constants.Api.USER_AVATAR_GET, Constants.Api.USER_URL + "/", Request.Method.GET)
    , USER_LOGOUT(Constants.Api.USER_LOGOUT, Constants.Api.BASE_URL + "/logout", Request.Method.GET)

    , PASSWORD_FORGOT(Constants.Api.PASSWORD_FORGOT, Constants.Api.BASE_URL + "/forgot-password", Request.Method.POST)
    , PASSWORD_VERIFY_OTP(Constants.Api.PASSWORD_VERIFY_OTP, Constants.Api.BASE_URL + "/checkotp-password", Request.Method.POST)
    , PASSWORD_UPDATE(Constants.Api.PASSWORD_UPDATE, Constants.Api.BASE_URL + "/update-password", Request.Method.POST)
    , PASSWORD_CHANGE(Constants.Api.PASSWORD_CHANGE, Constants.Api.USER_URL + "/", Request.Method.POST)

    , TMDB_IMAGE_DETAILS(Constants.Api.TMDB_IMAGE_DETAILS, Constants.Api.BASE_URL + "/tmdb-image-path", Request.Method.GET)
    , T_AND_C(Constants.Api.T_AND_C, Constants.Api.BASE_URL + "/pages/9", Request.Method.GET)
    , COOKIES(Constants.Api.COOKIES, Constants.Api.BASE_URL + "/pages/7", Request.Method.GET)

    , SEARCH_AUTO_COMPLETE(Constants.Api.SEARCH_AUTO_COMPLETE, Constants.Api.BASE_URL + "/movies-autocomplete", Request.Method.POST)
    , SEARCH_MOVIE(Constants.Api.SEARCH_MOVIE, Constants.Api.BASE_URL + "/movies-search-results?query=", Request.Method.GET)

    , SPOILERS_NEW(Constants.Api.SPOILERS_NEW, Constants.Api.BASE_URL + "/newest-spoiler", Request.Method.GET)

    , MOVIE_IMAGES(Constants.Api.MOVIE_IMAGES, "https://image.tmdb.org/t/p/w300", Request.Method.GET)
    , MOVIES_POPULAR(Constants.Api.MOVIES_POPULAR, Constants.Api.BASE_URL + "/popular-movies", Request.Method.GET)
    , MOVIES_RECENTS(Constants.Api.MOVIES_RECENTS, Constants.Api.BASE_URL + "/movies-recent-releses", Request.Method.GET)
    , MOVIES_UPCOMING(Constants.Api.MOVIES_UPCOMING, Constants.Api.BASE_URL + "/movies-coming-soon", Request.Method.GET)
    , MOVIES_THIS_WEEK(Constants.Api.MOVIES_THIS_WEEK, Constants.Api.BASE_URL + "/new-movies-this-week", Request.Method.GET)

    , MOVIE_DETAILS(Constants.Api.MOVIES_DETAILS, Constants.Api.BASE_URL + "/movies-details/", Request.Method.GET)

    , MOVIE_SPOILERS_FULL(Constants.Api.MOVIE_SPOILERS_FULL, Constants.Api.MOVIE_DETAILS_URL + "full-spoiler", Request.Method.POST)
    , MOVIE_SPOILERS_BRIEF(Constants.Api.MOVIE_SPOILERS_BRIEF, Constants.Api.MOVIE_DETAILS_URL + "brief-summary", Request.Method.POST)
    , MOVIE_SPOILERS_ENDING(Constants.Api.MOVIE_SPOILERS_ENDING, Constants.Api.MOVIE_DETAILS_URL + "just-the-ending", Request.Method.POST)

    , MOVIE_COMMENTS(Constants.Api.MOVIE_COMMENTS, Constants.Api.MOVIE_URL + "all-comments", Request.Method.GET)
    , MOVIE_COMMENT_ADD(Constants.Api.MOVIE_COMMENT_ADD, Constants.Api.MOVIE_URL + "add-comments", Request.Method.POST)
    , MOVIE_COMMENT_EDIT(Constants.Api.MOVIE_COMMENT_EDIT, Constants.Api.MOVIE_URL + "edit-comments", Request.Method.POST)
    , MOVIE_COMMENT_REPLY(Constants.Api.MOVIE_COMMENT_REPLY, Constants.Api.MOVIE_URL + "add-reply-comments", Request.Method.POST)
    , MOVIE_COMMENT_DELETE(Constants.Api.MOVIE_COMMENT_DELETE, Constants.Api.MOVIE_URL + "delete-comments", Request.Method.POST)

    , SPOILERS_FIELDS(Constants.Api.SPOILERS_FIELDS, Constants.Api.BASE_URL + "/spoiler-fields", Request.Method.GET)
    , SPOILERS_ADD(Constants.Api.SPOILERS_ADD, Constants.Api.BASE_URL + "/add-spoiler", Request.Method.POST)
    , SPOILERS_EDIT(Constants.Api.SPOILERS_EDIT, Constants.Api.BASE_URL + "/edit-spoiler", Request.Method.POST)
    , SPOILERS_UPDATE(Constants.Api.SPOILERS_UPDATE, Constants.Api.BASE_URL + "/update-spoiler", Request.Method.POST)

    , SPOILERS_REPORT_FIELDS(Constants.Api.SPOILERS_REPORT_FIELDS, Constants.Api.BASE_URL + "/spoiler-report-fields", Request.Method.GET)
    , SPOILERS_REPORT_ADD(Constants.Api.SPOILERS_REPORT_ADD, Constants.Api.BASE_URL + "/add-spoiler-report", Request.Method.POST)

    , MY_SPOILERS(Constants.Api.MY_SPOILERS, Constants.Api.BASE_URL + "/my-spoiler", Request.Method.POST)
    , MY_SPOILER_EDIT(Constants.Api.MY_SPOILER_EDIT, Constants.Api.BASE_URL + "/edit-spoiler", Request.Method.POST)
    , MY_SPOILER_DELETE(Constants.Api.MY_SPOILER_DELETE, Constants.Api.BASE_URL + "/delete-my-spoiler", Request.Method.POST)
    , MY_WATCHLIST(Constants.Api.MY_WATCHLIST, Constants.Api.BASE_URL + "/my-watchlist", Request.Method.POST)
    , MY_WATCHLIST_ADD(Constants.Api.MY_WATCHLIST_ADD, Constants.Api.BASE_URL + "/add-my-watchlist", Request.Method.POST)
    , MY_WATCHLIST_REMOVE(Constants.Api.MY_WATCHLIST_REMOVE, Constants.Api.BASE_URL + "/delete-my-watchlist", Request.Method.POST)
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
