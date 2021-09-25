package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;

import java.util.ArrayList;
import java.util.List;

public class MeterResponse extends Response {

    private final List<Meter> meters = new ArrayList<>();

    public MeterResponse(String json) {
        parseResponse(json);
    }

    @Override
    protected void parseResponse(String json) {
        // TODO: 9/24/2021
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public Meter getMeter() {
        return meters.get(0);
    }
}
