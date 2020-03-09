package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.activities.DashboardActivity;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.viewmodels.DashboardViewModel;

public class MoviesFragment extends RootFragment {
    public static final String TAG = MoviesFragment.class.getCanonicalName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private MoviesPopularFragment popularFragment;
    private MoviesRecentFragment recentFragment;
    private MoviesSoonFragment soonFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void initUi(View view) {
        tabLayout = ((DashboardActivity) getActivity()).getTabLayout();
        viewPager = view.findViewById(R.id.vp_movies);
    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setData(View view) {

    }

    @Override
    public void setUpViewPager(View view) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(3);

        popularFragment = new MoviesPopularFragment();
        viewPagerAdapter.addFragment(popularFragment, getResString(R.string.popular_movies));

        recentFragment = new MoviesRecentFragment();
        viewPagerAdapter.addFragment(recentFragment, getResString(R.string.recent_movies));

        soonFragment = new MoviesSoonFragment();
        viewPagerAdapter.addFragment(soonFragment, getResString(R.string.coming_soon));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public void onClick(View v) {

    }
}
