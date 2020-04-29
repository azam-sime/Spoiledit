package com.spoiledit.models;

import android.os.Parcel;

import com.spoiledit.utils.NumericUtils;

public class MovieSpoilerModel extends RootSelectionModel {
    private String userLogin;
    private int mId;
    private String mName;
    private int userId;
    private int selectType;
    private String midCredit;
    private String stringer;
    private String spoiler;
    private String cratedOn;
    private String displayName;
    private String date;
    private String description;
    private String thumbsUp;
    private String thumbsDown;
    private String avatarUrl;
    private boolean darkBackground;

    public MovieSpoilerModel() {
    }

    protected MovieSpoilerModel(Parcel in) {
        super(in);
        mId = in.readInt();
        mName = in.readString();
        userId = in.readInt();
        selectType = in.readInt();
        midCredit = in.readString();
        stringer = in.readString();
        spoiler = in.readString();
        cratedOn = in.readString();
        userLogin = in.readString();
        displayName = in.readString();
        date = in.readString();
        description = in.readString();
        thumbsUp = in.readString();
        thumbsDown = in.readString();
        avatarUrl = in.readString();
        darkBackground = in.readByte() != 0;
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
        dest.writeString(userLogin);
        dest.writeString(displayName);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(thumbsUp);
        dest.writeString(thumbsDown);
        dest.writeString(avatarUrl);
        dest.writeByte((byte) (darkBackground ? 1 : 0));
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public int getThumbsUpInt() {
        return NumericUtils.parseInt(thumbsUp);
    }

    public String getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(String thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public int getThumbsDownInt() {
        return NumericUtils.parseInt(thumbsDown);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isDarkBackground() {
        return darkBackground;
    }

    public void setDarkBackground(boolean darkBackground) {
        this.darkBackground = darkBackground;
    }

    @Override
    public String toString() {
        return "MovieSpoilerModel{" +
                "userLogin='" + userLogin + '\'' +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", userId=" + userId +
                ", selectType=" + selectType +
                ", midCredit='" + midCredit + '\'' +
                ", stringer='" + stringer + '\'' +
                ", spoiler='" + spoiler + '\'' +
                ", cratedOn='" + cratedOn + '\'' +
                ", displayName='" + displayName + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", thumbsUp='" + thumbsUp + '\'' +
                ", thumbsDown='" + thumbsDown + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", darkBackground='" + darkBackground + '\'' +
                '}';
    }
}
