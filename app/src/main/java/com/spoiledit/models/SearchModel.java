package com.spoiledit.models;

import android.os.Parcel;

public class SearchModel extends MovieModel {
    private int type;

    public SearchModel() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    protected SearchModel(Parcel in) {
        super(in);
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchModel> CREATOR = new Creator<SearchModel>() {
        @Override
        public SearchModel createFromParcel(Parcel in) {
            return new SearchModel(in);
        }

        @Override
        public SearchModel[] newArray(int size) {
            return new SearchModel[size];
        }
    };

    @Override
    public String toString() {
        return "SearchMovieModel{" +
                super.toString() +
                "type=" + type +
                '}';
    }
}
