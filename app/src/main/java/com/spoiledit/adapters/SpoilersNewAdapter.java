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
import com.spoiledit.constants.Urls;
import com.spoiledit.listeners.OnItemSelectionListener;
import com.spoiledit.models.SpoilersNewModel;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpoilersNewAdapter extends RootSelectionAdapter {
    public static final String TAG = SpoilersNewAdapter.class.getCanonicalName();

    private Context context;
    private List<SpoilersNewModel> spoilersNewModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public SpoilersNewAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        spoilersNewModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<SpoilersNewModel> spoilersNewModels) {
        this.spoilersNewModels.clear();
        if (spoilersNewModels != null)
            this.spoilersNewModels.addAll(spoilersNewModels);
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

        spoilersNewModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > spoilersNewModels.size()) {
                lastSelection = -1;
                return;
            }
            if (spoilersNewModels.get(lastSelection).isSelected()) {
                spoilersNewModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public SpoilersNewModel getItemAt(int position) {
        return spoilersNewModels.get(position);
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
        return new SpoilerViewHolder(LayoutInflater.from(context).inflate(R.layout.row_spoilers_new,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpoilerViewHolder) {
            SpoilerViewHolder viewHolder = (SpoilerViewHolder) holder;
            SpoilersNewModel spoilersNewModel = spoilersNewModels.get(position);

            ViewUtils.changeProgressMode(spoilersNewModel.isSelected(), viewHolder.loadingBar);

            viewHolder.tvTitle.setText(spoilersNewModel.getmName());
            viewHolder.tvCategory.setText(spoilersNewModel.getCategory());
            viewHolder.tvMidCredit.setText(spoilersNewModel.getMidCredit());
            viewHolder.tvPostCredit.setText(spoilersNewModel.getStringer());

            Picasso.get()
                    .load(spoilersNewModel.getPosterPath())
                    .resize(80, 132)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return spoilersNewModels.size();
    }

    public class SpoilerViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle, tvCategory, tvMidCredit, tvPostCredit;

        public SpoilerViewHolder(@NonNull View itemView) {
            super(itemView);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            ivPoster = itemView.findViewById(R.id.iv_poster);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvMidCredit = itemView.findViewById(R.id.tv_mid_credit);
            tvPostCredit = itemView.findViewById(R.id.tv_post_credit);

            itemView.findViewById(R.id.btn_goto_spoiler).setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }
    }
}
