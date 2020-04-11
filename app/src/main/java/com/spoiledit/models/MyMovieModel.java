package com.spoiledit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMovieModel extends MovieModel {
    private boolean darkBackground;

    public MyMovieModel() {

    }

    protected MyMovieModel(Parcel in) {
        super(in);
        darkBackground = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (darkBackground ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyMovieModel> CREATOR = new Creator<MyMovieModel>() {
        @Override
        public MyMovieModel createFromParcel(Parcel in) {
            return new MyMovieModel(in);
        }

        @Override
        public MyMovieModel[] newArray(int size) {
            return new MyMovieModel[size];
        }
    };

    public boolean isDarkBackground() {
        return darkBackground;
    }

    public void setDarkBackground(boolean darkBackground) {
        this.darkBackground = darkBackground;
    }

    @Override
    public String toString() {
        return "MyMovieModel{" +
                super.toString() +
                '}';
    }
}
