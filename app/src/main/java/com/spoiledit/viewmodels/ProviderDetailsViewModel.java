package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.ProviderDetailsRepo;

public class ProviderDetailsViewModel extends ViewModel {
    private ProviderDetailsRepo providerDetailsRepo;

    public ProviderDetailsViewModel(ProviderDetailsRepo providerDetailsRepo) {
        this.providerDetailsRepo = providerDetailsRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return providerDetailsRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<String> getDetailsMutableLiveData() {
        return providerDetailsRepo.getDetailsMutableLiveData();
    }

    public void requestTermsConditions() {
        providerDetailsRepo.requestTermsConditions();
    }

    public void requestPrivacyPolicies() {
        providerDetailsRepo.requestPrivacyPolicies();
    }

    public void requestCookiesPolicy() {
        providerDetailsRepo.requestCookiesPolicy();
    }

    public void requestAboutUs() {
        providerDetailsRepo.requestAboutUs();
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private ProviderDetailsRepo providerDetailsRepo;

        public Factory(ProviderDetailsRepo providerDetailsRepo) {
            this.providerDetailsRepo = providerDetailsRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProviderDetailsViewModel(providerDetailsRepo);
        }
    }
}
