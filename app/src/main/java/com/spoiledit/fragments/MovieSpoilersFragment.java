package com.spoiledit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.activities.AddSpoilerActivity;
import com.spoiledit.activities.DetailsMovieActivity;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.viewmodels.DetailsSpoilersViewModel;

public class MovieSpoilersFragment extends RootFragment {
    public static final String TAG = MovieSpoilersFragment.class.getCanonicalName();

    private DetailsSpoilersViewModel detailsSpoilersViewModel;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private SpoilersFullFragment fullFragment;
    private SpoilersBriefFragment briefFragment;
    private SpoilersEndingFragment endingFragment;

    private ExtendedFloatingActionButton fabAddNewSpoiler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsSpoilersViewModel = ViewModelProviders.of(getActivity()).get(DetailsSpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_spoilers, container, false);
    }

    @Override
    public void setUpToolbar() {
        setupToolBar("Movie Spoilers", true);
    }

    @Override
    public void initUi(View view) {
        hideLoader();

        tabLayout = view.findViewById(R.id.tl_spoilers);
        viewPager = view.findViewById(R.id.vp_spoilers);

        fabAddNewSpoiler = view.findViewById(R.id.fab_add_spoiler);
    }

    @Override
    public void initialiseListener(View view) {
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        fabAddNewSpoiler.setOnClickListener(this);
    }

    @Override
    public void setUpViewPager(View view) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPager.setOffscreenPageLimit(3);

        fullFragment = new SpoilersFullFragment();
        viewPagerAdapter.addFragment(fullFragment, getResString(R.string.full_spoiler));

        briefFragment = new SpoilersBriefFragment();
        viewPagerAdapter.addFragment(briefFragment, getResString(R.string.brief_summary));

        endingFragment = new SpoilersEndingFragment();
        viewPagerAdapter.addFragment(endingFragment, getResString(R.string.just_ending));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public void addObservers() {
        detailsSpoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.SPOILERS_ADD) {
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

                    fullFragment.onRefresh();
                    briefFragment.onRefresh();
                    endingFragment.onRefresh();
                }
            }
        });

        detailsSpoilersViewModel.getSpoilerFullModelsMutable().observe(this, spoilerFullModels -> {
            int size = spoilerFullModels == null ? 0 : spoilerFullModels.size();
            tabLayout.getTabAt(0).setText("Full\nSpoiler (" + size + ")");
        });

        detailsSpoilersViewModel.getSpoilerBriefModelsMutable().observe(this, spoilerBriefModels -> {
            int size = spoilerBriefModels == null ? 0 : spoilerBriefModels.size();
            tabLayout.getTabAt(1).setText("Brief\nSummary (" + size + ")");
        });

        detailsSpoilersViewModel.getSpoilerEndingModelsMutable().observe(this, spoilerEndingModels -> {
            int size = spoilerEndingModels == null ? 0 : spoilerEndingModels.size();
            tabLayout.getTabAt(2).setText("Just\nEnding (" + size + ")");
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add_spoiler) {
            if (loggedIn())
            startActivityForResult(new Intent(getContext(), AddSpoilerActivity.class), App.Intent.Request.ADD_SPOILER);
            else
                showNotLoginError();
        } else if (v.getId() == R.id.iv_back) {
            getActivity().onBackPressed();
        } else
            super.onClick(v);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == App.Intent.Request.ADD_SPOILER) {
            if (resultCode == Activity.RESULT_OK) {
                showLoader();
                detailsSpoilersViewModel.addSpoilers(data.getParcelableExtra(AddSpoilerActivity.RESULT_EXTRA_MODEL));
            }
        }
    }
}
