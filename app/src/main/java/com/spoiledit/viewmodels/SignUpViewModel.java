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

    public void requestTAndC(String[] values) {
        signUpRepo.requestTAndC(values);
    }

    public static final class SignUpFactory implements ViewModelProvider.Factory {
        private SignUpRepo signUpRepo;

        public SignUpFactory(SignUpRepo signUpRepo) {
            this.signUpRepo = signUpRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SignUpViewModel(signUpRepo);
        }
    }
}
