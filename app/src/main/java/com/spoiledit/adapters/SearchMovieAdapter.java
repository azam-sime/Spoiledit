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
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.SearchMovieModel;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieAdapter extends RootSelectionAdapter {
    public static final String TAG = SearchMovieAdapter.class.getCanonicalName();

    private Context context;
    private List<SearchMovieModel> searchMovieModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public SearchMovieAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        searchMovieModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<SearchMovieModel> searchMovieModels) {
        this.searchMovieModels.clear();
        if (searchMovieModels != null)
            this.searchMovieModels.addAll(searchMovieModels);
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

        searchMovieModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > searchMovieModels.size()) {
                lastSelection = -1;
                return;
            }
            if (searchMovieModels.get(lastSelection).isSelected()) {
                searchMovieModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public SearchMovieModel getItemAt(int position) {
        return searchMovieModels.get(position);
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
        return new PopularMovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movies,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularMovieViewHolder) {
            PopularMovieViewHolder viewHolder = (PopularMovieViewHolder) holder;
            SearchMovieModel searchMovieModel = searchMovieModels.get(position);

            ViewUtils.changeProgressMode(searchMovieModel.isSelected(), viewHolder.loadingBar);

            ImageView[] rateBarImageViews = viewHolder.getRateBarImageViews();
            for (int i = 0; i < rateBarImageViews.length; i++) {
                if ((i + 1) <= searchMovieModel.getVoteAverage() / 2)
                    rateBarImageViews[i].setImageResource(R.drawable.star_yellow);
                else
                    rateBarImageViews[i].setImageResource(R.drawable.star_grey);
            }

            viewHolder.tvTitle.setText(searchMovieModel.getTitle());
            viewHolder.tvOverview.setText(searchMovieModel.getOverview());

            Picasso.get()
                    .load(Urls.MOVIE_IMAGES.getUrl() + searchMovieModel.getPosterPath())
                    .resize(80, 132)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
//                    .error(context.getResources().getDrawable(R.drawable.popcorn))
                    .into(viewHolder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return searchMovieModels.size();
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle, tvOverview;

        public PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_poster);

            ivRating1 = itemView.findViewById(R.id.iv_rate_1);
            ivRating2 = itemView.findViewById(R.id.iv_rate_2);
            ivRating3 = itemView.findViewById(R.id.iv_rate_3);
            ivRating4 = itemView.findViewById(R.id.iv_rate_4);
            ivRating5 = itemView.findViewById(R.id.iv_rate_5);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }

        public ImageView[] getRateBarImageViews() {
            return new ImageView[]{ivRating1, ivRating2, ivRating3, ivRating4, ivRating5};
        }
    }
}
