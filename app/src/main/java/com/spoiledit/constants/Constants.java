package com.spoiledit.constants;

import com.spoiledit.BuildConfig;

public final class Constants {
    private Constants() {

    }

    public static final String APP_ID = BuildConfig.APPLICATION_ID;

    public static final class Api {

        private Api() {
            // last = 48
        }

        public static final String BASE_URL = "https://spoiledit.com/wp-json/wp/v2";
        public static final String USER_URL = BASE_URL + "/users";
        public static final String MOVIE_URL = BASE_URL + "/movie-";
        public static final String MOVIE_DETAILS_URL = MOVIE_URL + "details-";

        public static final int TOKEN = 1;

        public static final int OTP_FORGOT_PASSWORD = 9;
        public static final int OTP_USER_REGISTRATION = 3;

        public static final int USER_REGISTER = 2;
        public static final int USER_LOGIN = 4;
        public static final int USER_PROFILE_GET = 5;
        public static final int USER_PROFILE_UPDATE = 6;
        public static final int USER_AVATAR_UPDATE = 7;
        public static final int USER_AVATAR_GET = 8;
        public static final int USER_LOGOUT = 45;

        public static final int PASSWORD_VERIFY_OTP = 10;
        public static final int PASSWORD_UPDATE = 11;
        public static final int PASSWORD_CHANGE = 12;

        public static final int TMDB_IMAGE_DETAILS = 13;

        public static final int PROVIDER_TERMS_CONDITIONS = 4;
        public static final int PROVIDER_PRIVACY_POLICIES = 47;
        public static final int PROVIDER_COOKIES_POLICY = 15;
        public static final int PROVIDER_ABOUT_US = 48;

        public static final int SPOILERS_NEW = 18;

        public static final int MOVIE_IMAGES = 46;
        public static final int MOVIES_POPULAR = 19;
        public static final int MOVIES_RECENTS = 20;
        public static final int MOVIES_UPCOMING = 21;
        public static final int MOVIES_THIS_WEEK = 22;

        public static final int MOVIES_DETAILS = 23;

        public static final int MOVIE_SPOILERS_FULL = 24;
        public static final int MOVIE_SPOILERS_BRIEF = 25;
        public static final int MOVIE_SPOILERS_ENDING = 26;

        public static final int MOVIE_COMMENTS = 28;
        public static final int MOVIE_COMMENT_ADD = 29;
        public static final int MOVIE_COMMENT_EDIT = 30;
        public static final int MOVIE_COMMENT_REPLY = 31;
        public static final int MOVIE_COMMENT_DELETE = 32;

        public static final int SPOILERS_FIELDS = 33;
        public static final int SPOILERS_ADD = 34;
        public static final int SPOILERS_EDIT = 35;
        public static final int SPOILERS_UPDATE = 36;

        public static final int SPOILERS_REPORT_FIELDS = 37;
        public static final int SPOILERS_REPORT_ADD = 38;

        public static final int MY_SPOILERS = 39;
        public static final int MY_SPOILER_EDIT = 40;
        public static final int MY_SPOILER_DELETE = 41;
        public static final int MY_WATCHLIST = 42;
        public static final int MY_WATCHLIST_ADD = 43;
        public static final int MY_WATCHLIST_REMOVE = 44;

        public static final int SEARCH_AUTO_COMPLETE = 16;
        public static final int SEARCH_MOVIE = 17;
        public static final int SEARCH_MOVIE_BY_TITLE = 49;
        public static final int SEARCH_MOVIE_BY_PERSON = 50;
        public static final int SEARCH_MOVIE_BY_KEYWORD = 51;
        public static final int SEARCH_MOVIE_BY_COMPANIES = 52;

        public static final String SEARCH_TITLE_ADDON = "&type=movie&pn=1";
        public static final String SEARCH_PERSON_ADDON = "&type=person&pn=1";
        public static final String SEARCH_KEYWORD_ADDON = "&type=keyword&pn=1";
        public static final String SEARCH_COMPANIES_ADDON = "&type=company&pn=1";
    }

    public static final class Broadcast {
        public static final String NETWORK_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

        private Broadcast() {

        }
    }
}
