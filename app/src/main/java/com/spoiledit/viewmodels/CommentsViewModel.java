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

    public void sendMessage(int commentId, String message) {
        commentsRepo.sendComment(commentId, message);
    }

    public void requestSpoilerDetails(int id) {

    }
}
