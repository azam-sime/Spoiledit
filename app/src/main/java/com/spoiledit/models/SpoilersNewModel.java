package com.spoiledit.models;

import android.os.Parcel;

public class SpoilersNewModel extends RootSelectionModel {
    private int mId;
    private String mName;
    private int selectType;
    private String category;
    private String midCredit;
    private String stringer;
    private String spoiler;
    private String dateFormat;
    private String cratedOn;
    private String posterPath;
    private int userId;
    private String userName;
    private String userImage;
    private String userEmail;
    private String userPhone;


    public SpoilersNewModel() {
    }

    protected SpoilersNewModel(Parcel in) {
        super(in);
        mId = in.readInt();
        mName = in.readString();
        selectType = in.readInt();
        category = in.readString();
        midCredit = in.readString();
        stringer = in.readString();
        spoiler = in.readString();
        dateFormat = in.readString();
        cratedOn = in.readString();
        posterPath = in.readString();
        userId = in.readInt();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(selectType);
        dest.writeString(category);
        dest.writeString(midCredit);
        dest.writeString(stringer);
        dest.writeString(spoiler);
        dest.writeString(dateFormat);
        dest.writeString(cratedOn);
        dest.writeString(posterPath);
        dest.writeInt(userId);
        dest.writeString(userName);
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

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "SpoilersNewModel{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", selectType=" + selectType +
                ", category='" + category + '\'' +
                ", midCredit='" + midCredit + '\'' +
                ", stringer='" + stringer + '\'' +
                ", spoiler='" + spoiler + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", cratedOn='" + cratedOn + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }

    public SpoilerUserModel toSpoilerUserModel() {
        return new SpoilerUserModel(
                userId, userName, userImage, userEmail, userPhone
        );
    }
}
