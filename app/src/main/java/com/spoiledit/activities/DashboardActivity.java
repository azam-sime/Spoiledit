package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.adapters.ViewPager2Adapter;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Type;
import com.spoiledit.fragments.MoviesFragment;
import com.spoiledit.fragments.NavigationFragment;
import com.spoiledit.fragments.SpoilersNewFragment;
import com.spoiledit.listeners.PagerChangeListener;
import com.spoiledit.listeners.TextChangeListener;
import com.spoiledit.repos.DashboardRepo;
import com.spoiledit.repos.SearchRepo;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.PreferenceUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DashboardViewModel;
import com.spoiledit.widget.SearchWindow;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends RootActivity {
    public static final String TAG = DashboardActivity.class.getCanonicalName();

    private DashboardViewModel dashboardViewModel;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TabLayout tabLayout;
    private BottomNavigationView bnvDashboard;

    private LinearLayout llNavigationCont;
    private NavigationFragment navigationFragment;

    private MaterialCardView mcvSearchBar;
    private EditText etSearch;
    private Timer timerSearch;
    private SearchWindow searchWindow;
    private boolean setText = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(this,
                new DashboardViewModel.Factory(new DashboardRepo()))
                .get(DashboardViewModel.class);

        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void setUpToolBar() {
        setupPopcornIconOnly();
    }

    @Override
    public void initUi() {
        appBarLayout = findViewById(R.id.abl_dashboard);
        collapsingToolbarLayout = findViewById(R.id.ctl_dashboard);
        tabLayout = findViewById(R.id.tl_dashboard);
        bnvDashboard = findViewById(R.id.bnv_dashboard);

        mcvSearchBar = findViewById(R.id.mcv_searchbar);
        etSearch = mcvSearchBar.findViewById(R.id.et_search);

        llNavigationCont = findViewById(R.id.ll_navigation_cont);
        navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.frag_navigation);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.iv_menu).setOnClickListener(this);

        etSearch.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (setText) {
                    setText = false;
                    return;
                }

                if (timerSearch != null) {
                    timerSearch.cancel();
                    timerSearch = null;
                }

                timerSearch = new Timer();
                timerSearch.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> dashboardViewModel
                                .requestSearchAutoCompleteValues(etSearch.getText().toString().trim()));
                    }
                }, 1000);
            }
        });

        bnvDashboard.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_movies) {
                ViewUtils.hideViews(llNavigationCont);
                ViewUtils.showViews(tabLayout, mcvSearchBar);
                appBarLayout.setExpanded(true, true);
//                viewPager.setCurrentItem(0, true);
                switchBetweenFragments(true);

                return true;
            } else if (menuItem.getItemId() == R.id.menu_spoilers) {
                ViewUtils.hideViews(llNavigationCont);
                ViewUtils.hideViews(tabLayout, mcvSearchBar);
                appBarLayout.setExpanded(false, true);

//                viewPager.setCurrentItem(1, true);
                switchBetweenFragments(false);

                return true;
            } else if (menuItem.getItemId() == R.id.menu_settings) {
                ViewUtils.showViews(llNavigationCont);

                return true;
            }
            return false;
        });
    }

    @Override
    public void addObservers() {
        dashboardViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.USER_LOGOUT) {
                PreferenceUtils.clearPreferences(this);
                startActivity(new Intent(this, SplashActivity.class));
                finish();

            } else if (apiStatusModel.getApi() == Constants.Api.SEARCH_AUTO_COMPLETE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT)
                    showLoader();
                else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        initSearchWindow(null);
                }
            }
        });

        dashboardViewModel.getSearchValues().observe(this, strings -> {
            if (searchWindow != null) {
                searchWindow.setSearchValues(strings);
                if (!searchWindow.isShowing())
                    searchWindow.showAsDropDown(mcvSearchBar);
            } else
                initSearchWindow(strings);
        });
    }

    @Override
    public void setData() {
        switchBetweenFragments(true);

        if (!loggedIn()) {
            DialogUtils.createNonCancelableDialog(this, Type.Info.HEY,
                    "You're not logged in. Please login or register yourself to enjoy more features.",
                    "Login", this::onPopcornClick, "Cancel", null);
        }
    }

    private void switchBetweenFragments(boolean movies) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (movies) {
            if (fragmentManager.findFragmentByTag(MoviesFragment.TAG) != null)
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(MoviesFragment.TAG)).commit();
            else
                fragmentManager.beginTransaction().add(R.id.ll_fragment_cont, new MoviesFragment(), MoviesFragment.TAG).commit();
            if (fragmentManager.findFragmentByTag(SpoilersNewFragment.TAG) != null)
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(SpoilersNewFragment.TAG)).commit();
        } else {
            if (fragmentManager.findFragmentByTag(SpoilersNewFragment.TAG) != null)
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(SpoilersNewFragment.TAG)).commit();
            else
                fragmentManager.beginTransaction().add(R.id.ll_fragment_cont, new SpoilersNewFragment(), SpoilersNewFragment.TAG).commit();
            if (fragmentManager.findFragmentByTag(MoviesFragment.TAG) != null)
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(MoviesFragment.TAG)).commit();
        }
    }

    private void initSearchWindow(List<String> strings) {
        if (searchWindow == null) {
            searchWindow = new SearchWindow(this);
            searchWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            searchWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            searchWindow.setOutsideTouchable(true);
            searchWindow.setFocusable(true);
            searchWindow.showAsDropDown(etSearch);
            searchWindow.setOnItemSelectionListener((lastSelection, currentSelection) -> {
                searchWindow.dismiss();
                gotoSearchActivity(searchWindow.getSearchValue(currentSelection));
            });
            if (strings != null)
                searchWindow.setSearchValues(strings);
        }
    }

    private void gotoSearchActivity(String searchValue) {
        SearchRepo.initialise(searchValue);
        setText = true;
        etSearch.setText("");

        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        navigationFragment.onActivityResult(requestCode, resultCode, data);
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
