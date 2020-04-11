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
            JSONArray resultArray = jsonObjects[0].optJSONArray("result_data");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.optJSONObject(i);
                CommentModel commentModel = new CommentModel();

                commentModel.setId(jsonObject.optInt("comment_id"));
                commentModel.setUserId(jsonObject.optInt("comment_sender_name"));
                commentModel.setMovieId(jsonObject.optInt("m_id"));
                commentModel.setSpoilerId(jsonObject.optInt("sp_spoiler_id"));
                commentModel.setParentCommentId(jsonObject.optInt("parent_comment_id"));
                commentModel.setComment(jsonObject.optString("comment"));
                commentModel.setCommentDate(jsonObject.optString("date"));
                commentModel.setUserName(jsonObject.optString("username"));
                commentModel.setAvatarUrl(jsonObject.optString("user-image"));
                commentModel.setLikes(jsonObject.optInt("likes"));
                commentModel.setDislikes(jsonObject.optInt("dislikes"));

                commentModels.add(commentModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentModels;
    }
}
