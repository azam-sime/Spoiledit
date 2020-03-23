package com.spoiledit.models;

import android.os.Parcel;

public class SpoilersNewModel extends RootSelectionModel {
    private int mId;
    private String mName;
    private int userId;
    private int selectType;
    private String midCredit;
    private String stringer;
    private String spoiler;
    private String cratedOn;
    private String posterPath;
    private String category;
    private String username;
    private String dateFormat;

    public SpoilersNewModel() {
    }

    protected SpoilersNewModel(Parcel in) {
        super(in);
        mId = in.readInt();
        mName = in.readString();
        userId = in.readInt();
        selectType = in.readInt();
        midCredit = in.readString();
        stringer = in.readString();
        spoiler = in.readString();
        cratedOn = in.readString();
        posterPath = in.readString();
        category = in.readString();
        username = in.readString();
        dateFormat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(userId);
        dest.writeInt(selectType);
        dest.writeString(midCredit);
        dest.writeString(stringer);
        dest.writeString(spoiler);
        dest.writeString(cratedOn);
        dest.writeString(posterPath);
        dest.writeString(category);
        dest.writeString(username);
        dest.writeString(dateFormat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpoilersNewModel> CREATOR = new Creator<SpoilersNewModel>() {
        @Override
        public SpoilersNewModel createFromParcel(Parcel in) {
            return new SpoilersNewModel(in);
        }

        @Override
        public SpoilersNewModel[] newArray(int size) {
            return new SpoilersNewModel[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public String getMidCredit() {
        return midCredit;
    }

    public void setMidCredit(String midCredit) {
        this.midCredit = midCredit;
    }

    public String getStringer() {
        return stringer;
    }

    public void setStringer(String stringer) {
        this.stringer = stringer;
    }

    public String getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(String spoiler) {
        this.spoiler = spoiler;
    }

    public String getCratedOn() {
        return cratedOn;
    }

    public void setCratedOn(String cratedOn) {
        this.cratedOn = cratedOn;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "SpoilerModel{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", userId=" + userId +
                ", selectType=" + selectType +
                ", midCredit='" + midCredit + '\'' +
                ", stringer='" + stringer + '\'' +
                ", spoiler='" + spoiler + '\'' +
                ", cratedOn='" + cratedOn + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", category='" + category + '\'' +
                ", username='" + username + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                '}';
    }
}
