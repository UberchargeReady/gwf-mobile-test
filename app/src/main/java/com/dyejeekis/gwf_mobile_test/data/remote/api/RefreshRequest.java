package com.dyejeekis.gwf_mobile_test.data.remote.api;

import androidx.annotation.NonNull;

import com.dyejeekis.gwf_mobile_test.data.remote.ApiEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

public class RefreshRequest extends Request {

    private final String refreshToken;

    public RefreshRequest(@NonNull String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String getPath() {
        return ApiEndpoint.REFRESH;
    }

    public String getRequestBody() throws JSONException {
        return new JSONObject()
                .put("refresh", refreshToken)
                .toString();
    }
}
