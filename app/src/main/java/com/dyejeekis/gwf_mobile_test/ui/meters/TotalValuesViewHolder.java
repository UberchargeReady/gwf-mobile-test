package com.dyejeekis.gwf_mobile_test.ui.meters;

import androidx.recyclerview.widget.RecyclerView;

import com.dyejeekis.gwf_mobile_test.data.model.TotalValues;
import com.dyejeekis.gwf_mobile_test.databinding.TotalValuesViewBinding;

public class TotalValuesViewHolder extends RecyclerView.ViewHolder {

    private final TotalValuesViewBinding binding;

    public TotalValuesViewHolder(TotalValuesViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindItem(TotalValues totalValues) {
        binding.textViewMeters.setText("Meters: " + totalValues.getMeters());
        binding.textViewVolume.setText("Volume: " + totalValues.getVolume());
        binding.textViewErrors.setText("Errors: " + totalValues.getErrors());
        binding.textViewReadouts.setText("Readouts: " + totalValues.getReadouts());
    }
}
