package com.dyejeekis.gwf_mobile_test.ui.meters;

import androidx.lifecycle.MutableLiveData;

import com.dyejeekis.gwf_mobile_test.data.model.Entity;
import com.dyejeekis.gwf_mobile_test.data.model.TotalValues;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiCallback;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.AppApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.Result;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterResponse;
import com.dyejeekis.gwf_mobile_test.data.remote.api.Response;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.TotalValuesResponse;
import com.dyejeekis.gwf_mobile_test.ui.BaseViewModel;
import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class MetersViewModel extends BaseViewModel {

    private final ApiHelper apiHelper = new AppApiHelper();

    private MutableLiveData<List<Entity>> mutableLiveData;

    public MutableLiveData<List<Entity>> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            try {
                loadData(null);
            } catch (Exception e) {}
        }
        return mutableLiveData;
    }

    public void loadData(ApiCallback<Response> callback) {
        if (getUser().isLoggedIn()) {
            getExecutor().execute(() -> {
                Result<MeterResponse> meterResult = apiHelper.getMeters(new MeterRequest());
                if (meterResult instanceof Result.Success) {
                    List<Entity> items = new ArrayList<>();
                    if (mutableLiveData.getValue() != null && mutableLiveData.getValue().size() >= 1
                            && mutableLiveData.getValue().get(0) instanceof TotalValues) {
                        items.add(mutableLiveData.getValue().get(0));
                    }
                    items.addAll(((Result.Success<MeterResponse>) meterResult).data.getMeters());
                    mutableLiveData.postValue(items);
                } else mutableLiveData.postValue(null);
                if (callback != null) callback.onComplete(null);
            });
            //getExecutor().execute(() -> {
            //    try {
            //        Thread.sleep(2000);
            //    } catch (Exception e) {
            //        e.printStackTrace();
            //    }
            //    Result<TotalValuesResponse> totalsResult = apiHelper.getTotalValues(new TotalValuesRequest());
            //    if (totalsResult instanceof Result.Success) {
            //        List<Entity> items = new ArrayList<>();
            //        items.add(((Result.Success<TotalValuesResponse>) totalsResult).data.getTotalValues());
            //        if (mutableLiveData.getValue() != null)
            //            items.addAll(mutableLiveData.getValue());
            //        mutableLiveData.postValue(items);
            //    }
            //});
        } else throw new NetworkUtil.NotAuthenticatedException();
    }
}