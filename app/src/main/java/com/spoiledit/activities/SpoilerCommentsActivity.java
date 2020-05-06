package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.spoiledit.R;
import com.spoiledit.adapters.SpoilerCommentsAdapter;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.File;
import com.spoiledit.constants.Status;
import com.spoiledit.manager.FileManager;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.utils.DateUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.CommentsViewModel;
import com.spoiledit.widget.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class SpoilerCommentsActivity extends RootActivity {
    public static final String TAG = SpoilerCommentsActivity.class.getCanonicalName();

    private CommentsViewModel commentsViewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SpoilerCommentsAdapter commentsAdapter;

    private ImageView ivReport;
    private EditText etComment;
    private ExpandableTextView etvSpoiler;
    private TextView tvThumbsUp, tvThumbsDown, tvMoreLess;
    private FileManager fileManager;

//    private boolean reply = false;
//    private int commentId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        setContentView(R.layout.activity_spoiler_comments);
    }

    @Override
    public void setUpToolBar() {
        setupBackIconOnly();
    }

    @Override
    public void initUi() {
        hideLoader();
        fileManager = new FileManager(this, File.From.SPOILERS);

        swipeRefreshLayout = findViewById(R.id.srl_comments);

        etvSpoiler = findViewById(R.id.tv_spoiler);
        tvMoreLess = findViewById(R.id.tv_more_less);
        ivReport = findViewById(R.id.iv_report);
        etComment = findViewById(R.id.et_message);
        tvThumbsUp = findViewById(R.id.tv_thumbs_up);
        tvThumbsDown = findViewById(R.id.tv_thumbs_down);
    }

    @Override
    public void initialiseListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        findViewById(R.id.iv_add_files).setOnClickListener(this);
        findViewById(R.id.iv_send).setOnClickListener(this);
        findViewById(R.id.tv_comment).setOnClickListener(this);

        etvSpoiler.setOnClickListener(this);
        tvMoreLess.setOnClickListener(this);
        ivReport.setOnClickListener(this);
        tvThumbsUp.setOnClickListener(this);
        tvThumbsDown.setOnClickListener(this);
    }

    @Override
    public void setData() {
        MovieSpoilerModel spoilerModel = commentsViewModel.getMovieSpoilerModel();

        StringUtils.setText(findViewById(R.id.tv_username), spoilerModel.getDisplayName());
        try {
            StringUtils.setText(findViewById(R.id.tv_date),
                    DateUtils.toPattern(spoilerModel.getCratedOn(),
                            "yyyy-MM-dd HH:mm:ss",
                            "dd MMM, yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
            StringUtils.setText(findViewById(R.id.tv_date), spoilerModel.getCratedOn());
        }

        etvSpoiler.setText(spoilerModel.getSpoiler(), TextView.BufferType.SPANNABLE);
        etvSpoiler.setTrim(true);
        tvMoreLess.setText("Show More....");

        StringUtils.setText(tvThumbsUp, "(" + spoilerModel.getThumbsUp() + ")");
        StringUtils.setText(tvThumbsDown, "(" + spoilerModel.getThumbsDown() + ")");

        tvThumbsUp.setCompoundDrawablesRelativeWithIntrinsicBounds(
                spoilerModel.getThumbsUpInt() == 0 ? R.drawable.thumbs_up_none : R.drawable.thumbs_up,
                0, 0, 0
        );

        tvThumbsDown.setCompoundDrawablesRelativeWithIntrinsicBounds(
                spoilerModel.getThumbsDownInt() == 0 ? R.drawable.thumbs_down_none : R.drawable.thumbs_down,
                0, 0, 0
        );

        Picasso.get()
                .load(spoilerModel.getAvatarUrl())
                .resize(80, 80)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into((RoundedImageView) findViewById(R.id.riv_user));
    }

    @Override
    public void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentsAdapter = new SpoilerCommentsAdapter(this, new SpoilerCommentsAdapter.OnCommentActionListener() {
            @Override
            public void onReplyComment(int position) {
                Toast.makeText(SpoilerCommentsActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLikeComment(int position) {
                Toast.makeText(SpoilerCommentsActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDislikeComment(int position) {
                Toast.makeText(SpoilerCommentsActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReport(int position) {
                Toast.makeText(SpoilerCommentsActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(commentsAdapter);
        ViewUtils.addFabOffset(this, recyclerView);

        commentsAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        commentsViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENTS) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(!swipeRefreshLayout.isRefreshing(), apiStatusModel.getMessage());

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        showFailure(false, apiStatusModel.getMessage());
                }
            } else if (apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_ADD
                    || apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_EDIT
                    || apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_REPLY
                    || apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_DELETE) {
                if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                    requestData();
            }
        });

        commentsViewModel.getCommentsModelMutable().observe(this, commentModels -> {
            if (commentModels != null)
                commentsAdapter.setItems(commentModels);
        });
    }

    @Override
    public void requestData() {
        showLoader();
        commentsViewModel.requestComments();
    }

    @Override
    public void onRefresh() {
        commentsViewModel.requestComments();
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_add_files)
            showInterrupt("Coming Soon", true);
        else if (v.getId() == R.id.tv_comment) {
            etComment.requestFocus();
            InputUtils.showKeyboard(etComment);
        } else if (v.getId() == R.id.tv_spoiler) {
            etvSpoiler.setTrim(!etvSpoiler.isTrim());
            tvMoreLess.setText(etvSpoiler.isTrim() ? "Show More...." : "Show Less....");
        } else if (v.getId() == R.id.tv_more_less) {
            etvSpoiler.setTrim(!etvSpoiler.isTrim());
            tvMoreLess.setText(etvSpoiler.isTrim() ? "Show More...." : "Show Less....");
        } else if (v.getId() == R.id.iv_report)
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        else if (v.getId() == R.id.tv_thumbs_up)
            showInterrupt("Coming Soon", true);
        else if (v.getId() == R.id.tv_thumbs_down)
            showInterrupt("Coming Soon", true);
        else if (v.getId() == R.id.iv_send) {
            if (!StringUtils.isInvalid(etComment)) {
                    commentsViewModel.addComment(etComment.getText().toString().trim());
                etComment.setText("");
            }
        } else
            super.onClick(v);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == App.Usage.BROWSER) {
            fileManager.gotoFileBrowser();
        } else if (requestCode == App.Usage.GALLERY) {
            fileManager.gotoGallery();
        } else if (requestCode == App.Usage.CAMERA) {
            fileManager.gotoCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.SystemIntent.GALLERY) {
            if (resultCode == RESULT_OK) {
//                updateProfilePic(fileManager.getFileDataFromIntent(data));
            }
        } else if (requestCode == App.SystemIntent.BROWSER) {
            if (resultCode == RESULT_OK) {
//                updateProfilePic(fileManager.getFileDataFromIntent(data));
            }
        } else if (requestCode == App.SystemIntent.CAMERA) {
            if (resultCode == RESULT_OK) {
//                updateProfilePic(fileManager.getImagePathForCamera());
            }
        }
    }
}
