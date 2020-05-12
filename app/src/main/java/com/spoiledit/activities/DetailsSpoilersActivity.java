package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.spoiledit.R;
import com.spoiledit.fragments.MovieSpoilersFragment;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.repos.CommentsRepo;
import com.spoiledit.viewmodels.DetailsSpoilersViewModel;

public class DetailsSpoilersActivity extends RootActivity {
    public static final String TAG = DetailsSpoilersActivity.class.getCanonicalName();

    private DetailsSpoilersViewModel detailsSpoilersViewModel;
    private MovieSpoilersFragment movieSpoilersFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsSpoilersViewModel = ViewModelProviders.of(this).get(DetailsSpoilersViewModel.class);
        setContentView(R.layout.activity_spoilers_details);
    }

    @Override
    public void setUpToolBar() {
        setupBackIconOnly();
    }

    @Override
    public void initUi() {
        movieSpoilersFragment = (MovieSpoilersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_movie_spoilers);
    }

    @Override
    public void initialiseListener() {

    }

    public void gotoCommentsActivity(MovieSpoilerModel movieSpoilerModel) {
        CommentsRepo.initialise(movieSpoilerModel);
        startActivity(new Intent(this, CommentsSpoilerActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        movieSpoilersFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
