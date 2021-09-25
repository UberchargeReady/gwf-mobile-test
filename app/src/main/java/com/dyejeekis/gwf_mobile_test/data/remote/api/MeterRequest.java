package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.remote.ApiEndpoint;

public class MeterRequest extends Request {

    @Override
    public String getPath() {
        return ApiEndpoint.METERS;
    }
}
