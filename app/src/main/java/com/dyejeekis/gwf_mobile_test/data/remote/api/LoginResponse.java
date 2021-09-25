package com.dyejeekis.gwf_mobile_test.data.remote.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginResponse extends Response {

    private String refreshToken, accessToken;

    public LoginResponse(String json) throws JSONException {
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) throws JSONException {
        Object obj = new JSONTokener(json).nextValue();
        JSONObject jsonObject = (JSONObject) obj;
        refreshToken = jsonObject.getString("refresh");
        accessToken = jsonObject.getString("access");
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
