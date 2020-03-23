package com.spoiledit.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spoiledit.R;
import com.spoiledit.adapters.ViewPagerAdapter;
import com.spoiledit.fragments.MovieDetailsFragment;
import com.spoiledit.fragments.MovieSpoilersFragment;
import com.spoiledit.listeners.PagerChangeListener;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.repos.CommentsRepo;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsMovieViewModel;

public class DetailsMovieActivity extends RootActivity {
    public static final String TAG = DetailsMovieActivity.class.getCanonicalName();

    public static final int REQUEST_ADD_SPOILER = 1234;

    private DetailsMovieViewModel detailsMovieViewModel;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private BottomNavigationView bnvMovie;

    private MovieDetailsFragment detailsFragment;
    private MovieSpoilersFragment spoilersFragment;

    private boolean autoSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsMovieViewModel = ViewModelProviders.of(this).get(DetailsMovieViewModel.class);
        setContentView(R.layout.activity_movie_details);
    }

    @Override
    public void setUpToolBar() {

    }

    @Override
    public void initUi() {
        viewPager = findViewById(R.id.vp_movie_details);
        bnvMovie = findViewById(R.id.bnv_movie);
    }

    @Override
    public void initialiseListener() {
        bnvMovie.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_buy_now) {
                if (autoSet) {
                    autoSet = false;
                    return false;
                }

                openLinkInWeb(detailsMovieViewModel.getMovieDetailsModel().getBuyNowLink());
                return true;

            } else if (menuItem.getItemId() == R.id.menu_add_watchlist) {
                Toast.makeText(this, "Implementing soon...", Toast.LENGTH_SHORT).show();
                return true;

            } else if (menuItem.getItemId() == R.id.menu_view_spoilers) {
                Log.i(TAG, "setOnNavigationItemSelectedListener: ");

                viewPager.setCurrentItem(1, true);
                ViewUtils.hideViews(bnvMovie);
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
                Log.i(TAG, "onPageSelected: ");

                autoSet = true;
                bnvMovie.setSelectedItemId(position == 2 ? R.id.menu_view_spoilers : R.id.menu_buy_now);
                ViewUtils.toggleViewVisibility(position != 2, bnvMovie);
            }
        });

        detailsFragment = new MovieDetailsFragment();
        viewPagerAdapter.addFragment(detailsFragment, getResString(R.string.details));

        spoilersFragment = new MovieSpoilersFragment();
        viewPagerAdapter.addFragment(spoilersFragment, getResString(R.string.spoilers));

        viewPager.setAdapter(viewPagerAdapter);
    }

    public void openLinkInWeb(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    public void gotoCommentsActivity(MovieSpoilerModel movieSpoilerModel) {
        CommentsRepo.initialise(movieSpoilerModel);
        startActivity(new Intent(this, SpoilerCommentsActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        spoilersFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            ViewUtils.showViews(bnvMovie);
            autoSet = true;
            bnvMovie.setSelectedItemId(R.id.menu_buy_now);
            viewPager.setCurrentItem(0);
        } else
            super.onBackPressed();
    }
}
