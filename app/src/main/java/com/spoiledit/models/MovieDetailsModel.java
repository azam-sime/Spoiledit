package com.spoiledit.models;

import android.os.Parcel;

public class MovieDetailsModel extends MovieModel {
    private String genresStr;
    private String midCreditScene;
    private String postCreditScene;
    private String youtubeId;
    private String buyNowLink;

    public MovieDetailsModel() {

    }

    protected MovieDetailsModel(Parcel in) {
        super(in);
        genresStr = in.readString();
        midCreditScene = in.readString();
//        mcsDislikes = in.readString();
        postCreditScene = in.readString();
//        pcsDislikes = in.readString();
        youtubeId = in.readString();
        buyNowLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(genresStr);
        dest.writeString(midCreditScene);
//        dest.writeString(mcsDislikes);
        dest.writeString(postCreditScene);
//        dest.writeString(pcsDislikes);
        dest.writeString(youtubeId);
        dest.writeString(buyNowLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDetailsModel> CREATOR = new Creator<MovieDetailsModel>() {
        @Override
        public MovieDetailsModel createFromParcel(Parcel in) {
            return new MovieDetailsModel(in);
        }

        @Override
        public MovieDetailsModel[] newArray(int size) {
            return new MovieDetailsModel[size];
        }
    };

    public String getGenresStr() {
        return genresStr;
    }

    public void setGenresStr(String genresStr) {
        this.genresStr = genresStr;
    }

    public String getMidCreditScene() {
        return midCreditScene;
    }

    public void setMidCreditScene(String midCreditScene) {
        this.midCreditScene = midCreditScene;
    }

    public String getPostCreditScene() {
        return postCreditScene;
    }

    public void setPostCreditScene(String postCreditScene) {
        this.postCreditScene = postCreditScene;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getBuyNowLink() {
        return buyNowLink;
    }

    public void setBuyNowLink(String buyNowLink) {
        this.buyNowLink = buyNowLink;
    }

    @Override
    public String toString() {
        return "MovieDetailsModel{" +
                "super='" + super.toString() + '\'' +
                ", genresStr='" + genresStr + '\'' +
                ", mcsLikes='" + midCreditScene + '\'' +
                ", pcsLikes='" + postCreditScene + '\'' +
                ", youtubeId='" + youtubeId + '\'' +
                ", buyNowLink='" + buyNowLink + '\'' +
                '}';
    }

    public String getDirectorsStr() {
        StringBuilder stringBuilder = new StringBuilder();
        int l = getDirectors().length;
        for (int i = 0; i < l; i++) {
            if (i < l - 1)
                stringBuilder.append(getDirectors()[i]).append(" | ");
            else
                stringBuilder.append(getDirectors()[i]);
        }
        return stringBuilder.toString();
    }

    public String getCastsStr() {
        StringBuilder stringBuilder = new StringBuilder();
        int l = getCasts().length;
        for (int i = 0; i < l; i++) {
            if (i < l - 1)
                stringBuilder.append(getCasts()[i]).append(" | ");
            else
                stringBuilder.append(getCasts()[i]);
        }
        return stringBuilder.toString();
    }
}
