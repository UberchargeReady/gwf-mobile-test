package com.dyejeekis.gwf_mobile_test.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    public static String get(String url, String bearerToken) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        //Log.d("OkHttp", "Response body: " + responseStr);
        return responseStr;
    }

    public static String post(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        //Log.d("OkHttp", "Response body: " + responseStr);
        return responseStr;
    }
}
