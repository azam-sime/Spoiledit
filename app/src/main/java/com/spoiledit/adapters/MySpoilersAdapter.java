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
import com.spoiledit.listeners.OnSpoilerActionClickListener;
import com.spoiledit.models.MySpoilerModel;
import com.spoiledit.utils.DateUtils;
import com.spoiledit.utils.StringUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.widget.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MySpoilersAdapter extends RootSelectionAdapter {
    public static final String TAG = MySpoilersAdapter.class.getCanonicalName();

    private Context context;
    private List<MySpoilerModel> mySpoilerModels;
    private OnSpoilerActionClickListener onSpoilerActionClickListener;
    private int lastSelection;

    public MySpoilersAdapter(Context context, OnSpoilerActionClickListener onSpoilerActionClickListener) {
        this.context = context;
        mySpoilerModels = new ArrayList<>();
        this.onSpoilerActionClickListener = onSpoilerActionClickListener;
        lastSelection = -1;
    }

    public void setItems(List<MySpoilerModel> mySpoilerModels) {
        this.mySpoilerModels.clear();
        if (mySpoilerModels != null)
            this.mySpoilerModels.addAll(mySpoilerModels);
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

        mySpoilerModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > mySpoilerModels.size()) {
                lastSelection = -1;
                return;
            }
            if (mySpoilerModels.get(lastSelection).isSelected()) {
                mySpoilerModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public MySpoilerModel getItemAt(int position) {
        return mySpoilerModels.get(position);
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
        return new MySpoilerViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movie_spoilers,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MySpoilerViewHolder) {
            MySpoilerViewHolder viewHolder = (MySpoilerViewHolder) holder;
            MySpoilerModel spoilerModel = mySpoilerModels.get(position);

            ViewUtils.changeProgressMode(spoilerModel.isSelected(), viewHolder.loadingBar);

            viewHolder.tvUserName.setText(spoilerModel.getUserName());
            viewHolder.etvSpoiler.setText(spoilerModel.getSpoiler(), TextView.BufferType.SPANNABLE);
            viewHolder.etvSpoiler.setTrim(spoilerModel.isTrimmed());
            viewHolder.tvMoreLess.setText(spoilerModel.isTrimmed() ? "Show More...." : "Show Less....");
            try {
                viewHolder.tvDate.setText(
                        DateUtils.toPattern(spoilerModel.getCratedOn(),
                                "yyyy-MM-dd HH:mm:ss", "dd MMM, yyyy")
                );
            } catch (ParseException e) {
                e.printStackTrace();
                viewHolder.tvDate.setText(spoilerModel.getCratedOn());
            }

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
                    .load(spoilerModel.getUserPhotoUrl())
//                    .resize(60, 60)
//                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.rivUser);
        }
    }

    @Override
    public int getItemCount() {
        return mySpoilerModels.size();
    }

    public class MySpoilerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView rivUser;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvUserName, tvDate, tvThumbsUp, tvThumbsDown, tvMoreLess;
        private ExpandableTextView etvSpoiler;

        public MySpoilerViewHolder(@NonNull View itemView) {
            super(itemView);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            rivUser = itemView.findViewById(R.id.riv_user);

            tvUserName = itemView.findViewById(R.id.tv_username);
            etvSpoiler = itemView.findViewById(R.id.tv_spoiler);
            tvDate = itemView.findViewById(R.id.tv_date);

            tvThumbsUp = itemView.findViewById(R.id.tv_thumbs_up);
            tvThumbsDown = itemView.findViewById(R.id.tv_thumbs_down);

            tvMoreLess = itemView.findViewById(R.id.tv_more_less);

            tvMoreLess.setOnClickListener(v -> {
                if (onSpoilerActionClickListener != null)
                    onSpoilerActionClickListener.onContentToggled(getAdapterPosition());
            });

            tvThumbsUp.setOnClickListener(v -> {
                if (onSpoilerActionClickListener != null)
                    onSpoilerActionClickListener.onThumbsUp(getAdapterPosition());
            });

            tvThumbsDown.setOnClickListener(v -> {
                if (onSpoilerActionClickListener != null)
                    onSpoilerActionClickListener.onThumbsDown(getAdapterPosition());
            });

            itemView.setOnClickListener(v -> {
                if (onSpoilerActionClickListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onSpoilerActionClickListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }
    }
}
