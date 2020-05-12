package com.spoiledit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spoiledit.R;
import com.spoiledit.activities.DetailsMovieActivity;
import com.spoiledit.activities.DetailsSpoilersActivity;
import com.spoiledit.activities.MySpoilersActivity;
import com.spoiledit.activities.ProfileOtherActivity;
import com.spoiledit.adapters.SpoilerEndingAdapter;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.listeners.OnSpoilerActionClickListener;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsSpoilersViewModel;

public class SpoilersEndingFragment extends RootFragment {
    public static final String TAG = SpoilersEndingFragment.class.getCanonicalName();

    private DetailsSpoilersViewModel detailsSpoilersViewModel;
    private SpoilerEndingAdapter spoilerEndingAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsSpoilersViewModel = ViewModelProviders.of(getActivity()).get(DetailsSpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_ending, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers_ending);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_ending);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerEndingAdapter = new SpoilerEndingAdapter(getContext(),
                new OnSpoilerActionClickListener() {
                    @Override
                    public void onUserClicked(int position) {
                        Intent intent = new Intent(getContext(), ProfileOtherActivity.class);
                        intent.putExtra(App.Intent.Extra.USER_SPOILER, spoilerEndingAdapter.getItemAt(position).toSpoilerUserModel());
                        startActivity(intent);
                    }

                    @Override
                    public void onContentToggled(int position) {
                        spoilerEndingAdapter.getItemAt(position).setTrimmed(
                                !spoilerEndingAdapter.getItemAt(position).isTrimmed());
                        spoilerEndingAdapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onThumbsUp(int position) {
                        detailsSpoilersViewModel.thumbsUpSpoiler(2, spoilerEndingAdapter.getItemAt(position).getId(), true);
                    }

                    @Override
                    public void onThumbsDown(int position) {
                        detailsSpoilersViewModel.thumbsDownSpoiler(2, spoilerEndingAdapter.getItemAt(position).getId(), true);
                    }

                    @Override
                    public void onItemSelected(int lastPosition, int position) {
                        if (getActivity() instanceof DetailsMovieActivity) {
                            ((DetailsMovieActivity) getActivity()).gotoCommentsActivity(
                                    spoilerEndingAdapter.getItemAt(position));
                            spoilerEndingAdapter.removeLastSelection();
                        } else if (getActivity() instanceof DetailsSpoilersActivity) {
                            ((DetailsSpoilersActivity) getActivity()).gotoCommentsActivity(
                                    spoilerEndingAdapter.getItemAt(position));
                            spoilerEndingAdapter.removeLastSelection();
                        }
                    }
                });

        recyclerView.setAdapter(spoilerEndingAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilerEndingAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        detailsSpoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_SPOILERS_ENDING) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(!swipeRefreshLayout.isRefreshing(), apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    swipeRefreshLayout.setRefreshing(false);
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR) {
                        setError(apiStatusModel.getMessage());
                        showFailure(false, apiStatusModel.getMessage());
                    }
                }
            } else if (apiStatusModel.getFromScreen() == 2
                    && (apiStatusModel.getApi() == Constants.Api.THUMBS_UP_SPOILER_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_UP_SPOILER_REMOVE)) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(getContext(), apiStatusModel.getMessage());
                    else
                        requestData();
                }
            } else if (apiStatusModel.getFromScreen() == 2
                    && (apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_SPOILER_ADD
                    || apiStatusModel.getApi() == Constants.Api.THUMBS_DOWN_SPOILER_REMOVE)) {
                if (apiStatusModel.getStatus() == Status.Request.API_HIT) {
                    showLoader(apiStatusModel.getMessage());

                } else {
                    hideLoader();
                    if (apiStatusModel.getStatus() == Status.Request.API_ERROR)
                        DialogUtils.showToast(getContext(), apiStatusModel.getMessage());
                    else
                        requestData();
                }
            }
        });

        detailsSpoilersViewModel.getSpoilerEndingModelsMutable().observe(this, spoilerEndingModels -> {
            if (spoilerEndingModels != null)
                spoilerEndingAdapter.setItems(spoilerEndingModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilerEndingAdapter.getItemCount() == 0);
    }

    @Override
    public void requestData() {
        detailsSpoilersViewModel.requestSpoilerEnding();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilerEndingAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
