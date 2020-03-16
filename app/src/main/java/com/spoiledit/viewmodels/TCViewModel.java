package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.TCRepo;

public class TCViewModel extends ViewModel {
    private TCRepo tcRepo;

    public TCViewModel(TCRepo tcRepo) {
        this.tcRepo = tcRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return tcRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<String> getStringTCMutableLiveData() {
        return tcRepo.getStringTCMutableLiveData();
    }

    public void requestTAndC() {
        tcRepo.requestTAndC();
    }

    public static final class TCViewModelFactory implements ViewModelProvider.Factory {
        private TCRepo tcRepo1;

        public TCViewModelFactory(TCRepo tcRepo1) {
            this.tcRepo1 = tcRepo1;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TCViewModel(tcRepo1);
        }
    }
}
