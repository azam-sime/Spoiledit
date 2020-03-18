package com.spoiledit.adapters;

import android.content.Context;
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
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class SpoilerFullAdapter extends RootSelectionAdapter {
    public static final String TAG = SpoilerFullAdapter.class.getCanonicalName();

    private Context context;
    private List<SpoilerFullModel> spoilerFullModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public SpoilerFullAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        spoilerFullModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<SpoilerFullModel> spoilerFullModels) {
        this.spoilerFullModels.clear();
        if (spoilerFullModels != null)
            this.spoilerFullModels.addAll(spoilerFullModels);
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

        spoilerFullModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > spoilerFullModels.size()) {
                lastSelection = -1;
                return;
            }
            if (spoilerFullModels.get(lastSelection).isSelected()) {
                spoilerFullModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public SpoilerFullModel getItemAt(int position) {
        return spoilerFullModels.get(position);
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
        return new SpoilerFullViewHolder(LayoutInflater.from(context).inflate(R.layout.row_spoilers,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpoilerFullViewHolder) {
            SpoilerFullViewHolder viewHolder = (SpoilerFullViewHolder) holder;
            SpoilerFullModel spoilerModel = spoilerFullModels.get(position);

            ViewUtils.changeProgressMode(spoilerModel.isSelected(), viewHolder.loadingBar);

//            Picasso.get().load(spoilerModel.getPosterPath()).into(viewHolder.ivPoster,
//                    new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            LogUtils.logResponse("Image preview successful.");
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            LogUtils.logError(e.getMessage());
//                            viewHolder.ivPoster.setImageResource(R.drawable.popcorn);
//                        }
//                    });
        }
    }

    @Override
    public int getItemCount() {
        return spoilerFullModels.size();
    }

    public class SpoilerFullViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView rivUser;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvUserName, tvSpoiler, tvDate, tvThumbsUp, tvThumbsDown;

        public SpoilerFullViewHolder(@NonNull View itemView) {
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