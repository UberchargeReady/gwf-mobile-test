package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.view.View;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;

public interface MeterListener {

    View.OnClickListener onMeterClick(Meter meter);
}
