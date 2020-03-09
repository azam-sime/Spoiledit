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
import com.spoiledit.listeners.OnItemSelectionListener;
import com.spoiledit.models.MovieModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesRecentAdapter extends RootSelectionAdapter {
    public static final String TAG = MoviesRecentAdapter.class.getCanonicalName();

    private Context context;
    private List<MovieRecentModel> movieRecentModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public MoviesRecentAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        movieRecentModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<MovieRecentModel> movieRecentModels) {
        this.movieRecentModels.clear();
        if (movieRecentModels != null)
            this.movieRecentModels.addAll(movieRecentModels);
        notifyDataSetChanged();
    }

//    @Override
//    public void notifyLoadingMore(boolean isLoadingMore) {
//        if (isLoadingMore) {
//            MovieModel loadingModel = new MovieModel();
//            loadingModel.setViewHolderType(Type.ViewHolder.TYPE_LOADING);
//            movieRecentModels.add(loadingModel);
//            notifyItemInserted(movieRecentModels.size() - 1);
//        }
//    }

    @Override
    public void notifySelection(int currentSelection) {
        removeLastSelection();

        movieRecentModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > movieRecentModels.size()) {
                lastSelection = -1;
                return;
            }
            if (movieRecentModels.get(lastSelection).isSelected()) {
                movieRecentModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public MovieModel getItemAt(int position) {
        return movieRecentModels.get(position);
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
        return new RecentMovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movies_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecentMovieViewHolder) {
            RecentMovieViewHolder viewHolder = (RecentMovieViewHolder) holder;
            MovieModel movieModel = movieRecentModels.get(position);

            ViewUtils.changeProgressMode(movieModel.isSelected(), viewHolder.loadingBar);

            ImageView[] rateBarImageViews = viewHolder.getRateBarImageViews();
            for (int i = 0; i < rateBarImageViews.length; i++) {
                if ((i + 1) <= movieModel.getVoteAverage() / 2)
                    rateBarImageViews[i].setImageResource(R.drawable.star_yellow);
                else
                    rateBarImageViews[i].setImageResource(R.drawable.star_grey);
            }

            viewHolder.tvTitle.setText(movieModel.getTitle());
            viewHolder.tvOverview.setText(movieModel.getOverview());

            Picasso.get().load(movieModel.getPosterPath()).into(viewHolder.ivPoster,
                    new Callback() {
                        @Override
                        public void onSuccess() {
                            LogUtils.logResponse("Image preview successful.");
                        }

                        @Override
                        public void onError(Exception e) {
                            LogUtils.logError(e.getMessage());
                            viewHolder.ivPoster.setImageResource(R.drawable.popcorn);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return movieRecentModels.size();
    }

    public class RecentMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle, tvOverview, tvViewDetails;

        public RecentMovieViewHolder(@NonNull View itemView) {
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
            tvViewDetails = itemView.findViewById(R.id.tv_view_details);

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
