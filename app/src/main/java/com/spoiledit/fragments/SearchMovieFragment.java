package com.spoiledit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spoiledit.R;
import com.spoiledit.activities.DetailsMovieActivity;
import com.spoiledit.adapters.SearchMovieAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Type;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.SearchViewModel;

public class SearchMovieFragment extends RootFragment {
    public static final String TAG = SearchMovieFragment.class.getCanonicalName();

    private SearchViewModel searchViewModel;
    private SearchMovieAdapter searchMovieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int type;

    public SearchMovieFragment(int type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_search_movies);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_search_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchMovieAdapter = new SearchMovieAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    searchViewModel.requestMovieDetails(
                            searchMovieAdapter.getItemAt(currentSelection).getId()
                    );
                });

        recyclerView.setAdapter(searchMovieAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        searchMovieAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        searchViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if ((type == Type.Search.MOVIES_BY_TITLE && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_TITLE)
                    || (type == Type.Search.MOVIES_BY_PERSON && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_PERSON)
                    || (type == Type.Search.MOVIES_BY_KEYWORD && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_KEYWORD)
                    || (type == Type.Search.MOVIES_BY_COMPANIES && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_COMPANIES)) {

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
                    searchMovieAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        startActivity(new Intent(getContext(), DetailsMovieActivity.class));
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            }
        });

        if (type == Type.Search.MOVIES_BY_TITLE) {
            searchViewModel.getMoviesByTitleMutable().observe(this, searchMovieModels -> {
                if (searchMovieModels != null)
                    searchMovieAdapter.setItems(searchMovieModels);
            });
        } else if (type == Type.Search.MOVIES_BY_PERSON) {
            searchViewModel.getMoviesByPersonMutable().observe(this, searchMovieModels -> {
                if (searchMovieModels != null)
                    searchMovieAdapter.setItems(searchMovieModels);
            });
        } else if (type == Type.Search.MOVIES_BY_KEYWORD) {
            searchViewModel.getMoviesByKeywordMutable().observe(this, searchMovieModels -> {
                if (searchMovieModels != null)
                    searchMovieAdapter.setItems(searchMovieModels);
            });
        } else if (type == Type.Search.MOVIES_BY_COMPANIES) {
            searchViewModel.getMoviesByCompaniesMutable().observe(this, searchMovieModels -> {
                if (searchMovieModels != null)
                    searchMovieAdapter.setItems(searchMovieModels);
            });
        }
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void requestData() {
        showLoader();
        if (type == Type.Search.MOVIES_BY_TITLE)
            searchViewModel.requestMoviesByTitle();
        else if (type == Type.Search.MOVIES_BY_PERSON)
            searchViewModel.requestMoviesByPerson();
        else if (type == Type.Search.MOVIES_BY_KEYWORD)
            searchViewModel.requestMoviesByKeyword();
        else if (type == Type.Search.MOVIES_BY_COMPANIES)
            searchViewModel.requestMoviesByCompanies();
    }

    @Override
    public void onRefresh() {
        if (type == Type.Search.MOVIES_BY_TITLE)
            searchViewModel.requestMoviesByTitle();
        else if (type == Type.Search.MOVIES_BY_PERSON)
            searchViewModel.requestMoviesByPerson();
        else if (type == Type.Search.MOVIES_BY_KEYWORD)
            searchViewModel.requestMoviesByKeyword();
        else if (type == Type.Search.MOVIES_BY_COMPANIES)
            searchViewModel.requestMoviesByCompanies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchMovieAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
