package com.spoiledit.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.repos.RepliesRepo;

import java.util.List;

public class RepliesViewModel extends ViewModel {
    private RepliesRepo repliesRepo;

    public RepliesViewModel() {
        repliesRepo = RepliesRepo.getInstance();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return repliesRepo.getApiStatusModelMutable();
    }

    public MovieSpoilerModel getMovieSpoilerModel() {
        return repliesRepo.getMovieSpoilerModel();
    }

    public CommentModel getCommentModel() {
        return repliesRepo.getCommentModel();
    }

    public MutableLiveData<List<CommentModel>> getRepliesModelMutable() {
        return repliesRepo.getRepliesModelMutable();
    }

    public void requestReplies() {
        repliesRepo.requestReplies();
    }

    public void addReply(String reply) {
        repliesRepo.addReply(reply);
    }

    public void editReply(int replyId, String newReply) {
        repliesRepo.editReply(replyId, newReply);
    }

    public void removeReply(int replyId) {
        repliesRepo.removeReply(replyId);
    }

    public void reportComment(int reportType, String reportExplanation) {
        repliesRepo.reportComment(reportType, reportExplanation);
    }

    public void thumbsDownComment(boolean add) {
        repliesRepo.thumbsDownComment(add);
    }

    public void thumbsUpComment(boolean add) {
        repliesRepo.thumbsUpComment(add);
    }
}
