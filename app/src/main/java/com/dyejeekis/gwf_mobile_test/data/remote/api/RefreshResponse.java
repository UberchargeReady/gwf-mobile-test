package com.dyejeekis.gwf_mobile_test.data.remote.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RefreshResponse extends Response {

    private String accessToken;

    public RefreshResponse(String json) throws JSONException {
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) throws JSONException {
        Object obj = new JSONTokener(json).nextValue();
        JSONObject jsonObject = (JSONObject) obj;
        accessToken = jsonObject.getString("access");
    }

    public String getAccessToken() {
        return accessToken;
    }
}
