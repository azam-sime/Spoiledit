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
import com.spoiledit.adapters.MoviesUpcomingAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.MoviesViewModel;

public class MoviesUpcomingFragment extends RootFragment {
    public static final String TAG = MoviesUpcomingFragment.class.getCanonicalName();

    private MoviesViewModel moviesViewModel;
    private MoviesUpcomingAdapter moviesUpcomingAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesViewModel = ViewModelProviders.of(getParentFragment()).get(MoviesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_upcoming, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies_upcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moviesUpcomingAdapter = new MoviesUpcomingAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, moviesUpcomingAdapter.getItemAt(currentSelection).toString());
                });
        recyclerView.setAdapter(moviesUpcomingAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);
    }

    @Override
    public void addObservers() {
        moviesViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIES_UPCOMING) {
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

        moviesViewModel.getMovieUpcomingModelsMutable().observe(this, movieUpcomingModels -> {
            if (movieUpcomingModels != null)
                moviesUpcomingAdapter.setItems(movieUpcomingModels);
        });
    }

    @Override
    public void requestData() {
        moviesViewModel.requestMoviesSoon();
    }

    @Override
    public void onClick(View v) {

    }
}
