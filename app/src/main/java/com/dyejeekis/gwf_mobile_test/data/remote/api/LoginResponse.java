package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;

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
        try {
            String code = jsonObject.getString("code");
            if (code.equalsIgnoreCase("authentication_failed"))
                throw new NetworkUtil.IncorrectCredentialsException();
        } catch (Exception e) {}
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
