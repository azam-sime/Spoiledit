package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.ProfileModel;
import com.spoiledit.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class UserParser extends AsyncTask<String, Void, UserModel> {
    @Override
    protected UserModel doInBackground(String... strings) {
        UserModel userModel = new UserModel();
        try {
            JSONObject jsonObject = new JSONObject(strings[0]);

            JSONObject dataObject = jsonObject.optJSONObject("data");
            userModel.setId(dataObject.optInt("ID"));
            userModel.setLogin(dataObject.optString("user_login"));
            userModel.setPassword(dataObject.optString("user_pass"));
            userModel.setNiceName(dataObject.optString("user_nicename"));
            userModel.setEmail(dataObject.optString("user_email"));
            userModel.setPhone(dataObject.optString("user_phone"));
            userModel.setUrl(dataObject.optString("user_url"));
            userModel.setRegistered(dataObject.optString("user_registered"));
            userModel.setActivationKey(dataObject.optString("user_activation_key"));
            userModel.setStatus(dataObject.optString("user_status"));
            userModel.setDisplayName(dataObject.optString("display_name"));

            JSONObject capsObject = jsonObject.optJSONObject("caps");
            for (Iterator<String> iterator = capsObject.keys(); iterator.hasNext(); ) {
                String key = iterator.next();
                if (capsObject.optBoolean(key))
                    userModel.setCapKey(key);
            }

            JSONArray rolesArray = jsonObject.optJSONArray("roles");
            int l = rolesArray.length();
            String[] roles = new String[l];
            for (int i = 0; i < l; i++) {
                roles[i] = rolesArray.optString(i);
            }
            userModel.setRoles(roles);

            JSONObject allcapsObject = jsonObject.optJSONObject("allcaps");

            JSONObject filterObject = jsonObject.optJSONObject("filter");

            return userModel;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final class ProfileParser extends AsyncTask<JSONObject, Void, ProfileModel> {

        @Override
        protected ProfileModel doInBackground(JSONObject... jsonObjects) {
            return null;
        }
    }
}
