package com.spoiledit.models;

import android.os.Parcel;

import java.util.List;

public class CommentModel extends RootSelectionModel {
    private int movieId;
    private int spoilerId;
    private int parentCommentId;
    private String comment;
    private String commentDate;
    private int thumbsUp;
    private int thumbsDown;
//    private int userThumbsUp;
//    private int userThumbsDown;
    private int userId;
    private String userName;
    private String userPhotoUrl;
    private String userMail;
    private String userPhone;
    private List<CommentModel> replyModels;

    public CommentModel() {
    }

    protected CommentModel(Parcel in) {
        super(in);
        movieId = in.readInt();
        spoilerId = in.readInt();
        parentCommentId = in.readInt();
        comment = in.readString();
        commentDate = in.readString();
        thumbsUp = in.readInt();
        thumbsDown = in.readInt();
//        userThumbsUp = in.readInt();
//        userThumbsDown = in.readInt();
        userId = in.readInt();
        userName = in.readString();
        userPhotoUrl = in.readString();
        userMail = in.readString();
        userPhone = in.readString();
        replyModels = in.createTypedArrayList(CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(movieId);
        dest.writeInt(spoilerId);
        dest.writeInt(parentCommentId);
        dest.writeString(comment);
        dest.writeString(commentDate);
        dest.writeInt(thumbsUp);
        dest.writeInt(thumbsDown);
//        dest.writeInt(userThumbsUp);
//        dest.writeInt(userThumbsDown);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userPhotoUrl);
        dest.writeString(userMail);
        dest.writeString(userPhone);
        dest.writeTypedList(replyModels);
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

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

//    public int getUserThumbsUp() {
//        return userThumbsUp;
//    }
//
//    public void setUserThumbsUp(int userThumbsUp) {
//        this.userThumbsUp = userThumbsUp;
//    }
//
//    public int getUserThumbsDown() {
//        return userThumbsDown;
//    }
//
//    public void setUserThumbsDown(int userThumbsDown) {
//        this.userThumbsDown = userThumbsDown;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<CommentModel> getReplyModels() {
        return replyModels;
    }

    public void setReplyModels(List<CommentModel> replyModels) {
        this.replyModels = replyModels;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "movieId='" + movieId + '\'' +
                ", spoilerId='" + spoilerId + '\'' +
                ", parentCommentId='" + parentCommentId + '\'' +
                ", comment='" + comment + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", thumbsUp='" + thumbsUp + '\'' +
                ", thumbsDown='" + thumbsDown + '\'' +
//                ", userThumbsUp='" + userThumbsUp + '\'' +
//                ", userThumbsDown='" + userThumbsDown + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", commentModels='" + replyModels + '\'' +
                '}';
    }

    public SpoilerUserModel toSpoilerUserModel() {
        return new SpoilerUserModel(
                userId, userName, userPhotoUrl, userMail, userPhone
        );
    }
}
