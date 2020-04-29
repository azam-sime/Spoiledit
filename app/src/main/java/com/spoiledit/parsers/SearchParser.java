package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.constants.Type;
import com.spoiledit.models.SearchModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchParser extends AsyncTask<JSONObject, Void, List<SearchModel>> {
    public static final String TAG = SearchParser.class.getCanonicalName();

    public static final class QueryParser extends AsyncTask<JSONObject, Void, List<String>> {
        public static final String TAG = QueryParser.class.getCanonicalName();

        @Override
        protected List<String> doInBackground(JSONObject... jsonObjects) {
            List<String> searchValues = new ArrayList<>();

            try {
                JSONObject jsonObject = jsonObjects[0];

                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    searchValues.add(jsonArray.optString(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return searchValues;
        }
    }

    private int type;

    public SearchParser(int type) {
        this.type = type;
    }

    @Override
    protected List<SearchModel> doInBackground(JSONObject... jsonObjects) {
        List<SearchModel> searchModels = new ArrayList<>();
        try {
            JSONObject dataObject = jsonObjects[0].optJSONObject("data");
            if (dataObject != null) {

                JSONArray resultArray = dataObject.optJSONArray("results");
                if (resultArray != null) {

                    int l = resultArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject searchObject = resultArray.optJSONObject(i);
                        SearchModel searchModel = new SearchModel();
                        searchModel.setId(searchObject.optInt("id"));
                        searchModel.setType(type);

                        if (type == Type.Search.MOVIES_BY_TITLE || type == Type.Search.MOVIES_FROM_KEYWORD)
                            parseMovie(searchObject, searchModel);
                        else if (type == Type.Search.MOVIES_BY_KEYWORD)
                            parseKeyword(searchObject, searchModel);
                        else if (type == Type.Search.MOVIES_BY_PERSON)
                            parsePerson(searchObject, searchModel);
                        else if (type == Type.Search.MOVIES_BY_COMPANIES)
                            parseCompany(searchObject, searchModel);

                        searchModels.add(searchModel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchModels;
    }

    private void parseMovie(JSONObject movieObject, SearchModel searchModel) {
        JSONObject castsObject = movieObject.optJSONObject("stars");
        List<String> casts = new ArrayList<>();
        if (castsObject != null) {
            Iterator<String> iterator = castsObject.keys();
            while (iterator.hasNext())
                casts.add(iterator.next());
        }
        searchModel.setCasts(casts.toArray(new String[0]));

        JSONObject directorsObject = movieObject.optJSONObject("director");
        List<String> directors = new ArrayList<>();
        if (directorsObject != null) {
            Iterator<String> iterator = directorsObject.keys();
            while (iterator.hasNext())
                directors.add(iterator.next());
        }
        searchModel.setDirectors(directors.toArray(new String[0]));

        JSONArray genreArray = movieObject.optJSONArray("genres");
        if (genreArray != null) {
            String[] genres = new String[genreArray.length()];
            for (int j = 0; j < genreArray.length(); j++)
                genres[j] = genreArray.optString(j);
            searchModel.setGenres(genres);
        } else
            searchModel.setGenres(new String[0]);

        searchModel.setOverview(movieObject.optString("overview"));
        searchModel.setPgNames(movieObject.optString("pg_names"));
        searchModel.setPopularity(movieObject.optInt("popularity"));
        searchModel.setPosterPath(movieObject.optString("poster_path"));
        searchModel.setReleaseDate(movieObject.optString("release_date"));
        searchModel.setRunTime(movieObject.optString("runtime"));
        searchModel.setTitle(movieObject.optString("title"));
        searchModel.setTotalPages(movieObject.optInt("total_pages"));
        searchModel.setVoteAverage(movieObject.optInt("vote_average"));
    }

    private void parseKeyword(JSONObject keywordObject, SearchModel searchModel) {
        searchModel.setTitle(keywordObject.optString("name"));
    }

    private void parsePerson(JSONObject personObject, SearchModel searchModel) {
        searchModel.setPosterPath(personObject.optString("profile_path"));
        searchModel.setTitle(personObject.optString("name"));
    }

    private void parseCompany(JSONObject companyObject, SearchModel searchModel) {
        searchModel.setPosterPath(companyObject.optString("logo_path"));
        searchModel.setTitle(companyObject.optString("name"));
    }
}
