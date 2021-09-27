package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.data.model.MeterState;
import com.dyejeekis.gwf_mobile_test.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MeterResponse extends DataResponse {

    private final List<Meter> meters = new ArrayList<>();

    public MeterResponse(String json) throws JSONException {
        super(json);
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) throws JSONException{
        Object obj = new JSONTokener(json).nextValue();
        JSONArray jsonArray = (JSONArray) obj;
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Meter meter = new Meter(
                    (float) Util.safeJsonToDouble(jsonObject, "lat", 404),
                    (float) Util.safeJsonToDouble(jsonObject, "lng", 404),
                    (float) Util.safeJsonToDouble(jsonObject, "volume", 0),
                    Util.safeJsonToString(jsonObject, "mp_name"),
                    Util.safeJsonToString(jsonObject, "meter_id"),
                    Util.safeJsonToString(jsonObject, "meter_type"),
                    Util.safeJsonToString(jsonObject, "last_entry"),
                    Util.safeJsonToString(jsonObject, "comm_mod_type"),
                    Util.safeJsonToString(jsonObject, "comm_mod_serial"),
                    Util.safeJsonToInteger(jsonObject, "battery_lifetime"),
                    parseMeterState(jsonObject.getJSONObject("state")));
            meters.add(meter);
        }
    }

    private MeterState parseMeterState(JSONObject jsonObject) throws JSONException {
        return new MeterState(
                Util.safeJsonToBoolean(jsonObject, "us_water_level"),
                Util.safeJsonToBoolean(jsonObject, "v_sensor_comm_timeout"),
                Util.safeJsonToBoolean(jsonObject, "water_level_error"),
                Util.safeJsonToBoolean(jsonObject, "t_air_error"),
                Util.safeJsonToBoolean(jsonObject, "t_water_error"),
                Util.safeJsonToBoolean(jsonObject, "w_air_error"),
                Util.safeJsonToBoolean(jsonObject, "w_water_error"),
                Util.safeJsonToBoolean(jsonObject, "velocity_error"),
                Util.safeJsonToBoolean(jsonObject, "system_error"),
                Util.safeJsonToBoolean(jsonObject, "battery_low"),
                Util.safeJsonToBoolean(jsonObject, "communication_error"),
                Util.safeJsonToBoolean(jsonObject, "parsing_error"),
                Util.safeJsonToBoolean(jsonObject, "encoder_error"));
    }

    public List<Meter> getMeters() {
        return meters;
    }
}
