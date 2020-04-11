package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.LoginRepo;

public class LoginViewModel extends ViewModel {
    private LoginRepo loginRepo;

    public LoginViewModel(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return loginRepo.getApiStatusModelMutable();
    }

    public void requestLogin(String[] credentials) {
        loginRepo.requestLogin(credentials);
    }

    public void requestForgotPasswordOtp(String[] values) {
        loginRepo.requestForgotPasswordOtp(values);
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private LoginRepo loginRepo;

        public Factory(LoginRepo loginRepo) {
            this.loginRepo = loginRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LoginViewModel(loginRepo);
        }
    }
}
