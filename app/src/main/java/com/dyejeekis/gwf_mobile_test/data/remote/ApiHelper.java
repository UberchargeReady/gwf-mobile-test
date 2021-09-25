package com.dyejeekis.gwf_mobile_test.data.remote;

import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesResponse;

public interface ApiHelper {

    Result<LoginResponse> postLogin(LoginRequest request);

    Result<RefreshResponse> postRefresh(RefreshRequest request);

    Result<MeterResponse> getMeters(MeterRequest request);

    Result<TotalValuesResponse> getTotalValues(TotalValuesRequest request);
}
