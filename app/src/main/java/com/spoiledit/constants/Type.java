package com.spoiledit.constants;

public final class Type {
    private Type() {

    }

    public static final class Parse {
        private Parse() {

        }

        public static final int JSON_ARRAY = 1;
        public static final int JSON_OBJECT = 2;
    }

    public static final class Multipart {
        private Multipart() {

        }

        public static final int USER_PHOTO = 1;
    }

    public static final class Info {
        private Info() {
        }

        public static final int OOPS = 0x10;
        public static final int SORRY = 0x11;
        public static final int SMILES = 0x12;
        public static final int HEY = 0x13;
        public static final int SURE = 0x14;
        public static final int LOGOUT = 0x15;
        public static final int UPDATE = 0x16;
        public static final int CUSTOM = 0x17;
    }
}
