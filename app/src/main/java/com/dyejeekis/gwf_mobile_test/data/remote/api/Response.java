package com.dyejeekis.gwf_mobile_test.data.remote.api;

import org.json.JSONException;

public abstract class Response {

    protected abstract void parseResponse(String json) throws JSONException;
}
