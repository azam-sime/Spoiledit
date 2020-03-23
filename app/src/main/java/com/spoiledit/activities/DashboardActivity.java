package com.spoiledit.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.fragments.MoviesFragment;
import com.spoiledit.fragments.SpoilersNewFragment;
import com.spoiledit.listeners.PagerChangeListener;
import com.spoiledit.repos.DashboardRepo;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DashboardViewModel;

public class DashboardActivity extends RootActivity {
    public static final String TAG = DashboardActivity.class.getCanonicalName();

    private DrawerLayout drawerLayout;
    private DashboardViewModel dashboardViewModel;

    private TabLayout tabLayout;
    private BottomNavigationView bnvDashboard;
    private MaterialCardView mcvSearchBar;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private MoviesFragment moviesFragment;
    private SpoilersNewFragment spoilersNewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(this,
                new DashboardViewModel.DashboardViewModelFactory(DashboardRepo.initialise()))
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
        mcvSearchBar = findViewById(R.id.mcv_searchbar);

        viewPager = findViewById(R.id.vp_dashboard);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.iv_menu).setOnClickListener(this);

        bnvDashboard.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_movies) {
                ViewUtils.showViews(tabLayout, mcvSearchBar);
                viewPager.setCurrentItem(0, true);

                return true;
            } else if (menuItem.getItemId() == R.id.menu_spoilers) {
                ViewUtils.invisibleViews(tabLayout, mcvSearchBar);
                viewPager.setCurrentItem(1, true);

                return true;
            }
            return false;
        });
    }

    @Override
    public void setUpViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new PagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                boolean onMovies = position == 0;
                ViewUtils.toggleViewVisibility(true, onMovies, tabLayout, mcvSearchBar);
                bnvDashboard.setSelectedItemId(onMovies ? R.id.menu_movies : R.id.menu_spoilers);
            }
        });

        moviesFragment = new MoviesFragment();
        viewPagerAdapter.addFragment(moviesFragment, getResString(R.string.movies));

        spoilersNewFragment = new SpoilersNewFragment();
        viewPagerAdapter.addFragment(spoilersNewFragment, getResString(R.string.spoilers));

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_menu) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else
            super.onClick(v);
    }

    public void switchMoviesFragment(boolean switchToWatchList) {

    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            ViewUtils.showViews(bnvDashboard);
            bnvDashboard.setSelectedItemId(R.id.menu_movies);
        } else
            super.onBackPressed();
    }
}
