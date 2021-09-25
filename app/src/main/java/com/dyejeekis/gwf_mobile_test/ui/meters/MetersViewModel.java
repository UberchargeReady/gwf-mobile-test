package com.dyejeekis.gwf_mobile_test.ui.meters;

import androidx.lifecycle.MutableLiveData;

import com.dyejeekis.gwf_mobile_test.data.model.Entity;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.AppApiHelper;
import com.dyejeekis.gwf_mobile_test.data.remote.Result;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterRequest;
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterResponse;
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
            //loadData();
        }
        return mutableLiveData;
    }

    public void loadData() {
        if (getUser().isLoggedIn()) {
            MeterRequest meterRequest = new MeterRequest();
            TotalValuesRequest totalsRequest = new TotalValuesRequest();
            getExecutor().execute(() -> {
                Result<MeterResponse> meterResult = apiHelper.getMeters(meterRequest);
                Result<TotalValuesResponse> totalsResult = apiHelper.getTotalValues(totalsRequest);
                if (meterResult instanceof Result.Success && totalsResult instanceof Result.Success) {
                    List<Entity> items = new ArrayList<>();
                    items.addAll(((Result.Success<MeterResponse>) meterResult).data.getMeters());
                    items.add(((Result.Success<TotalValuesResponse>) totalsResult).data.getTotalValues());
                    mutableLiveData.postValue(items);
                } else mutableLiveData.postValue(null);
            });
        } else throw new NetworkUtil.NotAuthenticatedException();
    }
}