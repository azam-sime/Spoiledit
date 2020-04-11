package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.ProfileRepo;

public class ChangePasswordViewModel extends ViewModel {
    private ProfileRepo.ChangePasswordRepo changePasswordRepo;

    public ChangePasswordViewModel(ProfileRepo.ChangePasswordRepo changePasswordRepo) {
        this.changePasswordRepo = changePasswordRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return changePasswordRepo.getApiStatusModelMutable();
    }

    public void requestUpdatePassword(String[] values) {
        changePasswordRepo.requestUpdatePassword(values);
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private ProfileRepo.ChangePasswordRepo changePasswordRepo;

        public Factory(ProfileRepo.ChangePasswordRepo changePasswordRepo) {
            this.changePasswordRepo = changePasswordRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChangePasswordViewModel(changePasswordRepo);
        }
    }
}
