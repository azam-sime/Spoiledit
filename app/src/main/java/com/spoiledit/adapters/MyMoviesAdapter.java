package com.spoiledit.adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.spoiledit.listeners.OnItemDeletionListener;
import com.spoiledit.listeners.OnItemSelectionListener;
import com.spoiledit.models.MyMovieModel;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyMoviesAdapter extends RootSelectionAdapter {
    public static final String TAG = MyMoviesAdapter.class.getCanonicalName();

    private Context context;
    private List<MyMovieModel> myMovieModels;
    private OnItemSelectionListener onItemSelectionListener;
    private OnItemDeletionListener onItemDeletionListener;
    private int lastSelection;

    public MyMoviesAdapter(Context context, OnItemSelectionListener onItemSelectionListener, OnItemDeletionListener onItemDeletionListener) {
        this.context = context;
        myMovieModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        this.onItemDeletionListener = onItemDeletionListener;
        lastSelection = -1;
    }

    public void setItems(List<MyMovieModel> myMovieModels) {
        this.myMovieModels.clear();
        if (myMovieModels != null)
            this.myMovieModels.addAll(myMovieModels);
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

        myMovieModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > myMovieModels.size()) {
                lastSelection = -1;
                return;
            }
            if (myMovieModels.get(lastSelection).isSelected()) {
                myMovieModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public MyMovieModel getItemAt(int position) {
        return myMovieModels.get(position);
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
        return new PopularMovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_my_movies,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularMovieViewHolder) {
            PopularMovieViewHolder viewHolder = (PopularMovieViewHolder) holder;
            MyMovieModel movieModel = myMovieModels.get(position);

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
            viewHolder.itemView.setBackgroundColor(movieModel.isDarkBackground()
                    ? Color.parseColor("#F3F3F3") : Color.parseColor("#FFFFFF"));

            Picasso.get()
                    .load(Urls.MOVIE_IMAGES.getUrl() + movieModel.getPosterPath())
                    .resize(80, 132)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
//                    .error(context.getResources().getDrawable(R.drawable.popcorn))
                    .into(viewHolder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return myMovieModels.size();
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
