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
        public static final int POPULAR_MOVIES = 7;
        public static final int MOVIES_RECENT_RELEASE = 8;
        public static final int MOVIES_COMING_SOON = 9;
        public static final int MOVIES_THIS_WEEK = 10;
    }

    public static final class Broadcast {
        public static final String NETWORK_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

        private Broadcast() {

        }
    }
}
