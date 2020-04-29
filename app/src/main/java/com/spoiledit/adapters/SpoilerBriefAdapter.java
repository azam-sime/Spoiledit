package com.spoiledit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.spoiledit.R;
import com.spoiledit.listeners.OnItemSelectionListener;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpoilerBriefAdapter extends RootSelectionAdapter {
    public static final String TAG = SpoilerBriefAdapter.class.getCanonicalName();

    private Context context;
    private List<SpoilerBriefModel> spoilerBriefModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public SpoilerBriefAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        spoilerBriefModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<SpoilerBriefModel> spoilerBriefModels) {
        this.spoilerBriefModels.clear();
        if (spoilerBriefModels != null)
            this.spoilerBriefModels.addAll(spoilerBriefModels);
        notifyDataSetChanged();
    }

//    @Override
//    public void notifyLoadingMore(boolean isLoadingMore) {
//        if (isLoadingMore) {
//            MovieModel loadingModel = new MovieModel();
//            loadingModel.setViewHolderType(Type.ViewHolder.TYPE_LOADING);
//            moviePopularModels.add(loadingModel);
//            notifyItemInserted(moviePopularModels.size() - 1);
//        }
//    }

    @Override
    public void notifySelection(int currentSelection) {
        removeLastSelection();

        spoilerBriefModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > spoilerBriefModels.size()) {
                lastSelection = -1;
                return;
            }
            if (spoilerBriefModels.get(lastSelection).isSelected()) {
                spoilerBriefModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public SpoilerBriefModel getItemAt(int position) {
        return spoilerBriefModels.get(position);
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
        return new SpoilerBriefViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movie_spoilers,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpoilerBriefViewHolder) {
            SpoilerBriefViewHolder viewHolder = (SpoilerBriefViewHolder) holder;
            SpoilerBriefModel spoilerModel = spoilerBriefModels.get(position);

            ViewUtils.changeProgressMode(spoilerModel.isSelected(), viewHolder.loadingBar);

            viewHolder.tvUserName.setText(spoilerModel.getDisplayName());
            viewHolder.tvSpoiler.setText(spoilerModel.getSpoiler());
            viewHolder.tvDate.setText(spoilerModel.getCratedOn());

            StringUtils.setText(viewHolder.tvThumbsUp, "(" + spoilerModel.getThumbsUp() + ")");
            StringUtils.setText(viewHolder.tvThumbsDown, "(" + spoilerModel.getThumbsDown() + ")");

            viewHolder.tvThumbsUp.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    spoilerModel.getThumbsUpInt() == 0 ? R.drawable.thumbs_up_none : R.drawable.thumbs_up,
                    0, 0, 0
            );

            viewHolder.tvThumbsDown.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    spoilerModel.getThumbsDownInt() == 0 ? R.drawable.thumbs_down_none : R.drawable.thumbs_down,
                    0, 0, 0
            );

            viewHolder.itemView.setBackgroundColor(spoilerModel.isDarkBackground()
                    ? Color.parseColor("#F3F3F3") : Color.parseColor("#FFFFFF"));

            Picasso.get()
                    .load(spoilerModel.getAvatarUrl())
                    .resize(60, 60)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.rivUser);
        }
    }

    @Override
    public int getItemCount() {
        return spoilerBriefModels.size();
    }

    public class SpoilerBriefViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView rivUser;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvUserName, tvSpoiler, tvDate, tvThumbsUp, tvThumbsDown;

        public SpoilerBriefViewHolder(@NonNull View itemView) {
            super(itemView);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            rivUser = itemView.findViewById(R.id.riv_user);

            tvUserName = itemView.findViewById(R.id.tv_username);
            tvSpoiler = itemView.findViewById(R.id.tv_spoiler);
            tvDate = itemView.findViewById(R.id.tv_date);

            tvThumbsUp = itemView.findViewById(R.id.tv_thumbs_up);
            tvThumbsDown = itemView.findViewById(R.id.tv_thumbs_down);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }
    }
}
