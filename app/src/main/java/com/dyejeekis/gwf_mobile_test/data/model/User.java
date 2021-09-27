package com.dyejeekis.gwf_mobile_test.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

//    public static final long ACCESS_TOKEN_DURATION = 15 * 60 * 1000;
//    public static final long REFRESH_TOKEN_DURATION = 60 * 60 * 1000;

    private final String username;
    private String accessToken, refreshToken;
//    private long accessTokenMillis, refreshTokenMillis;

    public User() {
        username = null;
        accessToken = null;
        refreshToken = null;
    }

    public User(@NonNull String username, @NonNull String accessToken, @NonNull String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
//        this.accessTokenMillis = System.currentTimeMillis();
//        this.refreshTokenMillis = System.currentTimeMillis();
    }

    public boolean isLoggedIn() {
        return username != null && accessToken != null && refreshToken != null;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

//    public boolean isAccessTokenValid() {
//        return accessToken != null &&
//                System.currentTimeMillis() - accessTokenMillis < ACCESS_TOKEN_DURATION;
//    }
//
//    public boolean isRefreshTokenValid() {
//        return refreshToken != null &&
//                System.currentTimeMillis() - refreshTokenMillis < REFRESH_TOKEN_DURATION;
//    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
//        this.accessTokenMillis = System.currentTimeMillis();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
//        this.refreshTokenMillis = System.currentTimeMillis();
    }
}
