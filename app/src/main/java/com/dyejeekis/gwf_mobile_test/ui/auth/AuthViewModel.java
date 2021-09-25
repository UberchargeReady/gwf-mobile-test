package com.dyejeekis.gwf_mobile_test.ui.auth;

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
import com.dyejeekis.gwf_mobile_test.data.remote.api.Response;
import com.dyejeekis.gwf_mobile_test.ui.BaseViewModel;

import java.util.Locale;

public class AuthViewModel extends BaseViewModel {

    private final ApiHelper apiHelper = new AppApiHelper();

    private MutableLiveData<User> userMutable;

    public MutableLiveData<User> getUserMutable() {
        if (userMutable == null) {
            userMutable = new MutableLiveData<>();
            userMutable.postValue(MyApp.getInstance().getCurrentUser());
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

    public void makeLogoutRequest(ApiCallback<Response> callback) {
        getExecutor().execute(() -> {
            User user = new User();
            saveUser(user);
            userMutable.postValue(user);
            callback.onComplete(null);
        });
    }
}