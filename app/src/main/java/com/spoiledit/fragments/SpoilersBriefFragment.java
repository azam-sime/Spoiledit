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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spoiledit.R;
import com.spoiledit.activities.DetailsMovieActivity;
import com.spoiledit.adapters.SpoilerBriefAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsMovieViewModel;

public class SpoilersBriefFragment extends RootFragment {
    public static final String TAG = SpoilersBriefFragment.class.getCanonicalName();

    private DetailsMovieViewModel detailsMovieViewModel;
    private SpoilerBriefAdapter spoilerBriefAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsMovieViewModel = ViewModelProviders.of(getActivity()).get(DetailsMovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_brief, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers_brief);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_brief);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerBriefAdapter = new SpoilerBriefAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    ((DetailsMovieActivity) getActivity()).gotoCommentsActivity(
                            spoilerBriefAdapter.getItemAt(currentSelection));
                    spoilerBriefAdapter.removeLastSelection();
                });

        recyclerView.setAdapter(spoilerBriefAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilerBriefAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        detailsMovieViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_SPOILERS_BRIEF) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                    hideLoader();

                } else {
                    hideLoader();
                    setError(apiStatusModel.getMessage());
                    showFailure(false, apiStatusModel.getMessage());
                }
            }
        });

        detailsMovieViewModel.getSpoilerBriefModelsMutable().observe(this, spoilerBriefModels -> {
            if (spoilerBriefModels != null)
                spoilerBriefAdapter.setItems(spoilerBriefModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilerBriefAdapter.getItemCount() == 0);
    }

    @Override
    public void requestData() {
        detailsMovieViewModel.requestSpoilerBrief();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilerBriefAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
