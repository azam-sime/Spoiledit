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
import com.spoiledit.activities.ProfileOtherActivity;
import com.spoiledit.adapters.SpoilerFullAdapter;
import com.spoiledit.constants.App;
import com.spoiledit.constants.Constants;
import com.spoiledit.constants.Status;
import com.spoiledit.listeners.OnSpoilerActionClickListener;
import com.spoiledit.utils.DialogUtils;
import com.spoiledit.utils.ViewUtils;
import com.spoiledit.viewmodels.DetailsSpoilersViewModel;

public class SpoilersFullFragment extends RootFragment {
    public static final String TAG = SpoilersFullFragment.class.getCanonicalName();

    private DetailsSpoilersViewModel detailsSpoilersViewModel;
    private SpoilerFullAdapter spoilerFullAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsSpoilersViewModel = ViewModelProviders.of(getActivity()).get(DetailsSpoilersViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spoilers_full, container, false);
    }

    @Override
    public void initUi(View view) {
        swipeRefreshLayout = view.findViewById(R.id.srl_spoilers_full);
    }

    @Override
    public void initialiseListener(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUpRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_spoilers_full);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spoilerFullAdapter = new SpoilerFullAdapter(getContext(),
                new OnSpoilerActionClickListener() {
                    @Override
                    public void onUserClicked(int position) {
                        Intent intent = new Intent(getContext(), ProfileOtherActivity.class);
                        intent.putExtra(App.Intent.Extra.USER_SPOILER, spoilerFullAdapter.getItemAt(position).toSpoilerUserModel());
                        startActivity(intent);
                    }

                    @Override
                    public void onContentToggled(int position) {
                        spoilerFullAdapter.getItemAt(position).setTrimmed(
                                !spoilerFullAdapter.getItemAt(position).isTrimmed());
                        spoilerFullAdapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onThumbsUp(int position) {
                        detailsSpoilersViewModel.thumbsUpSpoiler(0, spoilerFullAdapter.getItemAt(position).getId(), true);
                    }

                    @Override
                    public void onThumbsDown(int position) {
                        detailsSpoilersViewModel.thumbsDownSpoiler(0, spoilerFullAdapter.getItemAt(position).getId(), true);
                    }

                    @Override
                    public void onItemSelected(int lastPosition, int position) {
                        if (getActivity() instanceof DetailsMovieActivity) {
                            ((DetailsMovieActivity) getActivity()).gotoCommentsActivity(
                                    spoilerFullAdapter.getItemAt(position));
                            spoilerFullAdapter.removeLastSelection();
                        } else if (getActivity() instanceof DetailsSpoilersActivity) {
                            ((DetailsSpoilersActivity) getActivity()).gotoCommentsActivity(
                                    spoilerFullAdapter.getItemAt(position));
                            spoilerFullAdapter.removeLastSelection();
                        }
                    }
                });

        recyclerView.setAdapter(spoilerFullAdapter);
        ViewUtils.addFabOffset(getContext(), recyclerView);

        spoilerFullAdapter.registerAdapterDataObserver(getAdapterDataObserver());
    }

    @Override
    public void addObservers() {
        detailsSpoilersViewModel.getApiStatusModelMutable().observe(this, apiStatusModel -> {
            if (apiStatusModel.getApi() == Constants.Api.MOVIE_SPOILERS_FULL) {
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
            } else if (apiStatusModel.getFromScreen() == 0
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
            } else if (apiStatusModel.getFromScreen() == 0
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

        detailsSpoilersViewModel.getSpoilerFullModelsMutable().observe(this, spoilerFullModels -> {
            if (spoilerFullModels != null)
                spoilerFullAdapter.setItems(spoilerFullModels);
        });
    }

    @Override
    public void onAdapterDataChanged() {
        swipeRefreshLayout.setRefreshing(false);
        toggleTvNoData(spoilerFullAdapter.getItemCount() == 0);
    }

    @Override
    public void requestData() {
        detailsSpoilersViewModel.requestSpoilerFull();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spoilerFullAdapter.unregisterAdapterDataObserver(getAdapterDataObserver());
    }
}
