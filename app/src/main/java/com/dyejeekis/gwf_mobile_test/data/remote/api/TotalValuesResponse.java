package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.model.TotalValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TotalValuesResponse extends DataResponse {

    private TotalValues totalValues;

    public TotalValuesResponse(String json) throws JSONException {
        super(json);
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) throws JSONException {
        Object obj = new JSONTokener(json).nextValue();
        JSONObject jsonObject = (JSONObject) obj;
        totalValues = new TotalValues(
                jsonObject.getInt("meters"),
                (float) jsonObject.getDouble("volume"),
                jsonObject.getInt("errors"),
                jsonObject.getInt("readouts"));
    }

    public TotalValues getTotalValues() {
        return totalValues;
    }
}
