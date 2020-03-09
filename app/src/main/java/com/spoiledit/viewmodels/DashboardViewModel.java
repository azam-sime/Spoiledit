package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.repos.DashboardRepo;

public class DashboardViewModel extends ViewModel {
    private DashboardRepo dashboardRepo;

    public DashboardViewModel(DashboardRepo dashboardRepo) {
        this.dashboardRepo = dashboardRepo;
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return dashboardRepo.getApiStatusModelMutable();
    }

    public static final class DashboardViewModelFactory implements ViewModelProvider.Factory {
        private DashboardRepo dashboardRepo;

        public DashboardViewModelFactory(DashboardRepo dashboardRepo) {
            this.dashboardRepo = dashboardRepo;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DashboardViewModel(dashboardRepo);
        }
    }
}
