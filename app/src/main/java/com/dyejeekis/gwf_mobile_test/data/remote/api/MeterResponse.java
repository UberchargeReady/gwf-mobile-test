package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.data.model.MeterState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MeterResponse extends Response {

    private final List<Meter> meters = new ArrayList<>();

    public MeterResponse(String json) throws JSONException {
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) throws JSONException{
        Object obj = new JSONTokener(json).nextValue();
        JSONArray jsonArray = (JSONArray) obj;
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Meter meter = new Meter(
                    (float) jsonObject.getDouble("lat"),
                    (float) jsonObject.getDouble("lng"),
                    jsonObject.getString("mp_name"),
                    jsonObject.getString("meter_id"),
                    jsonObject.getString("meter_type"),
                    jsonObject.getString("last_entry"),
                    (float) jsonObject.getDouble("volume"),
                    parseMeterState(jsonObject.getJSONObject("state")));
            meters.add(meter);
        }
    }

    private MeterState parseMeterState(JSONObject jsonObject) throws JSONException {
        return new MeterState(
                jsonObject.getBoolean("us_water_level"),
                jsonObject.getBoolean("v_sensor_comm_timout"),
                jsonObject.getBoolean("water_level_error"),
                jsonObject.getBoolean("t_air_error"),
                jsonObject.getBoolean("t_water_error"),
                jsonObject.getBoolean("w_air_error"),
                jsonObject.getBoolean("w_water_error"),
                jsonObject.getBoolean("velocity_error"),
                jsonObject.getBoolean("system_error"));
    }

    public List<Meter> getMeters() {
        return meters;
    }
}
