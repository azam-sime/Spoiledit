package com.spoiledit.constants;

public final class AppIntent {

    private AppIntent() {
    }

    public static  final class Extra {
        public static final String OTP_FOR = Constants.APP_ID + ".otp_for";
        public static final String OTP_SENT_TO = Constants.APP_ID + ".otp_sent_to";
        public static final String OTP_SENT_TO_ADDRESS = Constants.APP_ID + ".otp_sent_to_address";

        private Extra() {
        }
    }

    public static  final class Value {
        public static final int OTP_FOR_REGISTRATION = 1;
        public static final int OTP_FOR_VERIFICATION = 2;
        public static final int OTP_SENT_TO_PHONE = 3;
        public static final int OTP_SENT_TO_MAIL = 4;

        private Value() {
        }
    }

    public static  final class Result {
        public static final int IS_TC_AGREED = 1;

        private Result() {
        }
    }
}
