package com.spoiledit.networks;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyProvider {
    private static final String TAG = VolleyProvider.class.getCanonicalName();

    public interface OnResponseListener<T> {
        void onSuccess(T response);

        void onFailure(VolleyError volleyError);
    }

    private static VolleyProvider volleyProvider;
    private RequestQueue requestQueue;

    private VolleyProvider(Context context) {
        requestQueue = new RequestQueue(new DiskBasedCache(context.getCacheDir(),
                NetworkUtils.CACHE_SIZE_MAX), new BasicNetwork(new HurlStack()));
        requestQueue.start();
    }

    public static synchronized VolleyProvider getProvider(Context context) {
        if (volleyProvider == null) {
            volleyProvider = new VolleyProvider(context.getApplicationContext());
        }
        return volleyProvider;
    }

    public void addToRequestQueue(MultipartProvider multipartProvider) {
        requestQueue.add(multipartProvider);
    }



    public void executePostRequest(String url, JSONArray params, OnResponseListener<JSONArray> onResponseListener, boolean cache, boolean hasAuth) {
        LogUtils.logInfo(TAG,"executePostJsonArrayRequest: ");

        if (NetworkUtils.isNetworkAvailable()) {
            try {
                requestQueue.getCache().remove(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.logRequest(TAG, url + ":" + params.toString());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, params,
                response -> {
                    LogUtils.logResponse(TAG, response.toString());
                    if (onResponseListener != null)
                        onResponseListener.onSuccess(response);
                },
                error -> {
                    LogUtils.logError(TAG, error);
                    if (onResponseListener != null)
                        onResponseListener.onFailure(error);
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                NetworkUtils.addHeaders(headers, hasAuth);

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
                return NetworkUtils.parseArray(networkResponse);
            }
        };

        request.setShouldCache(cache);
        request.setRetryPolicy(NetworkUtils.RETRY_POLICY);
        request.setTag(TAG);
        requestQueue.add(request);
    }



    public void executePostRequest(String url, JSONObject params, OnResponseListener<JSONObject> onResponseListener, boolean cache, boolean hasAuth) {
        LogUtils.logInfo(TAG, "executePostJsonRequest: ");

        if (NetworkUtils.isNetworkAvailable()) {
            try {
                requestQueue.getCache().remove(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.logRequest(TAG, url + ":" + params.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    LogUtils.logResponse(TAG, response.toString());
                    if (onResponseListener != null)
                        onResponseListener.onSuccess(response);
                },
                error -> {
                    LogUtils.logError(TAG, error);
                    if (onResponseListener != null)
                        onResponseListener.onFailure(error);
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                NetworkUtils.addHeaders(headers, hasAuth);

                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
                return NetworkUtils.parseObject(networkResponse);
            }
        };

        request.setShouldCache(cache);
        request.setRetryPolicy(NetworkUtils.RETRY_POLICY);
        request.setTag(TAG);
        requestQueue.add(request);
    }



    public void executePostRequest(String url, Map<String, String> params, OnResponseListener<String> onResponseListener, boolean cache, boolean hasAuth) {
        LogUtils.logInfo(TAG, "executePostStringRequest: ");
        if (NetworkUtils.isNetworkAvailable()) {
            try {
                requestQueue.getCache().remove(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.logRequest(TAG, url + ":" + params.toString());
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    LogUtils.logResponse(TAG, response);
                    if (onResponseListener != null)
                        onResponseListener.onSuccess(response);
                },
                error -> {
                    LogUtils.logError(TAG, error);
                    if (onResponseListener != null)
                        onResponseListener.onFailure(error);
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                NetworkUtils.addHeaders(headers, hasAuth);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        request.setShouldCache(cache);
        request.setRetryPolicy(NetworkUtils.RETRY_POLICY);
        request.setTag(TAG);
        requestQueue.add(request);
    }



    public void executeGetRequest(String url, OnResponseListener<JSONObject> onResponseListener, boolean cache, boolean hasAuth) {
        LogUtils.logInfo(TAG, "executeGetRequest: ");

        if (NetworkUtils.isNetworkAvailable()) {
            try {
                requestQueue.getCache().remove(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.logRequest(TAG, url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    LogUtils.logResponse(TAG, response);
                    if (onResponseListener != null) {
                        try {
                            onResponseListener.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> LogUtils.logError(TAG, error))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                NetworkUtils.addHeaders(headers, hasAuth);

                return headers;
            }
        };

        request.setShouldCache(cache);
        request.setRetryPolicy(NetworkUtils.RETRY_POLICY);
        request.setTag(TAG);
        requestQueue.add(request);
    }

    public void cancelRequest(Object tag) {
        requestQueue.cancelAll(tag);
    }
}
