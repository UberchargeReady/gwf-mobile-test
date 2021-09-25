package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.databinding.MeterViewBinding;

public class MeterViewHolder extends RecyclerView.ViewHolder {

    private final MeterViewBinding binding;

    public MeterViewHolder(MeterViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public MeterViewBinding getBinding() {
        return binding;
    }

    public void bindItem(Meter meter, View.OnClickListener listener) {
        // TODO: 9/25/2021
    }
}
