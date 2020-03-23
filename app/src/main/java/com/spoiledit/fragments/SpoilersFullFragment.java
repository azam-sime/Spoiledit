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
import com.spoiledit.adapters.SpoilerFullAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsMovieViewModel;

public class SpoilersFullFragment extends RootFragment {
    public static final String TAG = SpoilersFullFragment.class.getCanonicalName();

    private DetailsMovieViewModel detailsMovieViewModel;
    private SpoilerFullAdapter spoilerFullAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsMovieViewModel = ViewModelProviders.of(getActivity()).get(DetailsMovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_full, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers_full);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_full);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerFullAdapter = new SpoilerFullAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    ((DetailsMovieActivity) getActivity()).gotoCommentsActivity(
                            spoilerFullAdapter.getItemAt(currentSelection));
                    spoilerFullAdapter.removeLastSelection();
                });

        recyclerView.setAdapter(spoilerFullAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilerFullAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        detailsMovieViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_SPOILERS_FULL) {
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

        detailsMovieViewModel.getSpoilerFullModelsMutable().observe(this, spoilerFullModels -> {
            if (spoilerFullModels != null)
                spoilerFullAdapter.setItems(spoilerFullModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilerFullAdapter.getItemCount() == 0);
    }

    @Override
    public void requestData() {
        detailsMovieViewModel.requestSpoilerFull();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilerFullAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
