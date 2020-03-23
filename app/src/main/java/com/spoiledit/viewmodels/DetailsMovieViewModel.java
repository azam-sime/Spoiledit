package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.models.MovieDetailsModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.repos.DetailsMovieRepo;

import java.util.List;

public class DetailsMovieViewModel extends ViewModel {
    private DetailsMovieRepo detailsMovieRepo;

    public DetailsMovieViewModel() {
        this.detailsMovieRepo = DetailsMovieRepo.getInstance();
    }

    public MovieDetailsModel getMovieDetailsModel() {
        return detailsMovieRepo.getMovieDetailsModel();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return detailsMovieRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<SpoilerFullModel>> getSpoilerFullModelsMutable() {
        return detailsMovieRepo.getSpoilerFullModelsMutable();
    }

    public MutableLiveData<List<SpoilerBriefModel>> getSpoilerBriefModelsMutable() {
        return detailsMovieRepo.getSpoilerBriefModelsMutable();
    }

    public MutableLiveData<List<SpoilerEndingModel>> getSpoilerEndingModelsMutable() {
        return detailsMovieRepo.getSpoilerEndingModelsMutable();
    }

    public void requestSpoilerFull() {
        detailsMovieRepo.requestSpoilerFull();
    }

    public void requestSpoilerBrief() {
        detailsMovieRepo.requestSpoilerBrief();
    }

    public void requestSpoilerEnding() {
        detailsMovieRepo.requestSpoilerEnding();
    }

    public void addSpoilers(CreateSpoilerModel createSpoilerModel) {
        detailsMovieRepo.addSpoiler(createSpoilerModel);
    }
}
