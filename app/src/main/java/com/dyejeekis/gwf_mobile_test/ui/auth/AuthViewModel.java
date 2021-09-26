package com.dyejeekis.gwf_mobile_test.ui.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dyejeekis.gwf_mobile_test.MyApp;
import com.dyejeekis.gwf_mobile_test.data.model.User;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiCallback;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.AppApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.Result;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.RefreshResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.Response;
import com.dyejeekis.gwf_mobile_test.ui.BaseViewModel;
import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;

import java.util.Locale;

public class AuthViewModel extends BaseViewModel {

    private final ApiHelper apiHelper = new AppApiHelper();

    private MutableLiveData<User> userMutable;

    public MutableLiveData<User> getUserMutable() {
        if (userMutable == null) {
            userMutable = new MutableLiveData<>();
            userMutable.postValue(getUser());
        }
        return userMutable;
    }

    public void makeLoginRequest(@NonNull String username, @NonNull String password,
                                 ApiCallback<LoginResponse> callback) {
        LoginRequest request = new LoginRequest(username, password);
        getExecutor().execute(() -> {
            Result<LoginResponse> result = apiHelper.postLogin(request);
            if (result instanceof Result.Success) {
                LoginResponse response = ((Result.Success<LoginResponse>) result).data;
                User user = new User(username.toLowerCase(Locale.ROOT),
                        response.getAccessToken(), response.getRefreshToken());
                saveUser(user);
                userMutable.postValue(user);
            }
            callback.onComplete(result);
        });
    }

    public void makeRefreshRequest(ApiCallback<RefreshResponse> callback) {
        if (getUser().isLoggedIn()) {
            if (getUser().isRefreshTokenValid()) {
                RefreshRequest request = new RefreshRequest(getUser().getRefreshToken());
                getExecutor().execute(() -> {
                    Result<RefreshResponse> result = apiHelper.postRefresh(request);
                    if (result instanceof Result.Success) {
                        String accessToken = ((Result.Success<RefreshResponse>) result)
                                .data.getAccessToken();
                        User user = getUser();
                        user.setAccessToken(accessToken);
                        saveUser(user);
                        userMutable.postValue(user);
                    }
                    callback.onComplete(result);
                });
            } else {
                throw new NetworkUtil.RefreshTokenExpiredException();
            }
        } else throw new NetworkUtil.NotAuthenticatedException();
    }

    public void makeLogoutRequest(ApiCallback<Response> callback) {
        getExecutor().execute(() -> {
            User user = new User();
            saveUser(user);
            userMutable.postValue(user);
            callback.onComplete(null);
        });
    }
}