package com.spoiledit.constants;

public final class App {

    private App() {
    }

    public static final class Usage {
        public static final int ANY = 0x08;
        public static final int LOCATION = 0x09;
        public static final int CAMERA = 0x0A;
        public static final int GALLERY = 0x0B;
        public static final int BROWSER = 0x0C;
        public static final int STORAGE = 0x0D;
        public static final int CALL = 0x0E;

        private Usage() {
        }
    }

    public static final class SystemIntent {
        public static final int CAMERA = 0x0D;
        public static final int GALLERY = 0x0E;
        public static final int BROWSER = 0x0F;

        private SystemIntent() {
        }
    }

    public static final class Directory {
        public static final int ROOT = 0;
        public static final int MOVIES = 1;
        public static final int SPOILERS = 2;
        public static final int DATABASE = 3;
        public static final int BACK_UP = 4;
        public static final int PROFILE = 5;

        private Directory() {
        }
    }

    public static final class Intent {

        private Intent() {
        }

        public static  final class Request {
            public static final int LOGIN = 1000;
            public static final int REGISTER = 1003;
            public static final int USER_PROFILE_EDIT = 1001;
            public static final int ADD_SPOILER = 1002;

            private Request() {
            }
        }

        public static  final class Extra {
            public static final String REQUEST_LOGIN_FROM_POPCORN = Constants.APP_ID + ".request_sign_in_from_popcorn";

            public static final String OTP_FOR = Constants.APP_ID + ".otp_for";
            public static final String OTP_SENT_TO = Constants.APP_ID + ".otp_sent_to";
            public static final String OTP_SENT_TO_ADDRESS = Constants.APP_ID + ".otp_sent_to_address";

            public static final String PROVIDER_DETAILS = Constants.APP_ID + ".tc_and_pp";
            public static final String PROVIDER_DETAILS_DISPLAY_ONLY = Constants.APP_ID + ".tc_and_pp_display_only";

            private Extra() {
            }
        }

        public static  final class Value {
            public static final int OTP_FOR_REGISTRATION = 2000;
            public static final int OTP_FOR_PASSWORD_VERIFICATION = 2001;
            public static final int OTP_SENT_TO_PHONE = 2002;
            public static final int OTP_SENT_TO_MAIL = 2003;

            public static final int FOR_TC = 2005;
            public static final int FOR_PP = 2006;
            public static final int FOR_CP = 2007;
            public static final int FOR_AU = 2008;

            private Value() {
            }
        }

        public static  final class Result {
            public static final int IS_TC_AGREED = 3000;

            private Result() {
            }
        }
    }
}
