package com.spoiledit.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;
import com.spoiledit.adapters.SearchPopupAdapter;
import com.spoiledit.listeners.OnItemSelectionListener;

import java.util.List;

public class SearchWindow extends PopupWindow {
    private Context context;
    private RecyclerView rvSearchValues;
    private SearchPopupAdapter searchPopupAdapter;

    public SearchWindow(Context context) {
        super(context);
        this.context = context;

        initialiseView();
    }

    private void initialiseView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_view, null);

        rvSearchValues = view.findViewById(R.id.rv_search_values);
        rvSearchValues.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvSearchValues.setHasFixedSize(true);
        rvSearchValues.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        searchPopupAdapter = new SearchPopupAdapter(context);
        rvSearchValues.setAdapter(searchPopupAdapter);

        setContentView(view);
    }

    public void setOnItemSelectionListener(OnItemSelectionListener onItemSelectionListener) {
        searchPopupAdapter.setOnItemSelectionListener(onItemSelectionListener);
    }

    public void setSearchValues(List<String> searchValues) {
        searchPopupAdapter.setSearchValues(searchValues);
    }

    public String getSearchValue(int position) {
        return searchPopupAdapter.getSearchValue(position);
    }
}
