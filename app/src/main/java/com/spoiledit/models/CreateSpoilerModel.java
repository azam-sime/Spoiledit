package com.spoiledit.models;

import android.os.Parcel;

public class CreateSpoilerModel extends RootModel {
    private String spType;
    private String midCredit;
    private String stringer;
    private String spoiler;

    public CreateSpoilerModel() {
    }

    public CreateSpoilerModel(String spType, String midCredit, String stringer, String spoiler) {
        this.spType = spType;
        this.midCredit = midCredit;
        this.stringer = stringer;
        this.spoiler = spoiler;
    }

    protected CreateSpoilerModel(Parcel in) {
        super(in);
        spType = in.readString();
        midCredit = in.readString();
        stringer = in.readString();
        spoiler = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(spType);
        dest.writeString(midCredit);
        dest.writeString(stringer);
        dest.writeString(spoiler);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateSpoilerModel> CREATOR = new Creator<CreateSpoilerModel>() {
        @Override
        public CreateSpoilerModel createFromParcel(Parcel in) {
            return new CreateSpoilerModel(in);
        }

        @Override
        public CreateSpoilerModel[] newArray(int size) {
            return new CreateSpoilerModel[size];
        }
    };

    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
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

    @Override
    public String toString() {
        return "CreateSpoilerModel{" +
                "spoilerType=" + spType +
                ", midCreditExists=" + midCredit +
                ", postCreditExists=" + stringer +
                ", spoiler='" + spoiler + '\'' +
                '}';
    }
}
