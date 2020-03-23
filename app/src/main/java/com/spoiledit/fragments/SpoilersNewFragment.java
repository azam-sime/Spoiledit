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
import com.spoiledit.adapters.SpoilersNewAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DashboardViewModel;

public class SpoilersNewFragment extends RootFragment {
    public static final String TAG = SpoilersNewFragment.class.getCanonicalName();

    private DashboardViewModel dashboardViewModel;
    private SpoilersNewAdapter spoilersNewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(getActivity()).get(DashboardViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_new, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilersNewAdapter = new SpoilersNewAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, spoilersNewAdapter.getItemAt(currentSelection).toString());
                });

        recyclerView.setAdapter(spoilersNewAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilersNewAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        dashboardViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.SPOILERS_NEW) {
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

        dashboardViewModel.getSpoilerModelsMutable().observe(this, spoilerModels -> {
            if (spoilerModels != null)
                spoilersNewAdapter.setItems(spoilerModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void requestData() {
        showLoader();
        dashboardViewModel.requestSpoilers();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void gotoNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilersNewAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
