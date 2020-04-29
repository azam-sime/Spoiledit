package com.spoiledit.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spoiledit.models.ApiStatusModel;
import com.spoiledit.models.SearchModel;
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

    public MutableLiveData<List<SearchModel>> getMoviesByTitleMutable() {
        return searchRepo.getMoviesByTitleMutable();
    }

    public MutableLiveData<List<SearchModel>> getMoviesByPersonMutable() {
        return searchRepo.getMoviesByPersonMutable();
    }

    public MutableLiveData<List<SearchModel>> getMoviesByKeywordMutable() {
        return searchRepo.getMoviesByKeywordMutable();
    }

    public MutableLiveData<List<SearchModel>> getMoviesByCompaniesMutable() {
        return searchRepo.getMoviesByCompaniesMutable();
    }

    public MutableLiveData<List<SearchModel>> getMoviesFromKeywordMutable() {
        return searchRepo.getMoviesFromKeywordMutable();
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

    public void requestMovieDetails(int movieId, int fromTab) {
        searchRepo.requestMovieDetails(movieId, fromTab);
    }

    public void requestKeywordDetails(int keywordId) {
        searchRepo.requestKeywordDetails(keywordId);
    }

    public void requestPersonDetails(int personId) {
        searchRepo.requestPersonDetails(personId);
    }

    public void requestCompanyDetails(int companyId) {
        searchRepo.requestCompanyDetails(companyId);
    }
}
