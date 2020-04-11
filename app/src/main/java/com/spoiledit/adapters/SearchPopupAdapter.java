package com.spoiledit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spoiledit.R;
import com.spoiledit.listeners.OnItemSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPopupAdapter extends RecyclerView.Adapter<SearchPopupAdapter.SearchViewHolder> {
    private Context context;
    private List<String> searchValues;
    private OnItemSelectionListener onItemSelectionListener;

    public SearchPopupAdapter(Context context) {
        this.context = context;
        this.searchValues = new ArrayList<>();
    }

    public void setOnItemSelectionListener(OnItemSelectionListener onItemSelectionListener) {
        this.onItemSelectionListener = onItemSelectionListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.row_search_value, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tvSearchValue.setText(searchValues.get(position));
    }

    @Override
    public int getItemCount() {
        return searchValues.size();
    }

    public void setSearchValues(List<String> searchValues) {
        this.searchValues.clear();
        if (searchValues != null)
            this.searchValues.addAll(searchValues);
        notifyDataSetChanged();
    }

    public String getSearchValue(int position) {
        return searchValues.get(position);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSearchValue;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSearchValue = itemView.findViewById(R.id.tv_search_value);

            itemView.setOnClickListener(v -> {
                if (onItemSelectionListener != null)
                    onItemSelectionListener.onItemSelected(-1, getAdapterPosition());
            });
        }
    }
}
