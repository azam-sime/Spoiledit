package com.spoiledit.models;

public class MySpoilerModel extends MovieSpoilerModel {

    public MySpoilerModel() {
        setTrimmed(true);
    }

    @Override
    public String toString() {
        return "MySpoilerModel{" +
                super.toString() +
                '}';
    }
}
