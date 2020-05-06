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
import com.spoiledit.constants.Type;
import com.spoiledit.constants.Urls;
import com.spoiledit.listeners.OnItemSelectionListener;
import com.spoiledit.models.SearchModel;
import com.spoiledit.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RootSelectionAdapter {
    public static final String TAG = SearchAdapter.class.getCanonicalName();

    private Context context;
    private List<SearchModel> searchModels;
    private OnItemSelectionListener onItemSelectionListener;
    private int lastSelection;

    public SearchAdapter(Context context, OnItemSelectionListener onItemSelectionListener) {
        this.context = context;
        searchModels = new ArrayList<>();
        this.onItemSelectionListener = onItemSelectionListener;
        lastSelection = -1;
    }

    public void setItems(List<SearchModel> searchModels) {
        this.searchModels.clear();
        if (searchModels != null)
            this.searchModels.addAll(searchModels);
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

        searchModels.get(currentSelection).setSelected(true);
        notifyItemChanged(currentSelection);
        lastSelection = currentSelection;
    }

    @Override
    public void removeLastSelection() {
        if (lastSelection != -1) {
            if (lastSelection > searchModels.size()) {
                lastSelection = -1;
                return;
            }
            if (searchModels.get(lastSelection).isSelected()) {
                searchModels.get(lastSelection).setSelected(false);
                notifyItemChanged(lastSelection);
            }
        }
    }

    public SearchModel getItemAt(int position) {
        return searchModels.get(position);
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder viewHolder) {
        clearAnimation(viewHolder.itemView);
    }

    private void clearAnimation(View itemView) {
        ViewGroup viewGroup = (ViewGroup) itemView;
        if (viewGroup != null) viewGroup.clearAnimation();
    }

    @Override
    public int getItemViewType(int position) {
        return searchModels.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type.Search.MOVIES_BY_COMPANIES)
        return new CompanyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_search_company,
                parent, false));
        else if (viewType == Type.Search.MOVIES_BY_PERSON)
            return new PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.row_search_person,
                    parent, false));
        else if (viewType == Type.Search.MOVIES_BY_KEYWORD)
            return new KeywordViewHolder(LayoutInflater.from(context).inflate(R.layout.row_search_keyword,
                    parent, false));
        else
            return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_search_movies,
                    parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchModel searchModel = searchModels.get(position);

        if (holder instanceof CompanyViewHolder) {
            CompanyViewHolder viewHolder = (CompanyViewHolder) holder;

            ViewUtils.changeProgressMode(searchModel.isSelected(), viewHolder.loadingBar);
            viewHolder.tvTitle.setText(searchModel.getTitle());

            Picasso.get()
                    .load(Urls.MOVIE_IMAGES.getUrl() + searchModel.getPosterPath())
//                    .resize(80, 132)
//                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.ivPoster);

        } else if (holder instanceof PersonViewHolder) {
            PersonViewHolder viewHolder = (PersonViewHolder) holder;

            ViewUtils.changeProgressMode(searchModel.isSelected(), viewHolder.loadingBar);
            viewHolder.tvTitle.setText(searchModel.getTitle());

            Picasso.get()
                    .load(Urls.MOVIE_IMAGES.getUrl() + searchModel.getPosterPath())
//                    .resize(80, 132)
//                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.ivPoster);

        } else if (holder instanceof KeywordViewHolder) {
            KeywordViewHolder viewHolder = (KeywordViewHolder) holder;

            ViewUtils.changeProgressMode(searchModel.isSelected(), viewHolder.loadingBar);
            viewHolder.tvTitle.setText(searchModel.getTitle());

        } else {
            MovieViewHolder viewHolder = (MovieViewHolder) holder;

            ViewUtils.changeProgressMode(searchModel.isSelected(), viewHolder.loadingBar);

            ImageView[] rateBarImageViews = viewHolder.getRateBarImageViews();
            for (int i = 0; i < rateBarImageViews.length; i++) {
                if ((i + 1) <= searchModel.getVoteAverage() / 2)
                    rateBarImageViews[i].setImageResource(R.drawable.star_yellow);
                else
                    rateBarImageViews[i].setImageResource(R.drawable.star_grey);
            }

            viewHolder.tvTitle.setText(searchModel.getTitle());
            viewHolder.tvOverview.setText(searchModel.getOverview());

            Picasso.get()
                    .load(Urls.MOVIE_IMAGES.getUrl() + searchModel.getPosterPath())
//                    .resize(80, 132)
//                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(viewHolder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle, tvOverview;

        public MovieViewHolder(@NonNull View itemView) {
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

    public class KeywordViewHolder extends RecyclerView.ViewHolder {
        /*private ImageView ivPoster, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5*/;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle/*, tvOverview*/;

        public KeywordViewHolder(@NonNull View itemView) {
            super(itemView);

//            ivPoster = itemView.findViewById(R.id.iv_poster);

//            ivRating1 = itemView.findViewById(R.id.iv_rate_1);
//            ivRating2 = itemView.findViewById(R.id.iv_rate_2);
//            ivRating3 = itemView.findViewById(R.id.iv_rate_3);
//            ivRating4 = itemView.findViewById(R.id.iv_rate_4);
//            ivRating5 = itemView.findViewById(R.id.iv_rate_5);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvOverview = itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }

//        public ImageView[] getRateBarImageViews() {
//            return new ImageView[]{ivRating1, ivRating2, ivRating3, ivRating4, ivRating5};
//        }
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster/*, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5*/;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle/*, tvOverview*/;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_poster);

//            ivRating1 = itemView.findViewById(R.id.iv_rate_1);
//            ivRating2 = itemView.findViewById(R.id.iv_rate_2);
//            ivRating3 = itemView.findViewById(R.id.iv_rate_3);
//            ivRating4 = itemView.findViewById(R.id.iv_rate_4);
//            ivRating5 = itemView.findViewById(R.id.iv_rate_5);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvOverview = itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }

//        public ImageView[] getRateBarImageViews() {
//            return new ImageView[]{ivRating1, ivRating2, ivRating3, ivRating4, ivRating5};
//        }
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster/*, ivRating1, ivRating2, ivRating3, ivRating4, ivRating5*/;
        private ContentLoadingProgressBar loadingBar;
        private TextView tvTitle/*, tvOverview*/;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_poster);

//            ivRating1 = itemView.findViewById(R.id.iv_rate_1);
//            ivRating2 = itemView.findViewById(R.id.iv_rate_2);
//            ivRating3 = itemView.findViewById(R.id.iv_rate_3);
//            ivRating4 = itemView.findViewById(R.id.iv_rate_4);
//            ivRating5 = itemView.findViewById(R.id.iv_rate_5);

            loadingBar = itemView.findViewById(R.id.pb_loading);

            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvOverview = itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null) {
                    final int finalLastPosition = lastSelection;
                    notifySelection(getAdapterPosition());
                    onItemSelectionListener.onItemSelected(finalLastPosition, getAdapterPosition());
                }
            });
        }

//        public ImageView[] getRateBarImageViews() {
//            return new ImageView[]{ivRating1, ivRating2, ivRating3, ivRating4, ivRating5};
//        }
    }
}
