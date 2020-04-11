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
}
