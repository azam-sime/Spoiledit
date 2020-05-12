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
import com.spoiledit.listeners.OnCommentActionListener;
import com.spoiledit.manager.FileManager;
import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieSpoilerModel;
import com.spoiledit.utils.DateUtils;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.InputUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.RepliesViewModel;
import com.spoiledit.widget.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class CommentsReplyActivity extends RootActivity {
    public static final String TAG = CommentsReplyActivity.class.getCanonicalName();

    private RepliesViewModel repliesViewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SpoilerCommentsAdapter repliesAdapter;

    private ImageView ivReport;
    private EditText etReply;
    private ExpandableTextView extvComment;
    private TextView tvThumbsUp, tvThumbsDown, tvMoreLess;
    private FileManager fileManager;

    private boolean replied = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repliesViewModel = ViewModelProviders.of(this).get(RepliesViewModel.class);
        setContentView(R.layout.activity_comments_reply);
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

        ((TextView) findViewById(R.id.tv_comment)).setText("Reply");

        extvComment = findViewById(R.id.tv_spoiler);
        tvMoreLess = findViewById(R.id.tv_more_less);
        ivReport = findViewById(R.id.iv_report);
        etReply = findViewById(R.id.et_message);
        tvThumbsUp = findViewById(R.id.tv_thumbs_up);
        tvThumbsDown = findViewById(R.id.tv_thumbs_down);
    }

    @Override
    public void initialiseListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        findViewById(R.id.iv_add_files).setOnClickListener(this);
        findViewById(R.id.iv_send).setOnClickListener(this);
        findViewById(R.id.tv_comment).setOnClickListener(this);

        extvComment.setOnClickListener(this);
        tvMoreLess.setOnClickListener(this);
        ivReport.setOnClickListener(this);
        tvThumbsUp.setOnClickListener(this);
        tvThumbsDown.setOnClickListener(this);
    }

    @Override
    public void setData() {
        CommentModel commentModel = repliesViewModel.getCommentModel();

        StringUtils.setText(findViewById(R.id.tv_username), commentModel.getUserName());
        try {
            StringUtils.setText(findViewById(R.id.tv_date),
                    DateUtils.toPattern(commentModel.getCommentDate(),
                            "yyyy-MM-dd HH:mm:ss",
                            "dd MMM, yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
            StringUtils.setText(findViewById(R.id.tv_date), commentModel.getCommentDate());
        }

        extvComment.setText(commentModel.getComment(), TextView.BufferType.SPANNABLE);
        extvComment.setTrim(true);
        tvMoreLess.setText("Show More....");

        setUpThumbsStats();

        Picasso.get()
                .load(commentModel.getUserPhotoUrl())
                .resize(80, 80)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into((RoundedImageView) findViewById(R.id.riv_user));
    }

    @Override
    public void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repliesAdapter = new SpoilerCommentsAdapter(this, false, new OnCommentActionListener() {
            @Override
            public void onReplyComment(int position) {

            }

            @Override
            public void onReport(int position) {

            }

            @Override
            public void onContentToggled(int position) {

            }

            @Override
            public void onThumbsUp(int position) {

            }

            @Override
            public void onThumbsDown(int position) {

            }

            @Override
            public void onUserClicked(int position) {
                Intent intent = new Intent(CommentsReplyActivity.this, ProfileOtherActivity.class);
                intent.putExtra(App.Intent.Extra.USER_SPOILER, repliesAdapter.getItemAt(position).toSpoilerUserModel());
                startActivity(intent);
            }

            @Override
            public void onItemSelected(int lastSelection, int currentSelection) {

            }
        });

        recyclerView.setAdapter(repliesAdapter);
        ViewUtils.addFabOffset(this, recyclerView);

        repliesAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        repliesViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
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
                        repliesViewModel.getMovieSpoilerModel().changeThumbsUp(
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
                        repliesViewModel.getMovieSpoilerModel().changeThumbsUp(
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
            } else if (apiStatusModel.getApi() == Constants.Api.MOVIE_COMMENT_REPLY) {
                if (apiStatusModel.getStatus() == Status.Request.API_SUCCESS)
                    onBackPressed();
            }
        });

        repliesViewModel.getRepliesModelMutable().observe(this, replyModels -> {
            if (replyModels != null)
                repliesAdapter.setItems(replyModels);
        });
    }

    @Override
    public void requestData() {
        showLoader();
        repliesViewModel.requestReplies();
    }

    private void setUpThumbsStats() {
        CommentModel commentModel = repliesViewModel.getCommentModel();

        StringUtils.setText(tvThumbsUp, "(" + commentModel.getThumbsUp() + ")");
        StringUtils.setText(tvThumbsDown, "(" + commentModel.getThumbsDown() + ")");

        tvThumbsUp.setCompoundDrawablesRelativeWithIntrinsicBounds(
                commentModel.getThumbsUp() == 0 ? R.drawable.thumbs_up_none : R.drawable.thumbs_up,
                0, 0, 0
        );

        tvThumbsDown.setCompoundDrawablesRelativeWithIntrinsicBounds(
                commentModel.getThumbsDown() == 0 ? R.drawable.thumbs_down_none : R.drawable.thumbs_down,
                0, 0, 0
        );
    }

    @Override
    public void onRefresh() {
        repliesViewModel.requestReplies();
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.riv_user) {
            Intent intent = new Intent(CommentsReplyActivity.this, ProfileOtherActivity.class);
            intent.putExtra(App.Intent.Extra.USER_SPOILER, repliesViewModel.getMovieSpoilerModel().toSpoilerUserModel());
            startActivity(intent);

        } else if (v.getId() == R.id.tv_comment) {
            if (verifyLoginWithInfo()) {
                etReply.requestFocus();
                InputUtils.showKeyboard(etReply);
            }

        } else if (v.getId() == R.id.tv_spoiler) {
            extvComment.setTrim(!extvComment.isTrim());
            tvMoreLess.setText(extvComment.isTrim() ? "Show More...." : "Show Less....");

        } else if (v.getId() == R.id.tv_more_less) {
            extvComment.setTrim(!extvComment.isTrim());
            tvMoreLess.setText(extvComment.isTrim() ? "Show More...." : "Show Less....");

        } else if (v.getId() == R.id.iv_report) {
            if (verifyLoginWithInfo()) {
                DialogUtils.createReportDialog(this, "Comment Report",
                        (reportType, reportExplanation) -> {
                            repliesViewModel.reportComment(reportType, reportExplanation);
                        });
            }
        } else if (v.getId() == R.id.tv_thumbs_up) {
            if (verifyLoginWithInfo())
                repliesViewModel.thumbsUpComment(true);
        } else if (v.getId() == R.id.tv_thumbs_down) {
            if (verifyLoginWithInfo())
                repliesViewModel.thumbsDownComment(true);
        } else if (v.getId() == R.id.iv_send) {
            if (verifyLoginWithInfo()) {
                if (!StringUtils.isInvalid(etReply)) {
                    replied = true;
                    repliesViewModel.addReply(etReply.getText().toString().trim());
                    etReply.setText("");
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
        }
    }

    @Override
    public void onBackPressed() {
        setResult(replied ? RESULT_OK : RESULT_CANCELED);
        super.onBackPressed();
    }
}
