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
import com.spoiledit.models.MySpoilerModel;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MySpoilersAdapter extends RootSelectionAdapter {
    public static final String TAG = MySpoilersAdapter.class.getCanonicalName();

    private Context context;
    private List<MySpoilerModel> mySpoilerModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public MySpoilersAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        mySpoilerModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
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
            MySpoilerModel mySpoilerModel = mySpoilerModels.get(position);

            ViewUtils.changeProgressMode(mySpoilerModel.isSelected(), viewHolder.loadingBar);

            viewHolder.tvUserName.setText(mySpoilerModel.getDisplayName());
            viewHolder.tvSpoiler.setText(mySpoilerModel.getSpoiler());
            viewHolder.tvDate.setText(mySpoilerModel.getCratedOn());
            viewHolder.tvThumbsUp.setText("(" + mySpoilerModel.getThumbsUp() + ")");
            viewHolder.tvThumbsDown.setText("(" + mySpoilerModel.getThumbsDown() + ")");
            viewHolder.itemView.setBackgroundColor(mySpoilerModel.isDarkBackground()
                    ? Color.parseColor("#F3F3F3") : Color.parseColor("#FFFFFF"));

            Picasso.get()
                    .load(mySpoilerModel.getAvatarUrl())
                    .resize(60, 60)
                    .centerCrop()
                    .error(context.getResources().getDrawable(R.drawable.popcorn))
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
        private TextView tvUserName, tvSpoiler, tvDate, tvThumbsUp, tvThumbsDown;

        public MySpoilerViewHolder(@NonNull View itemView) {
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
