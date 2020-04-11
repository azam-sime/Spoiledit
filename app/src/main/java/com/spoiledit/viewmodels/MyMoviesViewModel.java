package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MyMovieModel;
import com.spoiledit.repos.MyRepo;

import java.util.List;

public class MyMoviesViewModel extends ViewModel {
    private MyRepo.MyMoviesRepo myMoviesRepo;

    public MyMoviesViewModel(MyRepo.MyMoviesRepo myMoviesRepo) {
        this.myMoviesRepo = myMoviesRepo;
    }

    public MutableLiveData<List<MyMovieModel>> getMyMoviesMutable() {
        return myMoviesRepo.getMyMoviesMutable();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return myMoviesRepo.getApiStatusModelMutable();
    }

    public void requestMyMovies() {
        myMoviesRepo.requestMyMovies();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private MyRepo.MyMoviesRepo myMoviesRepo;

        public Factory(MyRepo.MyMoviesRepo myMoviesRepo) {
            this.myMoviesRepo = myMoviesRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MyMoviesViewModel(myMoviesRepo);
        }
    }
}
