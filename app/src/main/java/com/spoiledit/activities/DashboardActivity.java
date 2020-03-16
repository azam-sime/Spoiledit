package com.spoiledit.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.fragments.MoviesFragment;
import com.spoiledit.fragments.SettingsFragment;
import com.spoiledit.fragments.SpoilersFragment;
import com.spoiledit.listeners.PagerChangeListener;
import com.spoiledit.repos.DashboardRepo;
import com.spoiledit.viewmodels.DashboardViewModel;

public class DashboardActivity extends RootActivity {
    public static final String TAG = DashboardActivity.class.getCanonicalName();

    private DrawerLayout drawerLayout;
    private DashboardViewModel dashboardViewModel;

    private TabLayout tabLayout;
    private BottomNavigationView bnvDashboard;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private MoviesFragment moviesFragment;
    private SpoilersFragment spoilersFragment;
//    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(this,
                new DashboardViewModel.DashboardViewModelFactory(DashboardRepo.initialise(this)))
                .get(DashboardViewModel.class);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {
        drawerLayout = findViewById(R.id.drawer_dashboard);

        tabLayout = findViewById(R.id.tl_dashboard);
        bnvDashboard = findViewById(R.id.bnv_dashboard);
        viewPager = findViewById(R.id.vp_dashboard);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.iv_menu).setOnClickListener(this);

        bnvDashboard.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_movies) {
                viewPager.setCurrentItem(0, true);
                return true;
            } else if (menuItem.getItemId() == R.id.menu_spoilers) {
                viewPager.setCurrentItem(1, true);
                return true;
            } /*else if (menuItem.getItemId() == R.id.menu_settings) {
                viewPager.setCurrentItem(2, true);
                return true;
            }*/
            return false;
        });
    }

    @Override
    public void setUpViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new PagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                bnvDashboard.setSelectedItemId(position == 0 ? R.id.menu_movies
                        : /*position == 1 ?*/ R.id.menu_spoilers /*: R.id.menu_settings*/);
            }
        });

        moviesFragment = new MoviesFragment();
        viewPagerAdapter.addFragment(moviesFragment, getResString(R.string.movies));

        spoilersFragment = new SpoilersFragment();
        viewPagerAdapter.addFragment(spoilersFragment, getResString(R.string.spoilers));

//        settingsFragment = new SettingsFragment();
//        viewPagerAdapter.addFragment(settingsFragment, getResString(R.string.settings));

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_menu) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else
            super.onClick(v);
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
