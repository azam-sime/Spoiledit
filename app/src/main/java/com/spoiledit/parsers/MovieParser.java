package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.MovieModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MovieParser {
    public static final String TAG = MovieParser.class.getCanonicalName();



    public static class PopularsParser extends AsyncTask<JSONObject, Void, List<MoviePopularModel>> {
        public static final String TAG = PopularsParser.class.getCanonicalName();

        @Override
        protected List<MoviePopularModel> doInBackground(JSONObject... jsonObjects) {
            List<MoviePopularModel> moviePopularModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject movieObject = dataArray.optJSONObject(i);
                            MoviePopularModel popularModel = new MoviePopularModel();
                            popularModel.setId(movieObject.optInt("id"));

                            JSONObject castsObject = movieObject.optJSONObject("stars");
                            List<String> casts = new ArrayList<>();
                            if (castsObject != null) {
                                Iterator<String> iterator = castsObject.keys();
                                while (iterator.hasNext())
                                    casts.add(iterator.next());
                            }
                            popularModel.setCasts(casts.toArray(new String[0]));

                            JSONObject directorsObject = movieObject.optJSONObject("director");
                            List<String> directors = new ArrayList<>();
                            if (directorsObject != null) {
                                Iterator<String> iterator = directorsObject.keys();
                                while (iterator.hasNext())
                                    directors.add(iterator.next());
                            }
                            popularModel.setDirectors(directors.toArray(new String[0]));

                            JSONArray genreArray = movieObject.optJSONArray("genres");
                            if (genreArray != null) {
                                String[] genres = new String[genreArray.length()];
                                for (int j = 0; j < genreArray.length(); j++)
                                    genres[j] = genreArray.optString(j);
                                popularModel.setGenres(genres);
                            } else
                                popularModel.setGenres(new String[0]);

                            popularModel.setOverview(movieObject.optString("overview"));
                            popularModel.setPgNames(movieObject.optString("pg_names"));
                            popularModel.setPopularity(movieObject.optInt("popularity"));
                            popularModel.setPosterPath(movieObject.optString("poster_path"));
                            popularModel.setReleaseDate(movieObject.optString("release_date"));
                            popularModel.setRunTime(movieObject.optString("runtime"));
                            popularModel.setTitle(movieObject.optString("title"));
                            popularModel.setTotalPages(movieObject.optInt("total_pages"));
                            popularModel.setVoteAverage(movieObject.optInt("vote_average"));

                            moviePopularModels.add(popularModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return moviePopularModels;
        }
    }



    public static class RecentsParser extends AsyncTask<JSONObject, Void, List<MovieRecentModel>> {
        public static final String TAG = RecentsParser.class.getCanonicalName();

        @Override
        protected List<MovieRecentModel> doInBackground(JSONObject... jsonObjects) {
            List<MovieRecentModel> movieRecentModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject movieObject = dataArray.optJSONObject(i);
                            MovieRecentModel recentModel = new MovieRecentModel();
                            recentModel.setId(movieObject.optInt("id"));

                            JSONObject castsObject = movieObject.optJSONObject("stars");
                            List<String> casts = new ArrayList<>();
                            if (castsObject != null) {
                                Iterator<String> iterator = castsObject.keys();
                                while (iterator.hasNext())
                                    casts.add(iterator.next());
                            }
                            recentModel.setCasts(casts.toArray(new String[0]));

                            JSONObject directorsObject = movieObject.optJSONObject("director");
                            List<String> directors = new ArrayList<>();
                            if (directorsObject != null) {
                                Iterator<String> iterator = directorsObject.keys();
                                while (iterator.hasNext())
                                    directors.add(iterator.next());
                            }
                            recentModel.setDirectors(directors.toArray(new String[0]));

                            JSONArray genreArray = movieObject.optJSONArray("genres");
                            if (genreArray != null) {
                                String[] genres = new String[genreArray.length()];
                                for (int j = 0; j < genreArray.length(); j++)
                                    genres[j] = genreArray.optString(j);
                                recentModel.setGenres(genres);
                            } else
                                recentModel.setGenres(new String[0]);

                            recentModel.setOverview(movieObject.optString("overview"));
                            recentModel.setPgNames(movieObject.optString("pg_names"));
                            recentModel.setPopularity(movieObject.optInt("popularity"));
                            recentModel.setPosterPath(movieObject.optString("poster_path"));
                            recentModel.setReleaseDate(movieObject.optString("release_date"));
                            recentModel.setRunTime(movieObject.optString("runtime"));
                            recentModel.setTitle(movieObject.optString("title"));
                            recentModel.setTotalPages(movieObject.optInt("total_pages"));
                            recentModel.setVoteAverage(movieObject.optInt("vote_average"));

                            movieRecentModels.add(recentModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieRecentModels;
        }
    }



    public static class UpcomingsParser extends AsyncTask<JSONObject, Void, List<MovieUpcomingModel>> {
        public static final String TAG = UpcomingsParser.class.getCanonicalName();

        @Override
        protected List<MovieUpcomingModel> doInBackground(JSONObject... jsonObjects) {
            List<MovieUpcomingModel> movieUpcomingModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject movieObject = dataArray.optJSONObject(i);
                            MovieUpcomingModel upcomingModel = new MovieUpcomingModel();
                            upcomingModel.setId(movieObject.optInt("id"));

                            JSONObject castsObject = movieObject.optJSONObject("stars");
                            List<String> casts = new ArrayList<>();
                            if (castsObject != null) {
                                Iterator<String> iterator = castsObject.keys();
                                while (iterator.hasNext())
                                    casts.add(iterator.next());
                            }
                            upcomingModel.setCasts(casts.toArray(new String[0]));

                            JSONObject directorsObject = movieObject.optJSONObject("director");
                            List<String> directors = new ArrayList<>();
                            if (directorsObject != null) {
                                Iterator<String> iterator = directorsObject.keys();
                                while (iterator.hasNext())
                                    directors.add(iterator.next());
                            }
                            upcomingModel.setDirectors(directors.toArray(new String[0]));

                            JSONArray genreArray = movieObject.optJSONArray("genres");
                            if (genreArray != null) {
                                String[] genres = new String[genreArray.length()];
                                for (int j = 0; j < genreArray.length(); j++)
                                    genres[j] = genreArray.optString(j);
                                upcomingModel.setGenres(genres);
                            } else
                                upcomingModel.setGenres(new String[0]);

                            upcomingModel.setOverview(movieObject.optString("overview"));
                            upcomingModel.setPgNames(movieObject.optString("pg_names"));
                            upcomingModel.setPopularity(movieObject.optInt("popularity"));
                            upcomingModel.setPosterPath(movieObject.optString("poster_path"));
                            upcomingModel.setReleaseDate(movieObject.optString("release_date"));
                            upcomingModel.setRunTime(movieObject.optString("runtime"));
                            upcomingModel.setTitle(movieObject.optString("title"));
                            upcomingModel.setTotalPages(movieObject.optInt("total_pages"));
                            upcomingModel.setVoteAverage(movieObject.optInt("vote_average"));

                            movieUpcomingModels.add(upcomingModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieUpcomingModels;
        }
    }
}
