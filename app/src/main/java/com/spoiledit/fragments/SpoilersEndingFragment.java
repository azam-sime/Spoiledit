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
import com.spoiledit.adapters.SpoilerEndingAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsMovieViewModel;

public class SpoilersEndingFragment extends RootFragment {
    public static final String TAG = SpoilersEndingFragment.class.getCanonicalName();

    private DetailsMovieViewModel detailsMovieViewModel;
    private SpoilerEndingAdapter spoilerEndingAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsMovieViewModel = ViewModelProviders.of(getActivity()).get(DetailsMovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_ending, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers_ending);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_ending);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerEndingAdapter = new SpoilerEndingAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    ((DetailsMovieActivity) getActivity()).gotoCommentsActivity(
                            spoilerEndingAdapter.getItemAt(currentSelection));
                    spoilerEndingAdapter.removeLastSelection();
                });

        recyclerView.setAdapter(spoilerEndingAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilerEndingAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        detailsMovieViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_SPOILERS_ENDING) {
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

        detailsMovieViewModel.getSpoilerEndingModelsMutable().observe(this, spoilerEndingModels -> {
            if (spoilerEndingModels != null)
                spoilerEndingAdapter.setItems(spoilerEndingModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilerEndingAdapter.getItemCount() == 0);
    }

    @Override
    public void requestData() {
        detailsMovieViewModel.requestSpoilerEnding();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilerEndingAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
