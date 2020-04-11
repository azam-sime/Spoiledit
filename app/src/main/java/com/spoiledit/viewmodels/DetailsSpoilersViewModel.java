package com.spoiledit.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.models.MovieDetailsModel;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.repos.DetailsSpoilerRepo;

import java.util.List;

public class DetailsSpoilersViewModel extends ViewModel {
    private DetailsSpoilerRepo detailsSpoilerRepo;

    public DetailsSpoilersViewModel() {
        this.detailsSpoilerRepo = DetailsSpoilerRepo.getInstance();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return detailsSpoilerRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<SpoilerFullModel>> getSpoilerFullModelsMutable() {
        return detailsSpoilerRepo.getSpoilerFullModelsMutable();
    }

    public MutableLiveData<List<SpoilerBriefModel>> getSpoilerBriefModelsMutable() {
        return detailsSpoilerRepo.getSpoilerBriefModelsMutable();
    }

    public MutableLiveData<List<SpoilerEndingModel>> getSpoilerEndingModelsMutable() {
        return detailsSpoilerRepo.getSpoilerEndingModelsMutable();
    }

    public void requestSpoilerFull() {
        detailsSpoilerRepo.requestSpoilerFull();
    }

    public void requestSpoilerBrief() {
        detailsSpoilerRepo.requestSpoilerBrief();
    }

    public void requestSpoilerEnding() {
        detailsSpoilerRepo.requestSpoilerEnding();
    }

    public void addSpoilers(CreateSpoilerModel createSpoilerModel) {
        detailsSpoilerRepo.addSpoiler(createSpoilerModel);
    }
}
