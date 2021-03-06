package com.spoiledit.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.repos.CommentsRepo;

import java.util.List;

public class CommentsViewModel extends ViewModel {
    private CommentsRepo commentsRepo;

    public CommentsViewModel() {
        commentsRepo = CommentsRepo.getInstance();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return commentsRepo.getApiStatusModelMutable();
    }

    public MovieSpoilerModel getMovieSpoilerModel() {
        return commentsRepo.getMovieSpoilerModel();
    }

    public MutableLiveData<List<CommentModel>> getCommentsModelMutable() {
        return commentsRepo.getCommentsModelMutable();
    }

    public void requestComments() {
        commentsRepo.requestComments();
    }

    public void addComment(String comment) {
        commentsRepo.addComment(comment);
    }

    public void editComment(int commentId, String newComment) {
        commentsRepo.editComment(commentId, newComment);
    }

    public void replyComment(int commentId, String reply) {
        commentsRepo.replyComment(commentId, reply);
    }

    public void removeComment(int commentId) {
        commentsRepo.removeComment(commentId);
    }

    public void requestSpoilerDetails(int id) {

    }

    public void reportSpoiler(int reportType, String reportExplanation) {
        commentsRepo.reportSpoiler(reportType, reportExplanation);
    }

    public void reportComment(int commentId, int reportType, String reportExplanation) {
        commentsRepo.reportComment(commentId, reportType, reportExplanation);
    }

    public void thumbsDownSpoiler(boolean add) {
        commentsRepo.thumbsDownSpoiler(add);
    }

    public void thumbsUpSpoiler(boolean add) {
        commentsRepo.thumbsUpSpoiler(add);
    }

    public void thumbsDownComment(int commentId, boolean add) {
        commentsRepo.thumbsDownComment(commentId, add);
    }

    public void thumbsUpComment(int commentId, boolean add) {
        commentsRepo.thumbsUpComment(commentId, add);
    }
}
