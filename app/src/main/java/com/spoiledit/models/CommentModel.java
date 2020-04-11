package com.spoiledit.models;

import android.os.Parcel;

public class CommentModel extends RootSelectionModel {
    private int userId;
    private int movieId;
    private int spoilerId;
    private int parentCommentId;
    private String userName;
    private String avatarUrl;
    private String comment;
    private String commentDate;
    private int likes;
    private int dislikes;

    public CommentModel() {
    }

    protected CommentModel(Parcel in) {
        super(in);
        userId = in.readInt();
        movieId = in.readInt();
        spoilerId = in.readInt();
        parentCommentId = in.readInt();
        userName = in.readString();
        avatarUrl = in.readString();
        comment = in.readString();
        commentDate = in.readString();
        likes = in.readInt();
        dislikes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(userId);
        dest.writeInt(movieId);
        dest.writeInt(spoilerId);
        dest.writeInt(parentCommentId);
        dest.writeString(userName);
        dest.writeString(avatarUrl);
        dest.writeString(comment);
        dest.writeString(commentDate);
        dest.writeInt(likes);
        dest.writeInt(dislikes);
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSpoilerId() {
        return spoilerId;
    }

    public void setSpoilerId(int spoilerId) {
        this.spoilerId = spoilerId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "userId=" + userId +
                ", movieId='" + movieId + '\'' +
                ", spoilerId='" + spoilerId + '\'' +
                ", parentCommentId='" + parentCommentId + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", comment='" + comment + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", likes='" + likes + '\'' +
                ", dislikes='" + dislikes + '\'' +
                '}';
    }
}
