package com.spoiledit.models;

public class SpoilerEndingModel extends MovieSpoilerModel {

    public SpoilerEndingModel() {
        setTrimmed(true);
    }

    @Override
    public String toString() {
        return "SpoilerEndingModel{" +
                super.toString() +
                '}';
    }
}
