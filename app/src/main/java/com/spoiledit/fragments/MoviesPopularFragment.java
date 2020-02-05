package com.spoiledit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;
import com.spoiledit.adapters.MoviesPopularAdapter;

public class MoviesPopularFragment extends RootFragment {
    public static final String TAG = MoviesPopularFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_popular, container, false);
    }

    @Override
    public void initUi(View view) {

    }

    @Override
    public void initialiseListener(View view) {

    }

    @Override
    public void setData(View view) {

    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies_popular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MoviesPopularAdapter popularAdapter = new MoviesPopularAdapter(getContext());
        recyclerView.setAdapter(popularAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
