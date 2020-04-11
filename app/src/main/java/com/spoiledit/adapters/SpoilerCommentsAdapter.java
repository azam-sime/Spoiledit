package com.spoiledit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;
import com.spoiledit.models.CommentModel;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpoilerCommentsAdapter extends RootSelectionAdapter {
    public static final String TAG = SpoilerCommentsAdapter.class.getCanonicalName();

    private Context context;
    private List<CommentModel> commentModels;
    private OnCommentActionListener onCommentActionListener;
    private int lastSelection;

    public SpoilerCommentsAdapter(Context context, OnCommentActionListener onCommentActionListener) {
        this.context = context;
        commentModels = new ArrayList<>();
        this.onCommentActionListener = onCommentActionListener;
        lastSelection = -1;
    }

    public void setItems(List<CommentModel> commentModels) {
        this.commentModels.clear();
        if (commentModels != null)
            this.commentModels.addAll(commentModels);
        notifyDataSetChanged();
    }

//    @Override
//    public void notifyLoadingMore(boolean isLoadingMore) {
//        if (isLoadingMore) {
//            MovieModel loadingModel = new MovieModel();
//            loadingModel.setViewHolderType(Type.ViewHolder.TYPE_LOADING);
//            movieUpcomingModels.add(loadingModel);
//            notifyItemInserted(movieUpcomingModels.size() - 1);
//        }
//    }

    @Override
    public void notifySelection(int currentSelection) {
        removeLastSelection();

        commentModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > commentModels.size()) {
                lastSelection = -1;
                return;
            }
            if (commentModels.get(lastSelection).isSelected()) {
                commentModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public CommentModel getItemAt(int position) {
        return commentModels.get(position);
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder viewHolder) {
        clearAnimation(viewHolder.itemView);
    }

    private void clearAnimation(View itemView) {
        ViewGroup viewGroup = (ViewGroup) itemView;
        if (viewGroup != null) viewGroup.clearAnimation();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.row_spoiler_comments,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            CommentViewHolder viewHolder = (CommentViewHolder) holder;
            CommentModel commentModel = commentModels.get(position);

            ViewUtils.changeProgressMode(commentModel.isSelected(), viewHolder.loadingBar);

            viewHolder.tvComment.setText(commentModel.getComment());
            viewHolder.tvDate.setText(commentModel.getCommentDate());
            viewHolder.tvThumbsUp.setText("(" + commentModel.getLikes() + ")");
            viewHolder.tvThumbsDown.setText("(" + commentModel.getDislikes() + ")");

            Picasso.get()
                    .load(commentModel.getAvatarUrl())
                    .resize(40, 40)
                    .centerCrop()
                    .error(context.getResources().getDrawable(R.drawable.popcorn))
                    .into(viewHolder.ivUser);
        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUser;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvComment, tvDate, tvReply, tvThumbsUp, tvThumbsDown;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUser = itemView.findViewById(R.id.riv_user);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            tvComment = itemView.findViewById(R.id.tv_comment);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvReply = itemView.findViewById(R.id.tv_reply);
            tvThumbsUp = itemView.findViewById(R.id.tv_thumbs_up);
            tvThumbsDown = itemView.findViewById(R.id.tv_thumbs_down);

            tvReply.setOnClickListener(v -> {
                if (onCommentActionListener != null) {
                    onCommentActionListener.onReplyComment(commentModels.get(getAdapterPosition()));
                }
            });

            tvThumbsUp.setOnClickListener(v -> {
                if (onCommentActionListener != null) {
                    onCommentActionListener.onLikeComment(commentModels.get(getAdapterPosition()));
                }
            });

            tvThumbsDown.setOnClickListener(v -> {
                if (onCommentActionListener != null) {
                    onCommentActionListener.onDislikeComment(commentModels.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnCommentActionListener {
        void onReplyComment(CommentModel commentModel);

        void onLikeComment(CommentModel commentModel);

        void onDislikeComment(CommentModel commentModel);
    }
}
