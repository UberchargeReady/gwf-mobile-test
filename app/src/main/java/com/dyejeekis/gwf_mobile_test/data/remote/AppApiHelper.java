package com.dyejeekis.gwf_mobile_test.data.remote;

import com.dyejeekis.gwf_mobile_test.MyApp;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesResponse;
import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;

public class AppApiHelper implements ApiHelper {

    @Override
    public Result<LoginResponse> postLogin(LoginRequest request) {
        try {
            String url = ApiEndpoint.BASE_URL + request.getPath();
            String jsonBody = NetworkUtil.post(url, request.getRequestBody());
            LoginResponse response = new LoginResponse(jsonBody);
            return new Result.Success<>(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error<>(e);
        }
    }

    @Override
    public Result<RefreshResponse> postRefresh(RefreshRequest request) {
        try {
            String url = ApiEndpoint.BASE_URL + request.getPath();
            String jsonBody = NetworkUtil.post(url, request.getRequestBody());
            RefreshResponse response = new RefreshResponse(jsonBody);
            return new Result.Success<>(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error<>(e);
        }
    }

    @Override
    public Result<MeterResponse> getMeters(MeterRequest request) {
        try {
            String url = ApiEndpoint.BASE_URL + request.getPath();
            String accessToken = MyApp.getInstance().getCurrentUser().getAccessToken();
            String jsonBody = NetworkUtil.get(url, accessToken);
            MeterResponse response = new MeterResponse(jsonBody);
            return new Result.Success<>(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error<>(e);
        }
    }

    @Override
    public Result<TotalValuesResponse> getTotalValues(TotalValuesRequest request) {
        try {
            String url = ApiEndpoint.BASE_URL + request.getPath();
            String accessToken = MyApp.getInstance().getCurrentUser().getAccessToken();
            String jsonBody = NetworkUtil.get(url, accessToken);
            TotalValuesResponse response = new TotalValuesResponse(jsonBody);
            return new Result.Success<>(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error<>(e);
        }
    }
}
