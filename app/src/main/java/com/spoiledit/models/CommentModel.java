package com.spoiledit.models;

import android.os.Parcel;

public class CommentModel extends RootSelectionModel {
    private int userId;
    private String avatarUrl;
    private String comment;
    private String commentDate;
    private String likes;
    private String dislikes;

    public CommentModel() {
    }

    protected CommentModel(Parcel in) {
        super(in);
        userId = in.readInt();
        avatarUrl = in.readString();
        comment = in.readString();
        commentDate = in.readString();
        likes = in.readString();
        dislikes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(userId);
        dest.writeString(avatarUrl);
        dest.writeString(comment);
        dest.writeString(commentDate);
        dest.writeString(likes);
        dest.writeString(dislikes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "userId=" + userId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", comment='" + comment + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", likes='" + likes + '\'' +
                ", dislikes='" + dislikes + '\'' +
                '}';
    }
}
