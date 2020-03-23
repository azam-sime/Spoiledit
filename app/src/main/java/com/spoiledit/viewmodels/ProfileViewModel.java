package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.ProfileModel;
import com.spoiledit.repos.LoginRepo;
import com.spoiledit.repos.ProfileRepo;

public class ProfileViewModel extends ViewModel {
    private ProfileRepo profileRepo;

    public ProfileViewModel(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return profileRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<ProfileModel> getProfileModelMutable() {
        return profileRepo.getProfileModelMutable();
    }

    public void requestProfileDetails() {
        profileRepo.getProfileDetails();
    }

    public void updateProfile(String name, String email, String phone) {
        profileRepo.updateProfile(name, email, phone);
    }

    public void updateProfilePic(String filePath) {
        profileRepo.updateProfilePic(filePath);
    }

    public static final class ProfileViewModelFactory implements ViewModelProvider.Factory {
        private ProfileRepo profileRepo;

        public ProfileViewModelFactory(ProfileRepo profileRepo) {
            this.profileRepo = profileRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProfileViewModel(profileRepo);
        }
    }
}
