package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.SignUpRepo;

public class SignUpViewModel extends ViewModel {
    private SignUpRepo signUpRepo;

    public SignUpViewModel(SignUpRepo signUpRepo) {
        this.signUpRepo = signUpRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return signUpRepo.getApiStatusModelMutable();
    }

    public void requestSignUp(String[] credentials) {
        signUpRepo.requestSignUp(credentials);
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private SignUpRepo signUpRepo;

        public Factory(SignUpRepo signUpRepo) {
            this.signUpRepo = signUpRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SignUpViewModel(signUpRepo);
        }
    }
}
