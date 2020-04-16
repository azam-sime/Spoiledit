package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SpoilersNewModel;
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

    public MutableLiveData<List<MovieUpcomingModel>> getMovieUpcomingModelsMutable() {
        return dashboardRepo.getMovieUpcomingModelsMutable();
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

    public MutableLiveData<List<SpoilersNewModel>> getSpoilerModelsMutable() {
        return dashboardRepo.getSpoilerModelsMutable();
    }

    public MutableLiveData<List<String>> getSearchValues() {
        return dashboardRepo.getSearchValues();
    }

    public void requestSpoilers() {
        dashboardRepo.requestSpoilers();
    }

    public void requestMovieDetails(int movieId, int fromTab) {
        dashboardRepo.requestMovieDetails(movieId, fromTab);
    }

    public void requestSearchAutoCompleteValues(String searchValue) {
        dashboardRepo.requestSearchAutoCompleteValues(searchValue);
    }

    public void requestLogout() {
        dashboardRepo.requestLogout();
    }

    public static final class Factory implements ViewModelProvider.Factory {
        private DashboardRepo dashboardRepo;

        public Factory(DashboardRepo dashboardRepo) {
            this.dashboardRepo = dashboardRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DashboardViewModel(dashboardRepo);
        }
    }
}
