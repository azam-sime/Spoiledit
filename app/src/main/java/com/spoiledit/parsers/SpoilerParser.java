package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;
import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpoilerParser {
    public static final String TAG = SpoilerParser.class.getCanonicalName();



    public static class FullsParser extends AsyncTask<JSONObject, Void, List<SpoilerFullModel>> {
        public static final String TAG = FullsParser.class.getCanonicalName();

        @Override
        protected List<SpoilerFullModel> doInBackground(JSONObject... jsonObjects) {
            List<SpoilerFullModel> spoilerFullModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject spoilerObject = dataArray.optJSONObject(i);
                            SpoilerFullModel fullModel = new SpoilerFullModel();
                            fullModel.setId(spoilerObject.optInt("id"));

//                            JSONObject castsObject = spoilerObject.optJSONObject("stars");
//                            List<String> casts = new ArrayList<>();
//                            if (castsObject != null) {
//                                Iterator<String> iterator = castsObject.keys();
//                                while (iterator.hasNext())
//                                    casts.add(iterator.next());
//                            }
//                            fullModel.setCasts(casts.toArray(new String[0]));
//
//                            JSONObject directorsObject = spoilerObject.optJSONObject("director");
//                            List<String> directors = new ArrayList<>();
//                            if (directorsObject != null) {
//                                Iterator<String> iterator = directorsObject.keys();
//                                while (iterator.hasNext())
//                                    directors.add(iterator.next());
//                            }
//                            fullModel.setDirectors(directors.toArray(new String[0]));
//
//                            JSONArray genreArray = spoilerObject.optJSONArray("genres");
//                            if (genreArray != null) {
//                                String[] genres = new String[genreArray.length()];
//                                for (int j = 0; j < genreArray.length(); j++)
//                                    genres[j] = genreArray.optString(j);
//                                fullModel.setGenres(genres);
//                            } else
//                                fullModel.setGenres(new String[0]);
//
//                            fullModel.setOverview(spoilerObject.optString("overview"));
//                            fullModel.setPgNames(spoilerObject.optString("pg_names"));
//                            fullModel.setPopularity(spoilerObject.optInt("popularity"));
//                            fullModel.setPosterPath(spoilerObject.optString("poster_path"));
//                            fullModel.setReleaseDate(spoilerObject.optString("release_date"));
//                            fullModel.setRunTime(spoilerObject.optString("runtime"));
//                            fullModel.setTitle(spoilerObject.optString("title"));
//                            fullModel.setTotalPages(spoilerObject.optInt("total_pages"));
//                            fullModel.setVoteAverage(spoilerObject.optInt("vote_average"));

                            spoilerFullModels.add(fullModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spoilerFullModels;
        }
    }



    public static class BriefsParser extends AsyncTask<JSONObject, Void, List<SpoilerBriefModel>> {
        public static final String TAG = BriefsParser.class.getCanonicalName();

        @Override
        protected List<SpoilerBriefModel> doInBackground(JSONObject... jsonObjects) {
            List<SpoilerBriefModel> spoilerBriefModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject spoilerObject = dataArray.optJSONObject(i);
                            SpoilerBriefModel briefModel = new SpoilerBriefModel();
                            briefModel.setId(spoilerObject.optInt("id"));

//                            JSONObject castsObject = spoilerObject.optJSONObject("stars");
//                            List<String> casts = new ArrayList<>();
//                            if (castsObject != null) {
//                                Iterator<String> iterator = castsObject.keys();
//                                while (iterator.hasNext())
//                                    casts.add(iterator.next());
//                            }
//                            briefModel.setCasts(casts.toArray(new String[0]));
//
//                            JSONObject directorsObject = spoilerObject.optJSONObject("director");
//                            List<String> directors = new ArrayList<>();
//                            if (directorsObject != null) {
//                                Iterator<String> iterator = directorsObject.keys();
//                                while (iterator.hasNext())
//                                    directors.add(iterator.next());
//                            }
//                            briefModel.setDirectors(directors.toArray(new String[0]));
//
//                            JSONArray genreArray = spoilerObject.optJSONArray("genres");
//                            if (genreArray != null) {
//                                String[] genres = new String[genreArray.length()];
//                                for (int j = 0; j < genreArray.length(); j++)
//                                    genres[j] = genreArray.optString(j);
//                                briefModel.setGenres(genres);
//                            } else
//                                briefModel.setGenres(new String[0]);
//
//                            briefModel.setOverview(spoilerObject.optString("overview"));
//                            briefModel.setPgNames(spoilerObject.optString("pg_names"));
//                            briefModel.setPopularity(spoilerObject.optInt("popularity"));
//                            briefModel.setPosterPath(spoilerObject.optString("poster_path"));
//                            briefModel.setReleaseDate(spoilerObject.optString("release_date"));
//                            briefModel.setRunTime(spoilerObject.optString("runtime"));
//                            briefModel.setTitle(spoilerObject.optString("title"));
//                            briefModel.setTotalPages(spoilerObject.optInt("total_pages"));
//                            briefModel.setVoteAverage(spoilerObject.optInt("vote_average"));

                            spoilerBriefModels.add(briefModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spoilerBriefModels;
        }
    }



    public static class EndingsParser extends AsyncTask<JSONObject, Void, List<SpoilerEndingModel>> {
        public static final String TAG = EndingsParser.class.getCanonicalName();

        @Override
        protected List<SpoilerEndingModel> doInBackground(JSONObject... jsonObjects) {
            List<SpoilerEndingModel> spoilerEndingModels = new ArrayList<>();
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject spoilerObject = dataArray.optJSONObject(i);
                            SpoilerEndingModel endingModel = new SpoilerEndingModel();
                            endingModel.setId(spoilerObject.optInt("id"));

//                            JSONObject castsObject = spoilerObject.optJSONObject("stars");
//                            List<String> casts = new ArrayList<>();
//                            if (castsObject != null) {
//                                Iterator<String> iterator = castsObject.keys();
//                                while (iterator.hasNext())
//                                    casts.add(iterator.next());
//                            }
//                            endingModel.setCasts(casts.toArray(new String[0]));
//
//                            JSONObject directorsObject = spoilerObject.optJSONObject("director");
//                            List<String> directors = new ArrayList<>();
//                            if (directorsObject != null) {
//                                Iterator<String> iterator = directorsObject.keys();
//                                while (iterator.hasNext())
//                                    directors.add(iterator.next());
//                            }
//                            endingModel.setDirectors(directors.toArray(new String[0]));
//
//                            JSONArray genreArray = spoilerObject.optJSONArray("genres");
//                            if (genreArray != null) {
//                                String[] genres = new String[genreArray.length()];
//                                for (int j = 0; j < genreArray.length(); j++)
//                                    genres[j] = genreArray.optString(j);
//                                endingModel.setGenres(genres);
//                            } else
//                                endingModel.setGenres(new String[0]);
//
//                            endingModel.setOverview(spoilerObject.optString("overview"));
//                            endingModel.setPgNames(spoilerObject.optString("pg_names"));
//                            endingModel.setPopularity(spoilerObject.optInt("popularity"));
//                            endingModel.setPosterPath(spoilerObject.optString("poster_path"));
//                            endingModel.setReleaseDate(spoilerObject.optString("release_date"));
//                            endingModel.setRunTime(spoilerObject.optString("runtime"));
//                            endingModel.setTitle(spoilerObject.optString("title"));
//                            endingModel.setTotalPages(spoilerObject.optInt("total_pages"));
//                            endingModel.setVoteAverage(spoilerObject.optInt("vote_average"));

                            spoilerEndingModels.add(endingModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spoilerEndingModels;
        }
    }
}
