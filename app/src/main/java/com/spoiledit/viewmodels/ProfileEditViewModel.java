package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.repos.ProfileRepo;

public class ProfileEditViewModel extends ViewModel {
    private ProfileRepo.UpdateRepo updateRepo;

    public ProfileEditViewModel(ProfileRepo.UpdateRepo updateRepo) {
        this.updateRepo = updateRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return updateRepo.getApiStatusModelMutable();
    }

    public void updateProfile(String name, String email, String phone) {
        updateRepo.updateProfile(name, email, phone);
    }

    public void getProfileDetails() {
        updateRepo.getProfileDetails();
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private ProfileRepo.UpdateRepo updateRepo;

        public Factory(ProfileRepo.UpdateRepo updateRepo) {
            this.updateRepo = updateRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProfileEditViewModel(updateRepo);
        }
    }
}
