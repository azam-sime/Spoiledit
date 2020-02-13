package com.spoiledit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RootSelectionModel extends RootModel {
    public int viewHolderType;
    private boolean selected;

    public RootSelectionModel() {

    }

    protected RootSelectionModel(Parcel in) {
        super(in);
        viewHolderType = in.readInt();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(viewHolderType);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<RootSelectionModel> CREATOR = new Parcelable.Creator<RootSelectionModel>() {
        @Override
        public RootSelectionModel createFromParcel(Parcel in) {
            return new RootSelectionModel(in);
        }

        @Override
        public RootSelectionModel[] newArray(int size) {
            return new RootSelectionModel[size];
        }
    };

    public int getViewHolderType() {
        return viewHolderType;
    }

    public void setViewHolderType(int viewHolderType) {
        this.viewHolderType = viewHolderType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "RootSelectionModel{" +
                "viewHolderType=" + viewHolderType +
                ", selected=" + selected +
                '}';
    }
}
