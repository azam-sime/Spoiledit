package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
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



    public static final class ProfileFactory implements ViewModelProvider.Factory {
        private ProfileRepo profileRepo;

        public ProfileFactory(ProfileRepo profileRepo) {
            this.profileRepo = profileRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProfileViewModel(profileRepo);
        }
    }
}
