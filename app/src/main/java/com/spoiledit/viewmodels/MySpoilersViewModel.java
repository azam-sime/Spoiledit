package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MySpoilerModel;
import com.spoiledit.repos.MyRepo;

import java.util.List;

public class MySpoilersViewModel extends ViewModel {
    private MyRepo.MySpoilersRepo mySpoilersRepo;

    public MySpoilersViewModel(MyRepo.MySpoilersRepo mySpoilersRepo) {
        this.mySpoilersRepo = mySpoilersRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return mySpoilersRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<MySpoilerModel>> getMySpoilersMutable() {
        return mySpoilersRepo.getMySpoilersMutable();
    }

    public void requestMySpoilers() {
        mySpoilersRepo.requestMySpoilers();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private MyRepo.MySpoilersRepo mySpoilersRepo;

        public Factory(MyRepo.MySpoilersRepo mySpoilersRepo) {
            this.mySpoilersRepo = mySpoilersRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MySpoilersViewModel(mySpoilersRepo);
        }
    }
}
