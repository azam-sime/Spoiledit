package com.spoiledit.models;

import android.os.Parcel;

public class ApiStatusModel extends RootModel {
    private int status;
    private String message;

    public ApiStatusModel() {

    }

    public ApiStatusModel(int status, String message) {
        this.status = status;
        this.message = message;
    }


    protected ApiStatusModel(Parcel in) {
        status = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiStatusModel> CREATOR = new Creator<ApiStatusModel>() {
        @Override
        public ApiStatusModel createFromParcel(Parcel in) {
            return new ApiStatusModel(in);
        }

        @Override
        public ApiStatusModel[] newArray(int size) {
            return new ApiStatusModel[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiStatusModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
