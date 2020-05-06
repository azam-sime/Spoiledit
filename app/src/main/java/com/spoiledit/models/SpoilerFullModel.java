package com.spoiledit.models;

public class SpoilerFullModel extends MovieSpoilerModel {

    public SpoilerFullModel() {
        setTrimmed(true);
    }

    @Override
    public String toString() {
        return "SpoilerFullModel{" +
                super.toString() +
                '}';
    }
}
