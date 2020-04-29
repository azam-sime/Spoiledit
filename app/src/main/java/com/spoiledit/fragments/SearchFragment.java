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
import com.spoiledit.adapters.SearchAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Type;
import com.spoiledit.models.SearchModel;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.SearchViewModel;

public class SearchFragment extends RootFragment {
    public static final String TAG = SearchFragment.class.getCanonicalName();

    private SearchViewModel searchViewModel;
    private SearchAdapter searchAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int type;
    private int keywordId;

    public SearchFragment(int type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keywordId = 0;
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

        searchAdapter = new SearchAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    if (type == Type.Search.MOVIES_BY_TITLE) {
                        searchViewModel.requestMovieDetails(
                                searchAdapter.getItemAt(currentSelection).getId(), type
                        );
                    } else if (type == Type.Search.MOVIES_BY_KEYWORD) {
                        SearchModel searchModel = searchAdapter.getItemAt(currentSelection);
                        if (searchModel.getType() == Type.Search.MOVIES_FROM_KEYWORD)
                            searchViewModel.requestMovieDetails(searchModel.getId(), type);
                        else {
                            keywordId = searchModel.getId();
                            searchViewModel.requestKeywordDetails(keywordId);
                        }
                    } else if (type == Type.Search.MOVIES_BY_PERSON) {
                        searchViewModel.requestPersonDetails(
                                searchAdapter.getItemAt(currentSelection).getId()
                        );
                    } else if (type == Type.Search.MOVIES_BY_COMPANIES) {
                        searchViewModel.requestCompanyDetails(
                                searchAdapter.getItemAt(currentSelection).getId()
                        );
                    }
                });

        recyclerView.setAdapter(searchAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        searchAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        searchViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if ((type == Type.Search.MOVIES_BY_TITLE
                    && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_TITLE)
                    || (type == Type.Search.MOVIES_BY_PERSON
                    && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_PERSON)
                    || (type == Type.Search.MOVIES_BY_KEYWORD
                    && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_KEYWORD)
                    || (type == Type.Search.MOVIES_BY_COMPANIES
                    && apiStatusModel.getApi() == Constants.Api.SEARCH_MOVIE_BY_COMPANIES)) {

                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    if (!swipeRefreshLayout.isRefreshing())
                        showLoader(apiStatusModel.getMessage());
                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        showFailure(false, apiStatusModel.getMessage());
                }

            } else if (apiStatusModel.getApi() == Constants.Api.MOVIES_DETAILS
                    && apiStatusModel.getFromScreen() == type) {
                if (apiStatusModel.getStatus() != Status.Request.API_HIT) {
                    searchAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        startActivity(new Intent(getContext(), DetailsMovieActivity.class));
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            } else if (apiStatusModel.getApi() == Constants.Api.KEYWORD_DETAILS) {
                if (apiStatusModel.getStatus() != Status.Request.API_HIT) {
                    searchAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        showFailure(false, apiStatusModel.getMessage());
                }
            } else if (apiStatusModel.getApi() == Constants.Api.PERSON_DETAILS) {
                if (apiStatusModel.getStatus() != Status.Request.API_HIT) {
                    searchAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        showInterrupt("Coming Soon", true);
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            } else if (apiStatusModel.getApi() == Constants.Api.COMPANY_DETAILS) {
                if (apiStatusModel.getStatus() != Status.Request.API_HIT) {
                    searchAdapter.removeLastSelection();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        showInterrupt("Coming Soon", true);
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            }
        });

        if (type == Type.Search.MOVIES_BY_TITLE) {
            searchViewModel.getMoviesByTitleMutable().observe(this, searchTitleModels -> {
                if (searchTitleModels != null)
                    searchAdapter.setItems(searchTitleModels);
            });
        } else if (type == Type.Search.MOVIES_BY_PERSON) {
            searchViewModel.getMoviesByPersonMutable().observe(this, searchPersonModels -> {
                if (searchPersonModels != null)
                    searchAdapter.setItems(searchPersonModels);
            });
        } else if (type == Type.Search.MOVIES_BY_KEYWORD) {
            searchViewModel.getMoviesByKeywordMutable().observe(this, searchKeywordModels -> {
                if (searchKeywordModels != null)
                    searchAdapter.setItems(searchKeywordModels);
            });
            searchViewModel.getMoviesFromKeywordMutable().observe(this, searchKeywordMovieModels -> {
                if (searchKeywordMovieModels != null)
                    searchAdapter.setItems(searchKeywordMovieModels);
            });
        } else if (type == Type.Search.MOVIES_BY_COMPANIES) {
            searchViewModel.getMoviesByCompaniesMutable().observe(this, searchCompanyModels -> {
                if (searchCompanyModels != null)
                    searchAdapter.setItems(searchCompanyModels);
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
        else if (type == Type.Search.MOVIES_BY_KEYWORD) {
            if (keywordId != 0)
                searchViewModel.requestKeywordDetails(keywordId);
            else
                searchViewModel.requestMoviesByKeyword();
        } else if (type == Type.Search.MOVIES_BY_COMPANIES)
            searchViewModel.requestMoviesByCompanies();
    }

    @Override
    public void onRefresh() {
        if (type == Type.Search.MOVIES_BY_TITLE)
            searchViewModel.requestMoviesByTitle();
        else if (type == Type.Search.MOVIES_BY_PERSON)
            searchViewModel.requestMoviesByPerson();
        else if (type == Type.Search.MOVIES_BY_KEYWORD) {
            if (keywordId != 0)
                searchViewModel.requestKeywordDetails(keywordId);
            else
                searchViewModel.requestMoviesByKeyword();
        } else if (type == Type.Search.MOVIES_BY_COMPANIES)
            searchViewModel.requestMoviesByCompanies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
