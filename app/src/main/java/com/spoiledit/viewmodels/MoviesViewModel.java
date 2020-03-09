package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.repos.MoviesRepo;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private MoviesRepo moviesRepo;

    public MoviesViewModel(MoviesRepo moviesRepo) {
        this.moviesRepo = moviesRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return moviesRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<MoviePopularModel>> getMoviePopularModelsMutable() {
        return moviesRepo.getMoviePopularModelsMutable();
    }

    public MutableLiveData<List<MovieRecentModel>> getMovieRecentModelsMutable() {
        return moviesRepo.getMovieRecentModelsMutable();
    }

    public MutableLiveData<List<MovieUpcomingModel>> getMovieUpcomingModelsMutable() {
        return moviesRepo.getMovieSoonModelsMutable();
    }

    public void requestMoviesPopular() {
        moviesRepo.requestMoviesPopular();
    }

    public void requestMoviesRecent() {
        moviesRepo.requestMoviesRecent();
    }

    public void requestMoviesSoon() {
        moviesRepo.requestMoviesSoon();
    }

    public static final class MoviesViewModelFactory implements ViewModelProvider.Factory {
        private MoviesRepo moviesRepo;

        public MoviesViewModelFactory(MoviesRepo moviesRepo) {
            this.moviesRepo = moviesRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MoviesViewModel(moviesRepo);
        }
    }
}
