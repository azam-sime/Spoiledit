package com.spoiledit.parsers;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchParser extends AsyncTask<JSONObject, Void, List<String>> {
    public static final String TAG = SearchParser.class.getCanonicalName();

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
