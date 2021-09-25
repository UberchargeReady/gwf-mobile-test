package com.dyejeekis.gwf_mobile_test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dyejeekis.gwf_mobile_test.data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    public static final String USER_FILE_KEY = "key.USER";
    public static final String USERNAME_KEY = "key.USERNAME";
    public static final String ACCESS_TOKEN_KEY = "key.ACCESS_TOKEN";
    public static final String REFRESH_TOKEN_KEY = "key.REFRESH_TOKEN";

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    // TODO: 9/24/2021 token millis, logout user if refresh token expired
    public User getCurrentUser() {
        if (currentUser == null) {
            SharedPreferences sharedPref = getSharedPreferences(USER_FILE_KEY, Context.MODE_PRIVATE);
            String username = sharedPref.getString(USERNAME_KEY, null);
            if (username == null) currentUser = new User();
            else {
                String accessToken = sharedPref.getString(ACCESS_TOKEN_KEY, null);
                String refreshToken = sharedPref.getString(REFRESH_TOKEN_KEY, null);
                currentUser = new User(username, accessToken, refreshToken);
            }
        }
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        SharedPreferences sharedPref = getSharedPreferences(USER_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERNAME_KEY, currentUser.getUsername());
        editor.putString(ACCESS_TOKEN_KEY, currentUser.getAccessToken());
        editor.putString(REFRESH_TOKEN_KEY, currentUser.getRefreshToken());
        editor.apply();
    }
}
