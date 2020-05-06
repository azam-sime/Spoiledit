package com.spoiledit.models;

public class SpoilerBriefModel extends MovieSpoilerModel {

    public SpoilerBriefModel() {
        setTrimmed(true);
    }

    @Override
    public String toString() {
        return "SpoilerBriefModel{" +
                super.toString() +
                '}';
    }
}
