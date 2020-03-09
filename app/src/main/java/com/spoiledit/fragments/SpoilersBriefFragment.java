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
import com.spoiledit.adapters.SpoilerBriefAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.SpoilersViewModel;

public class SpoilersBriefFragment extends RootFragment {
    public static final String TAG = SpoilersBriefFragment.class.getCanonicalName();

    private SpoilersViewModel spoilersViewModel;
    private SpoilerBriefAdapter spoilerBriefAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spoilersViewModel = ViewModelProviders.of(getParentFragment()).get(SpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_brief, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_brief);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerBriefAdapter = new SpoilerBriefAdapter(getContext(),
                (lastSelection, currentSelection) -> {
                    LogUtils.logInfo(TAG, spoilerBriefAdapter.getItemAt(currentSelection).toString());
                });
        recyclerView.setAdapter(spoilerBriefAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);
    }

    @Override
    public void addObservers() {
        spoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.SPOILERS_BRIEF) {
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

        spoilersViewModel.getSpoilerBriefModelsMutable().observe(this, spoilerBriefModels -> {
            if (spoilerBriefModels != null)
                spoilerBriefAdapter.setItems(spoilerBriefModels);
        });
    }

    @Override
    public void requestData() {
        spoilersViewModel.requestSpoilerBrief();
    }
}
