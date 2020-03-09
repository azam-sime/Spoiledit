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
import com.spoiledit.adapters.SpoilerFullAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.SpoilersViewModel;

public class SpoilersFullFragment extends RootFragment {
    public static final String TAG = SpoilersFullFragment.class.getCanonicalName();

    private SpoilersViewModel spoilersViewModel;
    private SpoilerFullAdapter spoilerFullAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spoilersViewModel = ViewModelProviders.of(getParentFragment()).get(SpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_full, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_full);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerFullAdapter = new SpoilerFullAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, spoilerFullAdapter.getItemAt(currentSelection).toString());
                });
        recyclerView.setAdapter(spoilerFullAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);
    }

    @Override
    public void addObservers() {
        spoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.SPOILERS_FULL) {
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

        spoilersViewModel.getSpoilerFullModelsMutable().observe(this, spoilerFullModels -> {
            if (spoilerFullModels != null)
                spoilerFullAdapter.setItems(spoilerFullModels);
        });
    }

    @Override
    public void requestData() {
        spoilersViewModel.requestSpoilerFull();
    }
}
