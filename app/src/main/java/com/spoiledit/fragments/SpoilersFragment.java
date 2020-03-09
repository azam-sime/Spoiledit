package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.activities.DashboardActivity;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.repos.SpoilersRepo;
import com.spoiledit.viewmodels.SpoilersViewModel;

public class SpoilersFragment extends RootFragment {
    public static final String TAG = SpoilersFragment.class.getCanonicalName();

    private SpoilersViewModel spoilersViewModel;

    private ExtendedFloatingActionButton fabAddNewSpoiler;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private SpoilersFullFragment fullFragment;
    private SpoilersBriefFragment briefFragment;
    private SpoilersEndingFragment endingFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spoilersViewModel = ViewModelProviders.of(this,
                new SpoilersViewModel.SpoilersViewModelFactory(SpoilersRepo.initialise(getContext())))
                .get(SpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers, container, false);
    }

    @Override
    public void initUi(View view) {
        fabAddNewSpoiler = view.findViewById(R.id.fab_add_spoiler);

        tabLayout = ((DashboardActivity) getActivity()).getTabLayout();
        viewPager = view.findViewById(R.id.vp_spoilers);
    }

    @Override
    public void initialiseListener(View view) {
        fabAddNewSpoiler.setOnClickListener(this);
    }

    @Override
    public void setData(View view) {

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
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add_spoiler) {
            Toast.makeText(getContext(), "Implementing soon...", Toast.LENGTH_SHORT).show();
        }
    }
}
