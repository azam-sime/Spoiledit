package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.repos.ProfileRepo;

public class ProfileViewModel extends ViewModel {
    private ProfileRepo.DetailsRepo detailsRepo;

    public ProfileViewModel(ProfileRepo.DetailsRepo detailsRepo) {
        this.detailsRepo = detailsRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return detailsRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<ProfileModel> getProfileModelMutable() {
        return detailsRepo.getProfileModelMutable();
    }

    public void requestProfileDetails() {
        detailsRepo.getProfileDetails();
    }

    public void updateProfilePic(String filePath) {
        detailsRepo.updateProfilePic(filePath);
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private ProfileRepo.DetailsRepo detailsRepo;

        public Factory(ProfileRepo.DetailsRepo detailsRepo) {
            this.detailsRepo = detailsRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProfileViewModel(detailsRepo);
        }
    }
}
