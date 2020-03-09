package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;
import com.spoiledit.adapters.MoviesPopularAdapter;
import com.spoiledit.adapters.MoviesSoonAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DashboardViewModel;

public class MoviesSoonFragment extends RootFragment {
    public static final String TAG = MoviesSoonFragment.class.getCanonicalName();

    private DashboardViewModel dashboardViewModel;
    private MoviesSoonAdapter moviesSoonAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(getActivity()).get(DashboardViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_popular, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies_popular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moviesSoonAdapter = new MoviesSoonAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, moviesSoonAdapter.getItemAt(currentSelection).toString());
                });
        recyclerView.setAdapter(moviesSoonAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);
    }

    @Override
    public void addObservers() {
        dashboardViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIES_POPULAR) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                    hideLoader();

                } else {
                    hideLoader();
                    showFailure(false, apiStatusModel.getMessage());
                }
            }
        });

        dashboardViewModel.getMovieSoonModelsMutable().observe(this, moviePopularModels -> {
            if (moviePopularModels != null)
                moviesSoonAdapter.setItems(moviePopularModels);
        });
    }

    @Override
    public void requestData() {
        dashboardViewModel.requestMoviesSoon();
    }

    @Override
    public void onClick(View v) {

    }
}
