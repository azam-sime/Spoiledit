package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.repos.DashboardRepo;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private DashboardRepo dashboardRepo;

    public DashboardViewModel(DashboardRepo dashboardRepo) {
        this.dashboardRepo = dashboardRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return dashboardRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<MoviePopularModel>> getMoviePopularModelsMutable() {
        return dashboardRepo.getMoviePopularModelsMutable();
    }

    public MutableLiveData<List<MovieRecentModel>> getMovieRecentModelsMutable() {
        return dashboardRepo.getMovieRecentModelsMutable();
    }

    public MutableLiveData<List<MovieUpcomingModel>> getMovieSoonModelsMutable() {
        return dashboardRepo.getMovieSoonModelsMutable();
    }

    public void requestMoviesPopular() {
        dashboardRepo.requestMoviesPopular();
    }

    public void requestMoviesRecent() {
        dashboardRepo.requestMoviesRecent();
    }

    public void requestMoviesSoon() {
        dashboardRepo.requestMoviesSoon();
    }

    public static final class DashboardFactory implements ViewModelProvider.Factory {
        private DashboardRepo dashboardRepo;

        public DashboardFactory(DashboardRepo dashboardRepo) {
            this.dashboardRepo = dashboardRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DashboardViewModel(dashboardRepo);
        }
    }
}
