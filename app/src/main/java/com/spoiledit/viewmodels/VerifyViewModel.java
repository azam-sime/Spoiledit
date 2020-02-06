package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.VerifyRepo;

public class VerifyViewModel extends ViewModel {
    private VerifyRepo verifyRepo;

    public VerifyViewModel(VerifyRepo verifyRepo) {
        this.verifyRepo = verifyRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return verifyRepo.getApiStatusModelMutable();
    }

    public void requestNewPassword(String[] values) {
        verifyRepo.requestNewPassword(values);
    }

    public boolean isOtpValid() {
        return true;
    }

    public static final class VerifyFactory implements ViewModelProvider.Factory {
        private VerifyRepo verifyRepo;

        public VerifyFactory(VerifyRepo verifyRepo) {
            this.verifyRepo = verifyRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new VerifyViewModel(verifyRepo);
        }
    }
}
