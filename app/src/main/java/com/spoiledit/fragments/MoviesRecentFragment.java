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
import com.spoiledit.adapters.MoviesRecentAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.MoviesViewModel;

public class MoviesRecentFragment extends RootFragment {
    public static final String TAG = MoviesRecentFragment.class.getCanonicalName();

    private MoviesViewModel moviesViewModel;
    private MoviesRecentAdapter recentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesViewModel = ViewModelProviders.of(getParentFragment()).get(MoviesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_recent, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies_recent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recentAdapter = new MoviesRecentAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, recentAdapter.getItemAt(currentSelection).toString());
                });
        recyclerView.setAdapter(recentAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);
    }

    @Override
    public void addObservers() {
        moviesViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIES_RECENTS) {
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

        moviesViewModel.getMovieRecentModelsMutable().observe(this, movieRecentModels -> {
            if (movieRecentModels != null)
                recentAdapter.setItems(movieRecentModels);
        });
    }

    @Override
    public void requestData() {
        moviesViewModel.requestMoviesRecent();
    }

    @Override
    public void onClick(View v) {

    }
}
