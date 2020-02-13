package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.MovieModel;
import com.spoiledit.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MovieParser extends AsyncTask<String, Void, List<MovieModel>> {
    @Override
    protected List<MovieModel> doInBackground(String... strings) {
        List<MovieModel> movieModels = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(strings[0]);
            JSONObject dataObject = jsonObject.optJSONObject("data");
            if (dataObject != null) {
                JSONArray dataArray = dataObject.optJSONArray("data");
                if (dataArray != null) {
                    int l = dataArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject movieObject = dataArray.optJSONObject(i);
                        MovieModel movieModel = new MovieModel();

                        movieModel.setId(movieObject.optInt("id"));

                        JSONObject castsObject = movieObject.optJSONObject("stars");
                        List<String> casts = new ArrayList<>();
                        if (castsObject != null) {
                            Iterator<String> iterator = castsObject.keys();
                            while (iterator.hasNext())
                                casts.add(iterator.next());
                        }
                        movieModel.setCasts(casts.toArray(new String[0]));

                        JSONObject directorsObject = movieObject.optJSONObject("director");
                        List<String> directors = new ArrayList<>();
                        if (directorsObject != null) {
                            Iterator<String> iterator = directorsObject.keys();
                            while (iterator.hasNext())
                                directors.add(iterator.next());
                        }
                        movieModel.setDirectors(directors.toArray(new String[0]));

                        JSONArray genreArray = movieObject.optJSONArray("genres");
                        if (genreArray != null) {
                            String[] genres = new String[genreArray.length()];
                            for (int j = 0; j < genreArray.length(); j++)
                                genres[j] = genreArray.optString(j);
                            movieModel.setGenres(genres);
                        } else
                            movieModel.setGenres(new String[0]);

                        movieModel.setOverview(movieObject.optString("overview"));
                        movieModel.setPgNames(movieObject.optString("pg_names"));
                        movieModel.setPopularity(movieObject.optInt("popularity"));
                        movieModel.setPosterPath(movieObject.optString("poster_path"));
                        movieModel.setReleaseDate(movieObject.optString("release_date"));
                        movieModel.setRunTime(movieObject.optString("runtime"));
                        movieModel.setTitle(movieObject.optString("title"));
                        movieModel.setTotalPages(movieObject.optInt("total_pages"));
                        movieModel.setVoteAverage(movieObject.optInt("vote_average"));

                        movieModels.add(movieModel);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieModels;
    }
}
