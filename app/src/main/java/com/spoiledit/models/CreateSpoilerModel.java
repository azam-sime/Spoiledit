package com.spoiledit.models;

public class CreateSpoilerModel extends RootModel {
    private int spoilerType;
    private int midCreditExists;
    private int postCreditExists;
    private String spoiler;

    public CreateSpoilerModel(int spoilerType, int midCreditExists, int postCreditExists, String spoiler) {
        this.spoilerType = spoilerType;
        this.midCreditExists = midCreditExists;
        this.postCreditExists = postCreditExists;
        this.spoiler = spoiler;
    }

    public int getSpoilerType() {
        return spoilerType;
    }

    public void setSpoilerType(int spoilerType) {
        this.spoilerType = spoilerType;
    }

    public int getMidCreditExists() {
        return midCreditExists;
    }

    public void setMidCreditExists(int midCreditExists) {
        this.midCreditExists = midCreditExists;
    }

    public int getPostCreditExists() {
        return postCreditExists;
    }

    public void setPostCreditExists(int postCreditExists) {
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
