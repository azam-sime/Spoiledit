package com.spoiledit.constants;

public final class Urls {

    private Urls() {
    }

    private static final String BASE_URL = "https://spoiledit.com/wp-json/wp/v2";

    private static final String USER_URL = BASE_URL + "/users";
    public static final String USER_SIGN_UP = USER_URL + "";
    public static final String USER_SIGN_IN = USER_URL + "/me";
    public static final String USER_PROFILE = USER_URL + "/";

    public static final String FORGOT_PASSWORD = BASE_URL + "/forgot-password";

    public static final String POPULAR_MOVIES = BASE_URL + "/popular-movies";
    public static final String MOVIES_RECENT_RELEASE = BASE_URL + "/movies-recent-releses";
    public static final String MOVIES_COMING_SOON = BASE_URL + "/movies-coming-soon";
    public static final String MOVIES_THIS_WEEK = BASE_URL + "/new-movies-this-week";
}
