package com.dyejeekis.gwf_mobile_test.data.remote;

public interface ApiCallback<T> {
    void onComplete(Result<T> result);
}
