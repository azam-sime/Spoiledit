package com.spoiledit.constants;

public final class Constants {
    private Constants() {

    }

    public static final String BASE_URL = "https://spoiledit.com/wp-json/wp/v2";

    public static final class Api {

        private Api() {

        }

        public static final int TOKEN = -1;
        public static final int USER_URL = 1;

        public static final int USER_SIGN_UP = 2;
        public static final int USER_SIGN_IN = 3;
        public static final int VERIFY_OTP = 11;
        public static final int USER_PROFILE = 4;

        public static final int FORGOT_PASSWORD = 5;
        public static final int UPDATE_PASSWORD = 5;
        public static final int T_AND_C = 6;

        public static final int MOVIES_POPULAR = 7;
        public static final int MOVIES_RECENTS = 8;
        public static final int MOVIES_UPCOMING = 9;
        public static final int MOVIES_THIS_WEEK = 10;
        public static final int MOVIES_DETAILS = 11;

        public static final int SPOILERS_FULL = 14;
        public static final int SPOILERS_BRIEF = 15;
        public static final int SPOILERS_ENDING = 16;
        public static final int SPOILERS_DETAILS = 17;

        public static final int AUTO_COMPLETE = 12;
        public static final int SEARCH_MOVIE = 13;

        public static final String SEARCH_TITLE = "&type=movie&pn=1";
        public static final String SEARCH_PERSON = "&type=person&pn=1";
        public static final String SEARCH_KEYWORD = "&type=keyword&pn=1";
        public static final String SEARCH_COMPANIES = "&type=company&pn=1";
    }

    public static final class Broadcast {
        public static final String NETWORK_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

        private Broadcast() {

        }
    }
}
