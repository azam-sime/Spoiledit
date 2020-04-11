package com.spoiledit.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spoiledit.R;
import com.spoiledit.adapters.MySpoilersAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.repos.MyRepo;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.MySpoilersViewModel;

public class MySpoilersActivity extends RootActivity {
    public static final String TAG = MySpoilersActivity.class.getCanonicalName();

    private MySpoilersViewModel mySpoilersViewModel;
    private MySpoilersAdapter spoilersAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mySpoilersViewModel = ViewModelProviders.of(this,
                new MySpoilersViewModel.Factory(new MyRepo.MySpoilersRepo()))
                .get(MySpoilersViewModel.class);
        setContentView(R.layout.activity_my_spoilers);
    }

    @Override
    public void setUpToolBar() {
        setupToolBar("My Spoilers", true);
    }

    @Override
    public void initUi() {
        swipeRefreshLayout = findViewById(R.id.srl_my_spoilers);
    }

    @Override
    public void initialiseListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.rv_my_spoilers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spoilersAdapter = new MySpoilersAdapter(this, (lastSelection, currentSelection) -> {

        });

        recyclerView.setAdapter(spoilersAdapter);
        ViewUtils.addFabOffset(this, recyclerView);

        spoilersAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        mySpoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MY_SPOILERS) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    toggleViews(false);
                    showLoader(apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                    toggleViews(true);
                    hideLoader();
                    showFailure(false, apiStatusModel.getMessage());

                } else if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS) {
                    toggleViews(false);
                    hideLoader();
                }
            }
        });

        mySpoilersViewModel.getMySpoilersMutable().observe(this, mySpoilerModels -> {
            if (mySpoilerModels != null)
                spoilersAdapter.setItems(mySpoilerModels);
        });
    }

    @Override
    public void requestData() {
        showLoader();
        mySpoilersViewModel.requestMySpoilers();
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilersAdapter.getItemCount() == 0);
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        spoilersAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
