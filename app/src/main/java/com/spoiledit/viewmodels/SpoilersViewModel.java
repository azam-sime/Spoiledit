package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.repos.MoviesRepo;
import com.spoiledit.repos.SpoilersRepo;

import java.util.List;

public class SpoilersViewModel extends ViewModel {
    private SpoilersRepo spoilersRepo;

    public SpoilersViewModel(SpoilersRepo spoilersRepo) {
        this.spoilersRepo = spoilersRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return spoilersRepo.getApiStatusModelMutable();
    }

//    public MutableLiveData<List<SpoilerFullModel>> getSpoilerFullModelsMutable() {
//        return spoilersRepo.getSpoilerFullModelsMutable();
//    }
//
//    public MutableLiveData<List<SpoilerBriefModel>> getSpoilerBriefModelsMutable() {
//        return spoilersRepo.getSpoilerBriefModelsMutable();
//    }
//
//    public MutableLiveData<List<SpoilerEndingModel>> getSpoilerEndingModelsMutable() {
//        return spoilersRepo.getSpoilerEndingModelsMutable();
//    }
//
//    public void requestSpoilerFull() {
//        spoilersRepo.requestSpoilerFull();
//    }
//
//    public void requestSpoilerBrief() {
//        spoilersRepo.requestSpoilerBrief();
//    }
//
//    public void requestSpoilerEnding() {
//        spoilersRepo.requestSpoilerEnding();
//    }

    public static final class SpoilersViewModelFactory implements ViewModelProvider.Factory {
        private SpoilersRepo spoilersRepo;

        public SpoilersViewModelFactory(SpoilersRepo spoilersRepo) {
            this.spoilersRepo = spoilersRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SpoilersViewModel(spoilersRepo);
        }
    }
}
