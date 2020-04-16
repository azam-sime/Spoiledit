package com.spoiledit.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.spoiledit.R;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Type;
import com.spoiledit.fragments.SearchMovieFragment;
import com.spoiledit.listeners.TextChangeListener;
import com.spoiledit.repos.SearchRepo;
import com.spoiledit.viewmodels.SearchViewModel;
import com.spoiledit.widget.SearchWindow;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends RootActivity {
    public static final String TAG = SearchActivity.class.getCanonicalName();

    private SearchViewModel searchViewModel;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchMovieFragment movieFragmentTitle, movieFragmentPerson, movieFragmentKeyword, movieFragmentCompanies;

    private MaterialCardView mcvSearchBar;
    private EditText etSearch;
    private Timer timerSearch;
    private SearchWindow searchWindow;
    private boolean setText = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {
        tabLayout = findViewById(R.id.tl_search);

        mcvSearchBar = findViewById(R.id.mcv_searchbar);
        etSearch = mcvSearchBar.findViewById(R.id.et_search);

        viewPager = findViewById(R.id.vp_search);
    }

    @Override
    public void initialiseListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);

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
                        runOnUiThread(() -> searchViewModel.requestSearchAutoCompleteValues(etSearch.getText().toString().trim()));
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        movieFragmentTitle = new SearchMovieFragment(Type.Search.MOVIES_BY_TITLE);
        movieFragmentPerson = new SearchMovieFragment(Type.Search.MOVIES_BY_PERSON);
        movieFragmentKeyword = new SearchMovieFragment(Type.Search.MOVIES_BY_KEYWORD);
        movieFragmentCompanies = new SearchMovieFragment(Type.Search.MOVIES_BY_COMPANIES);

        viewPagerAdapter.addFragment(movieFragmentTitle, "Title");
        viewPagerAdapter.addFragment(movieFragmentPerson, "Person");
        viewPagerAdapter.addFragment(movieFragmentKeyword, "Keyword");
        viewPagerAdapter.addFragment(movieFragmentCompanies, "Companies");

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public void addObservers() {
        searchViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.SEARCH_AUTO_COMPLETE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT)
                    showLoader();
                else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        initSearchWindow(null);
                }
            }
        });

        searchViewModel.getSearchValues().observe(this, strings -> {
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
        setText = true;
        etSearch.setText(searchViewModel.getSearchValue());
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
                refreshSearchResults(searchWindow.getSearchValue(currentSelection));
            });
            if (strings != null)
                searchWindow.setSearchValues(strings);
        }
    }

    private void refreshSearchResults(String searchValue) {
        SearchRepo.getInstance().setSearchValue(searchValue);

        movieFragmentTitle.requestData();
        movieFragmentPerson.requestData();
        movieFragmentKeyword.requestData();
        movieFragmentCompanies.requestData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            onBackPressed();
        else
            super.onClick(v);
    }
}
