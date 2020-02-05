package com.spoiledit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;

public class MoviesPopularAdapter extends RootSelectionAdapter {
    public static final String TAG = MoviesPopularAdapter.class.getCanonicalName();

    private Context context;

    public MoviesPopularAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void notifySelection(int currentSelection) {

    }

    @Override
    public void removeLastSelection() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularMovieViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movies_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder{

        public PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
