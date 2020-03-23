package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.CommentModel;
import com.spoiledit.models.MovieDetailsModel;
import com.spoiledit.models.MoviePopularModel;
import com.spoiledit.models.MovieRecentModel;
import com.spoiledit.models.MovieUpcomingModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommentParser extends AsyncTask<JSONObject, Void, List<CommentModel>> {
    public static final String TAG = CommentParser.class.getCanonicalName();

    @Override
    protected List<CommentModel> doInBackground(JSONObject... jsonObjects) {
        List<CommentModel> commentModels = new ArrayList<>();

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentModels;
    }
}
