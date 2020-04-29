package com.spoiledit.constants;

public final class Status {
    private Status() {

    }

    public static final class Login {
        private Login() {

        }

        public static final int REQUIRE_NOTHING = 0;
        public static final int REQUIRE_REGISTER = 1;
        public static final int REQUIRE_LOGIN = 2;
    }

    public static final class Request {
        private Request() {

        }

        public static final int API_HIT = 1;
        public static final int API_SUCCESS = 2;
        public static final int API_ERROR = 3;
    }

    public static final class Response {
        private Response() {

        }

        public static final int SUCCESS = 200;
    }
}
