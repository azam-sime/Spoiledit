package com.spoiledit.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spoiledit.R;
import com.spoiledit.activities.DetailsMovieActivity;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.constants.Urls;
import com.spoiledit.models.MovieDetailsModel;
import com.spoiledit.viewmodels.DetailsMovieViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends RootFragment {
    public static final String TAG = MovieDetailsFragment.class.getCanonicalName();

    private DetailsMovieViewModel detailsMovieViewModel;
    private ImageView ivPoster,
            ivRating1, ivRating2, ivRating3, ivRating4, ivRating5, ivRating6, ivRating7, ivRating8, ivRating9, ivRating10;
    private TextView tvMidCreditScene, tvPostCreditScene,
            tvTitle,
            tvRating, tvRatingValue,
            tvDurationGenres,
            tvDirectors, tvCasts,
            tvOverview;
    private MaterialButton btnSeeSpoiler;
    private FloatingActionButton fabPlay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsMovieViewModel = ViewModelProviders.of(getActivity()).get(DetailsMovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void initUi(View view) {
        ivPoster = view.findViewById(R.id.iv_poster);

        ivRating1 = view.findViewById(R.id.iv_rate_1);
        ivRating2 = view.findViewById(R.id.iv_rate_2);
        ivRating3 = view.findViewById(R.id.iv_rate_3);
        ivRating4 = view.findViewById(R.id.iv_rate_4);
        ivRating5 = view.findViewById(R.id.iv_rate_5);
        ivRating6 = view.findViewById(R.id.iv_rate_6);
        ivRating7 = view.findViewById(R.id.iv_rate_7);
        ivRating8 = view.findViewById(R.id.iv_rate_8);
        ivRating9 = view.findViewById(R.id.iv_rate_9);
        ivRating10 = view.findViewById(R.id.iv_rate_10);

        tvMidCreditScene = view.findViewById(R.id.tv_mcs);
        tvPostCreditScene = view.findViewById(R.id.tv_pcs);
        tvTitle = view.findViewById(R.id.tv_title);
        tvRating = view.findViewById(R.id.tv_rating);
        tvRatingValue = view.findViewById(R.id.tv_rating_value);
        tvDurationGenres = view.findViewById(R.id.tv_genres_duration);
        tvDirectors = view.findViewById(R.id.tv_directors);
        tvCasts = view.findViewById(R.id.tv_stars);
        tvOverview = view.findViewById(R.id.tv_description);

        btnSeeSpoiler = view.findViewById(R.id.btn_see_spoiler);
        fabPlay = view.findViewById(R.id.fab_play);
    }

    @Override
    public void initialiseListener(View view) {
        btnSeeSpoiler.setOnClickListener(this);
        fabPlay.setOnClickListener(this);
    }

    @Override
    public void addObservers() {
        detailsMovieViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MY_WATCHLIST_ADD) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT)
                    showLoader(apiStatusModel.getMessage());
                else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                        showSuccess(true, apiStatusModel.getMessage());
                    else
                        showFailure(false, apiStatusModel.getMessage());
                }
            }
        });
    }

    @Override
    public void setData(View view) {
        MovieDetailsModel detailsModel = detailsMovieViewModel.getMovieDetailsModel();

        Picasso.get()
                .load(Urls.MOVIE_IMAGES.getUrl() + detailsModel.getPosterPath())
//                .resize(ivPoster.getWidth(), 400)
//                .centerCrop()
//                .onlyScaleDown()
                .placeholder(R.drawable.ic_placeholder)
                .fit()
//                .error(getResources().getDrawable(R.drawable.popcorn))
                .into(ivPoster);

        tvMidCreditScene.setText(detailsModel.getMidCreditScene());
        tvPostCreditScene.setText(detailsModel.getPostCreditScene());

        tvTitle.setText(detailsModel.getTitle());

        ImageView[] imageViews = new ImageView[]{ivRating1, ivRating2, ivRating3, ivRating4, ivRating5,
                ivRating6, ivRating7, ivRating8, ivRating9, ivRating10};
        for (int i = 0; i < imageViews.length; i++) {
            if ((i + 1) <= detailsModel.getVoteAverage())
                imageViews[i].setImageResource(R.drawable.star_yellow);
            else
                imageViews[i].setImageResource(R.drawable.star_grey);
        }

        tvRating.setText(detailsModel.getVoteAverage() + "/10");
        tvDurationGenres.setText(detailsModel.getRunTime() + " | " + detailsModel.getGenresStr());
        tvDirectors.setText(detailsModel.getDirectorsStr());
        tvCasts.setText(detailsModel.getCastsStr());
        tvOverview.setText(detailsModel.getOverview());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_see_spoiler) {
            ((DetailsMovieActivity) getActivity()).scrollToViewSpoilers();

        } else if (v.getId() == R.id.fab_play) {
            watchYoutubeVideo(detailsMovieViewModel.getMovieDetailsModel().getYoutubeId());
        } else
            super.onClick(v);
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
