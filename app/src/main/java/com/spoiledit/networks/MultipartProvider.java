package com.spoiledit.networks;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.spoiledit.constants.Type;
import com.spoiledit.utils.LogUtils;
import com.spoiledit.utils.NetworkUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;


public class MultipartProvider extends Request<String> {
    public static final String TAG = MultipartProvider.class.getCanonicalName();

    private HashMap<String, String> params;
    private int callFor;
    private boolean addAuthHeaders;
    private List<File> files;
    private Response.Listener<String> stringListener;
    private Response.ErrorListener errorListener;

    private HttpEntity httpEntity;

    public MultipartProvider(String serverUrl, HashMap<String, String> params, int callFor, boolean addAuthHeaders, List<File> files, Response.Listener<String> stringListener, Response.ErrorListener errorListener) {
        super(Method.POST, serverUrl, errorListener);

        this.params = params;
        this.callFor = callFor;
        this.addAuthHeaders = addAuthHeaders;
        this.files = files;
        this.stringListener = stringListener;
        this.errorListener = errorListener;

        decideUploadCallback();
    }

    private void decideUploadCallback() {
        if (callFor == Type.Multipart.USER_PHOTO) {
            httpEntity = buildUserPhotoEntity();
        }
    }

    private HttpEntity buildUserPhotoEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (int i = 0; i < files.size(); i++) {
            FileBody fileBody = new FileBody(files.get(i));
            builder.addPart("key", fileBody);
        }
        builder.addTextBody("key", params.get("value_key"));

        return builder.build();
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(NetworkUtils.RETRY_POLICY);
    }

    @Override
    public Request<?> setTag(Object tag) {
        return super.setTag(TAG);
    }

    @Override
    public String getBodyContentType() {
        return httpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpEntity.writeTo(bos);
            return bos.toByteArray();
        } catch (IOException | OutOfMemoryError e) {
            VolleyLog.e("" + e);
            return null;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (addAuthHeaders) {
            Map<String, String> headers = new HashMap<>();

            NetworkUtils.addAuthorization(headers);

            return headers;
        } else
            return super.getHeaders();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new String(response.data, StandardCharsets.UTF_8), getCacheEntry());
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(String response) {
        LogUtils.logResponse(TAG, response);
        stringListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        LogUtils.logError(TAG, error);
        errorListener.onErrorResponse(error);
    }
}