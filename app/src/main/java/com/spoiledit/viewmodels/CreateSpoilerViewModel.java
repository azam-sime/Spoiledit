package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.CreateSpoilerModel;
import com.spoiledit.repos.CreateSpoilerRepo;

public class CreateSpoilerViewModel extends ViewModel {
    private CreateSpoilerRepo createSpoilerRepo;

    public CreateSpoilerViewModel(CreateSpoilerRepo createSpoilerRepo) {
        this.createSpoilerRepo = createSpoilerRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return createSpoilerRepo.getApiStatusModelMutable();
    }

    public void createSpoiler(CreateSpoilerModel createSpoilerModel) {
        createSpoilerRepo.createSpoiler(createSpoilerModel);
    }

    public static class CreateSpoilerViewModelFactory implements ViewModelProvider.Factory {
        private CreateSpoilerRepo createSpoilerRepo;

        public CreateSpoilerViewModelFactory(CreateSpoilerRepo createSpoilerRepo) {
            this.createSpoilerRepo = createSpoilerRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CreateSpoilerViewModel(createSpoilerRepo);
        }
    }
}
