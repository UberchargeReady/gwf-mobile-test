package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.data.remote.ApiEndpoint;

public class TotalValuesRequest extends Request {

    @Override
    public String getPath() {
        return ApiEndpoint.TOTAL_VALUES;
    }
}
