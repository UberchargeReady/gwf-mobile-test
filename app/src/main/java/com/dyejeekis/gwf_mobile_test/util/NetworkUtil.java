package com.dyejeekis.gwf_mobile_test.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .build();

    public static String get(String url, String bearerToken) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        Log.d("OkHttp", "Response body: " + responseStr);
        return responseStr;
    }

    public static String post(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        //Log.d("OkHttp", "Request body: " + jsonBody);
        Log.d("OkHttp", "Response body: " + responseStr);
        return responseStr;
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("OkHttp", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("OkHttp", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    public static class RefreshTokenExpiredException extends RuntimeException {

    }

    public static class NotAuthenticatedException extends RuntimeException {

    }
}
