package com.spoiledit.models;

import android.os.Parcel;

public class SpoilerUserModel extends UserModel {

    public SpoilerUserModel(int userId, String userName, String userPhotoUrl, String userMail, String userPhone) {
        setId(userId);
        setDisplayName(userName);
        setUrl(userPhotoUrl);
        setEmail(userMail);
        setPhone(userPhone);
    }

    protected SpoilerUserModel(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpoilerUserModel> CREATOR = new Creator<SpoilerUserModel>() {
        @Override
        public SpoilerUserModel createFromParcel(Parcel in) {
            return new SpoilerUserModel(in);
        }

        @Override
        public SpoilerUserModel[] newArray(int size) {
            return new SpoilerUserModel[size];
        }
    };

    @Override
    public String toString() {
        return "SpoilerUserModel{" +
                "displayName='" + getDisplayName() + '\'' +
                ", url='" + getUrl() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                "}";
    }
}
