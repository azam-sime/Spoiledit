package com.spoiledit.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SearchMovieModel;
import com.spoiledit.models.SpoilersNewModel;
import com.spoiledit.repos.SearchRepo;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private SearchRepo searchRepo;

    public SearchViewModel() {
        this.searchRepo = SearchRepo.getInstance();
    }

    public String getSearchValue() {
        return searchRepo.getSearchValue();
    }

    public MutableLiveData<ApiStatusModel> getApiStatusModelMutable() {
        return searchRepo.getApiStatusModelMutable();
    }

    public MutableLiveData<List<String>> getSearchValues() {
        return searchRepo.getSearchValues();
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByTitleMutable() {
        return searchRepo.getMoviesByTitleMutable();
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByPersonMutable() {
        return searchRepo.getMoviesByPersonMutable();
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByKeywordMutable() {
        return searchRepo.getMoviesByKeywordMutable();
    }

    public MutableLiveData<List<SearchMovieModel>> getMoviesByCompaniesMutable() {
        return searchRepo.getMoviesByCompaniesMutable();
    }

    public void requestSearchAutoCompleteValues(String searchValue) {
        searchRepo.requestSearchAutoCompleteValues(searchValue);
    }

    public void requestMoviesByTitle() {
        searchRepo.requestMoviesByTitle();
    }

    public void requestMoviesByPerson() {
        searchRepo.requestMoviesByPerson();
    }

    public void requestMoviesByKeyword() {
        searchRepo.requestMoviesByKeyword();
    }

    public void requestMoviesByCompanies() {
        searchRepo.requestMoviesByCompanies();
    }

    public void requestMovieDetails(int movieId) {
        searchRepo.requestMovieDetails(movieId);
    }
}
