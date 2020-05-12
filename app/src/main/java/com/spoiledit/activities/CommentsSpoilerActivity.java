package com.spoiledit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.spoiledit.listeners.OnCommentActionListener;
import com.spoiledit.manager.FileManager;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.repos.RepliesRepo;
import com.spoiledit.utils.DateUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.CommentsViewModel;
import com.spoiledit.widget.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class CommentsSpoilerActivity extends RootActivity {
    public static final String TAG = CommentsSpoilerActivity.class.getCanonicalName();

    private CommentsViewModel commentsViewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SpoilerCommentsAdapter commentsAdapter;

    private RoundedImageView rivUser;
    private ImageView ivReport;
    private EditText etComment;
    private ExpandableTextView etvSpoiler;
    private TextView tvThumbsUp, tvThumbsDown, tvMoreLess;
    private FileManager fileManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        setContentView(R.layout.activity_comments_spoiler);
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

        rivUser = findViewById(R.id.riv_user);
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

        rivUser.setOnClickListener(this);
        findViewById(R.id.iv_report).setOnClickListener(this);
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

        StringUtils.setText(findViewById(R.id.tv_username), spoilerModel.getUserName());
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

        setUpThumbsStats();

        Picasso.get()
                .load(spoilerModel.getUserPhotoUrl())
                .resize(80, 80)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into((RoundedImageView) findViewById(R.id.riv_user));
    }

    @Override
    public void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentsAdapter = new SpoilerCommentsAdapter(this, true, new OnCommentActionListener() {
            @Override
            public void onReplyComment(int position) {
                if (verifyLoginWithInfo()) {
                    RepliesRepo.initialise(commentsViewModel.getMovieSpoilerModel(), commentsAdapter.getItemAt(position));
                    startActivityForResult(new Intent(CommentsSpoilerActivity.this,
                            CommentsReplyActivity.class), App.Intent.Request.IS_COMMENT_REPLIED);
                }
            }

            @Override
            public void onReport(int position) {
                if (verifyLoginWithInfo()) {
                    DialogUtils.createReportDialog(CommentsSpoilerActivity.this, "Comment Report",
                            (reportType, reportExplanation) -> {
                                commentsViewModel.reportComment(commentsAdapter.getItemAt(position).getId(),
                                        reportType, reportExplanation);
                            });
                }
            }

            @Override
            public void onContentToggled(int position) {

            }

            @Override
            public void onThumbsUp(int position) {
                if (verifyLoginWithInfo())
                    commentsViewModel.thumbsUpComment(commentsAdapter.getItemAt(position).getId(), true);
            }

            @Override
            public void onThumbsDown(int position) {
                if (verifyLoginWithInfo())
                    commentsViewModel.thumbsDownComment(commentsAdapter.getItemAt(position).getId(), true);
            }

            @Override
            public void onUserClicked(int position) {
                Intent intent = new Intent(CommentsSpoilerActivity.this, ProfileOtherActivity.class);
                intent.putExtra(App.Intent.Extra.USER_SPOILER, commentsAdapter.getItemAt(position).toSpoilerUserModel());
                startActivity(intent);
            }

            @Override
            public void onItemSelected(int lastSelection, int currentSelection) {

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
            } else if (apiStatusModel.getApi() == Constants.Api.SPOILERS_REPORT_ADD) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    DialogUtils.showToast(this, apiStatusModel.getMessage());
                }
            } else if (apiStatusModel.getApi() == Constants.Api.THUMBS_UP_SPOILER_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_UP_SPOILER_REMOVE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(this, apiStatusModel.getMessage());
                    else {
                        commentsViewModel.getMovieSpoilerModel().changeThumbsUp(
                                apiStatusModel.getApi() == Constants.Api.THUMBS_UP_SPOILER_ADD
                        );
                        setUpThumbsStats();
                    }
                }
            } else if (apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_SPOILER_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_SPOILER_REMOVE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(this, apiStatusModel.getMessage());
                    else {
                        commentsViewModel.getMovieSpoilerModel().changeThumbsUp(
                                apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_SPOILER_ADD
                        );
                        setUpThumbsStats();
                    }
                }
            } else if (apiStatusModel.getApi() == Constants.Api.THUMBS_UP_COMMENT_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_UP_COMMENT_REMOVE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(this, apiStatusModel.getMessage());
                    else
                        requestData();
                }
            } else if (apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_COMMENT_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_COMMENT_REMOVE) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(this, apiStatusModel.getMessage());
                    else
                        requestData();
                }
            } else if (apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_ADD
                    || apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_EDIT
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

    private void setUpThumbsStats() {
        MovieSpoilerModel spoilerModel = commentsViewModel.getMovieSpoilerModel();

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
        if (v.getId() == R.id.riv_user) {
            Intent intent = new Intent(CommentsSpoilerActivity.this, ProfileOtherActivity.class);
            intent.putExtra(App.Intent.Extra.USER_SPOILER, commentsViewModel.getMovieSpoilerModel().toSpoilerUserModel());
            startActivity(intent);

        } else if (v.getId() == R.id.tv_comment) {
            if (verifyLoginWithInfo()) {
                etComment.requestFocus();
                InputUtils.showKeyboard(etComment);
            }

        } else if (v.getId() == R.id.tv_spoiler) {
            etvSpoiler.setTrim(!etvSpoiler.isTrim());
            tvMoreLess.setText(etvSpoiler.isTrim() ? "Show More...." : "Show Less....");

        } else if (v.getId() == R.id.tv_more_less) {
            etvSpoiler.setTrim(!etvSpoiler.isTrim());
            tvMoreLess.setText(etvSpoiler.isTrim() ? "Show More...." : "Show Less....");

        } else if (v.getId() == R.id.iv_report) {
            if (verifyLoginWithInfo()) {
                DialogUtils.createReportDialog(this, "Spoiler Report",
                        (reportType, reportExplanation) -> {
                            commentsViewModel.reportSpoiler(reportType, reportExplanation);
                        });
            }

        } else if (v.getId() == R.id.tv_thumbs_up) {
            if (verifyLoginWithInfo()) {
                commentsViewModel.thumbsUpSpoiler(true);
            }
        } else if (v.getId() == R.id.tv_thumbs_down) {
            if (verifyLoginWithInfo()) {
                commentsViewModel.thumbsDownSpoiler(true);
            }
        } else if (v.getId() == R.id.iv_send) {
            if (verifyLoginWithInfo()) {
                if (!StringUtils.isInvalid(etComment)) {
                    commentsViewModel.addComment(etComment.getText().toString().trim());
                    etComment.setText("");
                }
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
        } else if (requestCode == App.Intent.Request.IS_COMMENT_REPLIED) {
            if (resultCode == RESULT_OK) {
                requestData();
            }
        }
    }
}
