package com.dyejeekis.gwf_mobile_test.data.remote.api;

import androidx.annotation.NonNull;

import com.dyejeekis.gwf_mobile_test.data.remote.ApiEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequest extends Request {

    private final String username, password;

    public LoginRequest(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getPath() {
        return ApiEndpoint.LOGIN;
    }

    public String getRequestBody() throws JSONException {
        return new JSONObject()
                .put("email", username)
                .put("password", password)
                .toString();
    }
}
