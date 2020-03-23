package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.SpoilerBriefModel;
import com.spoiledit.models.SpoilerEndingModel;
import com.spoiledit.models.SpoilerFullModel;
import com.spoiledit.models.SpoilersNewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpoilerParser {
    public static final String TAG = SpoilerParser.class.getCanonicalName();


    public static class NewParser extends AsyncTask<JSONObject, Void, List<SpoilersNewModel>> {
        public static final String TAG = NewParser.class.getCanonicalName();

        @Override
        protected List<SpoilersNewModel> doInBackground(JSONObject... jsonObjects) {
            List<SpoilersNewModel> spoilersNewModels = new ArrayList<>();
            try {
                JSONArray dataArray = jsonObjects[0].optJSONArray("data");
                if (dataArray != null) {

                    int l = dataArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject spoilerObject = dataArray.optJSONObject(i);
                        SpoilersNewModel spoilersNewModel = new SpoilersNewModel();

                        spoilersNewModel.setId(spoilerObject.optInt("id"));
                        spoilersNewModel.setmId(spoilerObject.optInt("m_id"));
                        spoilersNewModel.setmName(spoilerObject.optString("m_name"));
                        spoilersNewModel.setUserId(spoilerObject.optInt("user_id"));
                        spoilersNewModel.setSelectType(spoilerObject.optInt("select_type"));
                        spoilersNewModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        spoilersNewModel.setStringer(spoilerObject.optString("stringer"));
                        spoilersNewModel.setSpoiler(spoilerObject.optString("spoiler"));
                        spoilersNewModel.setCratedOn(spoilerObject.optString("created_on"));
                        spoilersNewModel.setCategory(spoilerObject.optString("category"));
                        spoilersNewModel.setUsername(spoilerObject.optString("username"));
                        spoilersNewModel.setDateFormat(spoilerObject.optString("date_format"));
                        spoilersNewModel.setPosterPath(spoilerObject.optString("poster_path"));

                        spoilersNewModels.add(spoilersNewModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spoilersNewModels;
        }
    }



    public static class FullsParser extends AsyncTask<JSONObject, Void, List<SpoilerFullModel>> {
        public static final String TAG = FullsParser.class.getCanonicalName();

        @Override
        protected List<SpoilerFullModel> doInBackground(JSONObject... jsonObjects) {
            List<SpoilerFullModel> spoilerFullModels = new ArrayList<>();
            try {
                JSONArray dataArray = jsonObjects[0].optJSONArray("result_data");
                if (dataArray != null) {

                    int l = dataArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject spoilerObject = dataArray.optJSONObject(i);
                        SpoilerFullModel fullModel = new SpoilerFullModel();

                        fullModel.setUserLogin(spoilerObject.optString("user_login"));
                        fullModel.setId(spoilerObject.optInt("id"));
                        fullModel.setmId(spoilerObject.optInt("m_id"));
                        fullModel.setmName(spoilerObject.optString("m_name"));
                        fullModel.setUserId(spoilerObject.optInt("user_id"));
                        fullModel.setSelectType(spoilerObject.optInt("select_type"));
                        fullModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        fullModel.setStringer(spoilerObject.optString("stringer"));
                        fullModel.setSpoiler(spoilerObject.optString("spoiler"));
                        fullModel.setCratedOn(spoilerObject.optString("created_on"));
                        fullModel.setDisplayName(spoilerObject.optString("display_name"));
                        fullModel.setDate(spoilerObject.optString("date"));
                        fullModel.setDescription(spoilerObject.optString("desciption"));
                        fullModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                        fullModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                        fullModel.setAvatarUrl(spoilerObject.optString("avatar_url"));
                        fullModel.setDarkBackground(i % 2 == 1);

                        spoilerFullModels.add(fullModel);
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
                JSONArray dataArray = jsonObjects[0].optJSONArray("result_data");
                if (dataArray != null) {

                    int l = dataArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject spoilerObject = dataArray.optJSONObject(i);
                        SpoilerBriefModel briefModel = new SpoilerBriefModel();

                        briefModel.setUserLogin(spoilerObject.optString("user_login"));
                        briefModel.setId(spoilerObject.optInt("id"));
                        briefModel.setmId(spoilerObject.optInt("m_id"));
                        briefModel.setmName(spoilerObject.optString("m_name"));
                        briefModel.setUserId(spoilerObject.optInt("user_id"));
                        briefModel.setSelectType(spoilerObject.optInt("select_type"));
                        briefModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        briefModel.setStringer(spoilerObject.optString("stringer"));
                        briefModel.setSpoiler(spoilerObject.optString("spoiler"));
                        briefModel.setCratedOn(spoilerObject.optString("created_on"));
                        briefModel.setDisplayName(spoilerObject.optString("display_name"));
                        briefModel.setDate(spoilerObject.optString("date"));
                        briefModel.setDescription(spoilerObject.optString("desciption"));
                        briefModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                        briefModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                        briefModel.setAvatarUrl(spoilerObject.optString("avatar_url"));
                        briefModel.setDarkBackground(i % 2 == 0);

                        spoilerBriefModels.add(briefModel);
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
                JSONObject dataObject = jsonObjects[0];
                if (dataObject != null) {

                    JSONArray dataArray = dataObject.optJSONArray("result_data");
                    if (dataArray != null) {

                        int l = dataArray.length();
                        for (int i = 0; i < l; i++) {

                            JSONObject spoilerObject = dataArray.optJSONObject(i);
                            SpoilerEndingModel endingModel = new SpoilerEndingModel();

                            endingModel.setUserLogin(spoilerObject.optString("user_login"));
                            endingModel.setId(spoilerObject.optInt("id"));
                            endingModel.setmId(spoilerObject.optInt("m_id"));
                            endingModel.setmName(spoilerObject.optString("m_name"));
                            endingModel.setUserId(spoilerObject.optInt("user_id"));
                            endingModel.setSelectType(spoilerObject.optInt("select_type"));
                            endingModel.setMidCredit(spoilerObject.optString("mid_credit"));
                            endingModel.setStringer(spoilerObject.optString("stringer"));
                            endingModel.setSpoiler(spoilerObject.optString("spoiler"));
                            endingModel.setCratedOn(spoilerObject.optString("created_on"));
                            endingModel.setDisplayName(spoilerObject.optString("display_name"));
                            endingModel.setDate(spoilerObject.optString("date"));
                            endingModel.setDescription(spoilerObject.optString("desciption"));
                            endingModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                            endingModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                            endingModel.setAvatarUrl(spoilerObject.optString("avatar_url"));
                            endingModel.setDarkBackground(i % 2 == 0);

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
