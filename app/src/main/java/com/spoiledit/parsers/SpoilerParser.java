package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.MySpoilerModel;
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
                        spoilersNewModel.setSelectType(spoilerObject.optInt("select_type"));
                        spoilersNewModel.setCategory(spoilerObject.optString("category"));
                        spoilersNewModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        spoilersNewModel.setStringer(spoilerObject.optString("stringer"));
                        spoilersNewModel.setSpoiler(spoilerObject.optString("spoiler"));
                        spoilersNewModel.setDateFormat(spoilerObject.optString("date_format"));
                        spoilersNewModel.setCratedOn(spoilerObject.optString("created_on"));
                        spoilersNewModel.setPosterPath(spoilerObject.optString("poster_path"));
                        spoilersNewModel.setUserId(spoilerObject.optInt("user_id"));
                        spoilersNewModel.setUserName(spoilerObject.optString("username"));
                        spoilersNewModel.setUserImage(spoilerObject.optString("user-image"));
                        spoilersNewModel.setUserEmail(spoilerObject.optString("email"));
                        spoilersNewModel.setUserPhone(spoilerObject.optString("phone"));

                        spoilersNewModels.add(spoilersNewModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spoilersNewModels;
        }
    }



    public static class MySpoilersParser extends AsyncTask<JSONObject, Void, List<MySpoilerModel>> {
        public static final String TAG = MySpoilersParser.class.getCanonicalName();

        @Override
        protected List<MySpoilerModel> doInBackground(JSONObject... jsonObjects) {
            List<MySpoilerModel> mySpoilerModels = new ArrayList<>();
            try {
                JSONArray dataArray = jsonObjects[0].optJSONArray("result_data");
                if (dataArray != null) {

                    int l = dataArray.length();
                    for (int i = 0; i < l; i++) {

                        JSONObject spoilerObject = dataArray.optJSONObject(i);
                        MySpoilerModel mySpoilerModel = new MySpoilerModel();

                        mySpoilerModel.setId(spoilerObject.optInt("id"));
                        mySpoilerModel.setmId(spoilerObject.optInt("m_id"));
                        mySpoilerModel.setmName(spoilerObject.optString("m_name"));
                        mySpoilerModel.setSelectType(spoilerObject.optInt("select_type"));
                        mySpoilerModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        mySpoilerModel.setStringer(spoilerObject.optString("stringer"));
                        mySpoilerModel.setSpoiler(spoilerObject.optString("spoiler"));
                        mySpoilerModel.setCratedOn(spoilerObject.optString("created_on"));
                        mySpoilerModel.setDate(spoilerObject.optString("date"));
                        mySpoilerModel.setDescription(spoilerObject.optString("desciption"));
                        mySpoilerModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                        mySpoilerModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                        mySpoilerModel.setUserId(spoilerObject.optInt("user_id"));
                        mySpoilerModel.setUserName(spoilerObject.optString("display_name"));
                        mySpoilerModel.setUserPhotoUrl(spoilerObject.optString("avatar_url"));
                        mySpoilerModel.setUserMail(spoilerObject.optString("user_login"));
//                        mySpoilerModel.setUserPhone(spoilerObject.optString("phone"));
                        mySpoilerModel.setDarkBackground(i % 2 == 1);

                        mySpoilerModels.add(mySpoilerModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mySpoilerModels;
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


                        fullModel.setId(spoilerObject.optInt("id"));
                        fullModel.setmId(spoilerObject.optInt("m_id"));
                        fullModel.setmName(spoilerObject.optString("m_name"));
                        fullModel.setSelectType(spoilerObject.optInt("select_type"));
                        fullModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        fullModel.setStringer(spoilerObject.optString("stringer"));
                        fullModel.setSpoiler(spoilerObject.optString("spoiler"));
                        fullModel.setCratedOn(spoilerObject.optString("created_on"));
                        fullModel.setDate(spoilerObject.optString("date"));
                        fullModel.setDescription(spoilerObject.optString("desciption"));
                        fullModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                        fullModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                        fullModel.setUserId(spoilerObject.optInt("user_id"));
                        fullModel.setUserName(spoilerObject.optString("display_name"));
                        fullModel.setUserPhotoUrl(spoilerObject.optString("avatar_url"));
                        fullModel.setUserMail(spoilerObject.optString("user_login"));
//                        fullModel.setUserPhone(spoilerObject.optString("phone"));
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

                        briefModel.setId(spoilerObject.optInt("id"));
                        briefModel.setmId(spoilerObject.optInt("m_id"));
                        briefModel.setmName(spoilerObject.optString("m_name"));
                        briefModel.setSelectType(spoilerObject.optInt("select_type"));
                        briefModel.setMidCredit(spoilerObject.optString("mid_credit"));
                        briefModel.setStringer(spoilerObject.optString("stringer"));
                        briefModel.setSpoiler(spoilerObject.optString("spoiler"));
                        briefModel.setCratedOn(spoilerObject.optString("created_on"));
                        briefModel.setDate(spoilerObject.optString("date"));
                        briefModel.setDescription(spoilerObject.optString("desciption"));
                        briefModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                        briefModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                        briefModel.setUserId(spoilerObject.optInt("user_id"));
                        briefModel.setUserName(spoilerObject.optString("display_name"));
                        briefModel.setUserPhotoUrl(spoilerObject.optString("avatar_url"));
                        briefModel.setUserMail(spoilerObject.optString("user_login"));
//                        briefModel.setUserPhone(spoilerObject.optString("phone"));
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

                            endingModel.setId(spoilerObject.optInt("id"));
                            endingModel.setmId(spoilerObject.optInt("m_id"));
                            endingModel.setmName(spoilerObject.optString("m_name"));
                            endingModel.setSelectType(spoilerObject.optInt("select_type"));
                            endingModel.setMidCredit(spoilerObject.optString("mid_credit"));
                            endingModel.setStringer(spoilerObject.optString("stringer"));
                            endingModel.setSpoiler(spoilerObject.optString("spoiler"));
                            endingModel.setCratedOn(spoilerObject.optString("created_on"));
                            endingModel.setDate(spoilerObject.optString("date"));
                            endingModel.setDescription(spoilerObject.optString("desciption"));
                            endingModel.setThumbsUp(spoilerObject.optString("thumbs_up"));
                            endingModel.setThumbsDown(spoilerObject.optString("thumbs_down"));
                            endingModel.setUserId(spoilerObject.optInt("user_id"));
                            endingModel.setUserName(spoilerObject.optString("display_name"));
                            endingModel.setUserPhotoUrl(spoilerObject.optString("avatar_url"));
                            endingModel.setUserMail(spoilerObject.optString("user_login"));
//                        endingModel.setUserPhone(spoilerObject.optString("phone"));
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
