package com.spoiledit.models;

import android.os.Parcel;

import com.spoiledit.utils.NumericUtils;

public class MovieSpoilerModel extends RootSelectionModel {
    private int mId;
    private String mName;
    private int selectType;
    private String midCredit;
    private String stringer;
    private String spoiler;
    private String cratedOn;
    private String date;
    private String description;
    private String thumbsUp;
    private String thumbsDown;
    private int userId;
    private String userName;
    private String userPhotoUrl;
    private String userMail;
    private String userPhone;
    private boolean darkBackground;
    private boolean trimmed;

    public MovieSpoilerModel() {

    }

    protected MovieSpoilerModel(Parcel in) {
        super(in);
        mId = in.readInt();
        mName = in.readString();
        selectType = in.readInt();
        midCredit = in.readString();
        stringer = in.readString();
        spoiler = in.readString();
        cratedOn = in.readString();
        date = in.readString();
        description = in.readString();
        thumbsUp = in.readString();
        thumbsDown = in.readString();
        userId = in.readInt();
        userName = in.readString();
        userPhotoUrl = in.readString();
        userMail = in.readString();
        userPhone = in.readString();
        darkBackground = in.readByte() != 0;
        trimmed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(selectType);
        dest.writeString(midCredit);
        dest.writeString(stringer);
        dest.writeString(spoiler);
        dest.writeString(cratedOn);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(thumbsUp);
        dest.writeString(thumbsDown);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userPhotoUrl);
        dest.writeString(userMail);
        dest.writeString(userPhone);
        dest.writeByte((byte) (darkBackground ? 1 : 0));
        dest.writeByte((byte) (trimmed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieSpoilerModel> CREATOR = new Creator<MovieSpoilerModel>() {
        @Override
        public MovieSpoilerModel createFromParcel(Parcel in) {
            return new MovieSpoilerModel(in);
        }

        @Override
        public MovieSpoilerModel[] newArray(int size) {
            return new MovieSpoilerModel[size];
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(String thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public void changeThumbsUp(boolean add) {
        if (add || getThumbsDownInt() > 0)
            setThumbsUp(String.valueOf(add ? getThumbsUpInt() + 1 : getThumbsUpInt() - 1));
    }

    public int getThumbsUpInt() {
        return NumericUtils.parseInt(thumbsUp);
    }

    public String getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(String thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public void changeThumbsDown(boolean add) {
        if (add || getThumbsDownInt() > 0)
            setThumbsUp(String.valueOf(add ? getThumbsDownInt() + 1 : getThumbsDownInt() - 1));
    }

    public int getThumbsDownInt() {
        return NumericUtils.parseInt(thumbsDown);
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

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public boolean isDarkBackground() {
        return darkBackground;
    }

    public void setDarkBackground(boolean darkBackground) {
        this.darkBackground = darkBackground;
    }

    public boolean isTrimmed() {
        return trimmed;
    }

    public void setTrimmed(boolean trimmed) {
        this.trimmed = trimmed;
    }

    @Override
    public String toString() {
        return "MovieSpoilerModel{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", selectType=" + selectType +
                ", midCredit='" + midCredit + '\'' +
                ", stringer='" + stringer + '\'' +
                ", spoiler='" + spoiler + '\'' +
                ", cratedOn='" + cratedOn + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", thumbsUp='" + thumbsUp + '\'' +
                ", thumbsDown='" + thumbsDown + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", darkBackground=" + darkBackground +
                ", trimmed=" + trimmed +
                '}';
    }

    public SpoilerUserModel toSpoilerUserModel() {
        return new SpoilerUserModel(
                userId, userName, userPhotoUrl, userMail, userPhone
        );
    }
}
