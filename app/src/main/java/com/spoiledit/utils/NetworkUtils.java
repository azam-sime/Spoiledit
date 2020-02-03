package com.spoiledit.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.spoiledit.R;
import com.spoiledit.constants.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getCanonicalName();

    public static final int CACHE_SIZE_MAX = 20 * 1024 * 1024; // 20 MB
    public static final int RETRY_TIMEOUT = 0; // 0 secs
    public static final int RETRY_NO_MAX = -1;
    public static final float RETRY_BACKOFF = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    public static RetryPolicy RETRY_POLICY
            = new DefaultRetryPolicy(RETRY_TIMEOUT, RETRY_NO_MAX, RETRY_BACKOFF);

    private NetworkUtils() {

    }

    public static boolean isNetworkAvailable() {
        boolean status = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) AppUtils.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
            status = activeNetworkInfo.isAvailable();
        return status;

    }

    public static boolean isNetworkAvailable(Activity activity, boolean showDialog) {
        boolean isNetworkAvailable = isNetworkAvailable();

        if (!isNetworkAvailable && showDialog) {
            DialogUtils.showFailure(activity, R.string.no_internet_connection);
        }

        return isNetworkAvailable;
    }

    public static void addAuthorization(Map<String, String> headers) {
        String username = PreferenceUtils.getString(AppUtils.context(), PreferenceUtils.KEY_USERNAME);
        String password = PreferenceUtils.getString(AppUtils.context(), PreferenceUtils.KEY_PASSWORD);
        String credentials = String.format("%s:%s", username, password);

        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
        LogUtils.logInfo(TAG, "BasicAuth: " + credentials + "/" + auth);
        headers.put("Authorization", auth);
    }

    public static void addHeaders(Map<String, String> headers, boolean addAuth) {
        headers.put("decode", "2");
        headers.put("g_zip", "2");
        headers.put("is_mobile", "1");

        if (addAuth)
            addAuthorization(headers);
    }

    public static Response<JSONArray> parseArray(NetworkResponse networkResponse) {
        try {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(networkResponse);
            return Response.success(new JSONArray(
                    parseNetworkResponse(cacheEntry, networkResponse)), cacheEntry);

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    public static Response<JSONObject> parseObject(NetworkResponse networkResponse) {
        try {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(networkResponse);
            return Response.success(new JSONObject(
                    parseNetworkResponse(cacheEntry, networkResponse)), cacheEntry);

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private static String parseNetworkResponse(Cache.Entry cacheEntry, NetworkResponse networkResponse) throws Exception {
        if (cacheEntry == null) {
            cacheEntry = new Cache.Entry();
        }
        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background_login
        final long cacheExpired = 5 * 24 * 60 * 60 * 1000; // in 5*24 hours this cache entry expires completely
        long now = System.currentTimeMillis();
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        cacheEntry.data = networkResponse.data;
        cacheEntry.softTtl = softExpire; // Represents refresh interval of cache
        cacheEntry.ttl = ttl; // Represents total duration(life-span) of cache before expiry

        String headerValue;

        headerValue = networkResponse.headers.get("Date");
        if (headerValue != null) {
            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        headerValue = networkResponse.headers.get("Last-Modified");
        if (headerValue != null) {
            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        cacheEntry.responseHeaders = networkResponse.headers;

        return new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
    }
}
