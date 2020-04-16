package com.spoiledit.parsers;

import android.os.AsyncTask;

import com.spoiledit.models.ProfileModel;
import com.spoiledit.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class UserParser {

    public static class LoginParser extends AsyncTask<String, Void, UserModel> {
        @Override
        protected UserModel doInBackground(String... strings) {
            try {
                JSONObject resultObject = new JSONObject(strings[0]).optJSONObject("results");
                UserModel userModel = parseDetailsObject(resultObject.optJSONObject("data"));
                parseCapsObject(userModel, resultObject.optJSONObject("caps"));
                parseRolesArray(userModel, resultObject.optJSONArray("roles"));
                parseAllCapsObject(userModel, resultObject.optJSONObject("allcaps"));
                parseFilterObject(userModel, resultObject.optJSONObject("filter"));

                return userModel;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class VerificationParser extends AsyncTask<JSONObject, Void, UserModel> {
        @Override
        protected UserModel doInBackground(JSONObject... jsonObjects) {
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                UserModel userModel = parseDetailsObject(dataObject.optJSONObject("data"));
                parseCapsObject(userModel, dataObject.optJSONObject("caps"));
                parseRolesArray(userModel, dataObject.optJSONArray("roles"));
                parseAllCapsObject(userModel, dataObject.optJSONObject("allcaps"));
                parseFilterObject(userModel, dataObject.optJSONObject("filter"));

                return userModel;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static final class EditParser extends AsyncTask<JSONObject, Void, UserModel> {

        @Override
        protected UserModel doInBackground(JSONObject... jsonObjects) {
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                UserModel userModel = parseDetailsObject(dataObject.optJSONObject("data"));
                parseCapsObject(userModel, dataObject.optJSONObject("caps"));
                parseRolesArray(userModel, dataObject.optJSONArray("roles"));
                parseAllCapsObject(userModel, dataObject.optJSONObject("allcaps"));
                parseFilterObject(userModel, dataObject.optJSONObject("filter"));

                return userModel;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static final class DetailsParser extends AsyncTask<JSONObject, Void, ProfileModel> {

        @Override
        protected ProfileModel doInBackground(JSONObject... jsonObjects) {
            try {
                JSONObject dataObject = jsonObjects[0].optJSONObject("data");
                UserModel userModel = parseDetailsObject(dataObject.optJSONObject("data"));
                parseCapsObject(userModel, dataObject.optJSONObject("caps"));
                parseRolesArray(userModel, dataObject.optJSONArray("roles"));
                parseAllCapsObject(userModel, dataObject.optJSONObject("allcaps"));
                parseFilterObject(userModel, dataObject.optJSONObject("filter"));

                return ProfileModel.fromUserModel(userModel);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static UserModel parseDetailsObject(JSONObject jsonObject) {
        UserModel userModel = new UserModel();

        if (jsonObject != null) {
            userModel.setId(jsonObject.optInt("ID"));
            userModel.setLogin(jsonObject.optString("user_login"));
            userModel.setPassword(jsonObject.optString("user_pass"));
            userModel.setNiceName(jsonObject.optString("user_nicename"));
            userModel.setEmail(jsonObject.optString("user_email"));
            userModel.setUrl(jsonObject.optString("user_url"));
            userModel.setRegistered(jsonObject.optString("user_registered"));
            userModel.setActivationKey(jsonObject.optString("user_activation_key"));
            userModel.setStatus(jsonObject.optString("user_status"));
            userModel.setDisplayName(jsonObject.optString("display_name"));
//            userModel.setUsername(dataObject.optString("username"));
            userModel.setPhone(jsonObject.has("phone") ?
                    jsonObject.optString("phone") : jsonObject.optString("phone_number"));
        }

        return userModel;
    }

    private static void parseCapsObject(UserModel userModel, JSONObject capsObject) {
        if (capsObject != null) {
            for (Iterator<String> iterator = capsObject.keys(); iterator.hasNext(); ) {
                String key = iterator.next();
                if (capsObject.optBoolean(key))
                    userModel.setCapKey(key);
            }
        }
    }

    private static void parseRolesArray(UserModel userModel, JSONArray rolesArray) {
        if (rolesArray != null) {
            int l = rolesArray.length();
            String[] roles = new String[l];
            for (int i = 0; i < l; i++) {
                roles[i] = rolesArray.optString(i);
            }
            userModel.setRoles(roles);
        }
    }

    private static void parseAllCapsObject(UserModel userModel, JSONObject allCapsObject) {

    }

    private static void parseFilterObject(UserModel userModel, JSONObject filterObject) {

    }
}
