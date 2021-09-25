package com.dyejeekis.gwf_mobile_test.data.remote;

import android.content.Context;
import android.os.Handler;

public class AppApiCallback<Response> implements ApiCallback<Response> {

    private final Context context;
    private final ApiCallback<Response> callback;

    public AppApiCallback(Context context, ApiCallback<Response> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void onComplete(Result<Response> result) {
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.post(() -> {
            callback.onComplete(result);
        });
    }
}
