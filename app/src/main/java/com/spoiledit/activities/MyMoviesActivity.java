package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.spoiledit.R;
import com.spoiledit.adapters.MoviesPopularAdapter;
import com.spoiledit.adapters.MyMoviesAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.MyRepo;
import com.spoiledit.repos.ProfileRepo;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.ChangePasswordViewModel;
import com.spoiledit.viewmodels.MyMoviesViewModel;

public class MyMoviesActivity extends RootActivity {
    public static final String TAG = MyMoviesActivity.class.getCanonicalName();

    private MyMoviesViewModel myMoviesViewModel;
    private MyMoviesAdapter moviesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myMoviesViewModel = ViewModelProviders.of(this,
                new MyMoviesViewModel.Factory(new MyRepo.MyMoviesRepo()))
                .get(MyMoviesViewModel.class);
        setContentView(R.layout.activity_my_movies);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("My Watchlist", false);
    }

    @Override
    public void initUi() {
        swipeRefreshLayout = findViewById(R.id.srl_my_movies);
    }

    @Override
    public void initialiseListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.rv_my_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moviesAdapter = new MyMoviesAdapter(this, (lastSelection, currentSelection) -> {
            myMoviesViewModel.requestMovieDetails(
                    moviesAdapter.getItemAt(currentSelection).getId()
            );
        }, position -> {
            showInterrupt("Coming Soon", true);
        });

        recyclerView.setAdapter(moviesAdapter);
        ViewUtils.addFabOffset(this, recyclerView);

        moviesAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        hideLoader();

        myMoviesViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MY_WATCHLIST) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    if (!swipeRefreshLayout.isRefreshing())
                        showLoader(apiStatusModel.getMessage());
                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        showFailure(false, apiStatusModel.getMessage());
                }

            } else if (apiStatusModel.getApi() == Constants.Api.MOVIES_DETAILS) {
                if (apiStatusModel.getStatus() != Status.Request.API_HIT) {
                    moviesAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        startActivity(new Intent(this, DetailsMovieActivity.class));
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            }
        });

        myMoviesViewModel.getMyMoviesMutable().observe(this, myMovieModels -> {
            if (myMovieModels != null)
                moviesAdapter.setItems(myMovieModels);
        });
    }

    @Override
    public void requestData() {
        showLoader();
        myMoviesViewModel.requestMyMovies();
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);

        toggleTvNoData(moviesAdapter.getItemCount() == 0);
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        moviesAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
