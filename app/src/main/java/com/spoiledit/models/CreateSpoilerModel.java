package com.spoiledit.models;

import android.os.Parcel;

public class CreateSpoilerModel extends RootModel {
    private String spoilerType;
    private String midCreditExists;
    private String postCreditExists;
    private String spoiler;

    public CreateSpoilerModel(String spoilerType, String midCreditExists, String postCreditExists, String spoiler) {
        this.spoilerType = spoilerType;
        this.midCreditExists = midCreditExists;
        this.postCreditExists = postCreditExists;
        this.spoiler = spoiler;
    }

    protected CreateSpoilerModel(Parcel in) {
        super(in);
        spoilerType = in.readString();
        midCreditExists = in.readString();
        postCreditExists = in.readString();
        spoiler = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(spoilerType);
        dest.writeString(midCreditExists);
        dest.writeString(postCreditExists);
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

    public String getSpoilerType() {
        return spoilerType;
    }

    public void setSpoilerType(String spoilerType) {
        this.spoilerType = spoilerType;
    }

    public String getMidCreditExists() {
        return midCreditExists;
    }

    public void setMidCreditExists(String midCreditExists) {
        this.midCreditExists = midCreditExists;
    }

    public String getPostCreditExists() {
        return postCreditExists;
    }

    public void setPostCreditExists(String postCreditExists) {
        this.postCreditExists = postCreditExists;
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
                "spoilerType=" + spoilerType +
                ", midCreditExists=" + midCreditExists +
                ", postCreditExists=" + postCreditExists +
                ", spoiler='" + spoiler + '\'' +
                '}';
    }
}
