package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.data.model.MeterState;
import com.dyejeekis.gwf_mobile_test.databinding.MeterViewBinding;

public class MeterViewHolder extends RecyclerView.ViewHolder {

    private final MeterViewBinding binding;

    public MeterViewHolder(MeterViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindItem(Meter meter, MeterListener listener) {
        binding.layoutMeter.setOnClickListener(listener.onMeterClick(meter));
        binding.textViewName.setText("Name: " + meter.getName());
        binding.textViewId.setText("ID: " + meter.getId());
        binding.textViewType.setText("Type: " + meter.getType());
        String lat = meter.getLatitude() == -1 ? "null" : String.valueOf(meter.getLatitude());
        String lng = meter.getLongitude() == -1 ? "null" : String.valueOf(meter.getLongitude());
        binding.textViewLat.setText("Latitude: " + lat);
        binding.textViewLng.setText("Longitude: " + lng);
        binding.textViewVolume.setText("Volume: " + meter.getVolume());
        binding.textViewLastEntry.setText("Last entry: " + meter.getLastEntry());

        binding.textViewCommModType.setText("Comm mod type: " + meter.getCommModType());
        binding.textViewCommModSerial.setText("Comm mod serial: " + meter.getCommModSerial());
        binding.textViewBatteryLifetime.setText("Battery lifetime: " + meter.getBatteryLifetime());

        if (meter.getType().equalsIgnoreCase("gwfencoder")) {
            binding.textViewCommModType.setVisibility(View.VISIBLE);
            binding.textViewCommModSerial.setVisibility(View.VISIBLE);
            binding.textViewBatteryLifetime.setVisibility(View.VISIBLE);
        } else {
            binding.textViewCommModType.setVisibility(View.GONE);
            binding.textViewCommModSerial.setVisibility(View.GONE);
            binding.textViewBatteryLifetime.setVisibility(View.GONE);
        }

        MeterState state = meter.getState();
        String stateStr = "State: ";
        if (state.isUsWaterLevel()) stateStr += "us_water_level, ";
        if (state.isvSensorCommTimeout()) stateStr += "v_sensor_comm_timeout, ";
        if (state.isWaterLevelError()) stateStr += "water_level_error, ";
        if (state.istAirError()) stateStr += "t_air_error, ";
        if (state.istWaterError()) stateStr += "t_water_error, ";
        if (state.iswAirError()) stateStr += "w_air_error, ";
        if (state.iswWaterError()) stateStr += "w_water_error, ";
        if (state.isVelocityError()) stateStr += "velocity_error, ";
        if (state.isSystemError()) stateStr += "system_error, ";
        if (state.isBatteryLow()) stateStr += "battery_low, ";
        if (state.isCommunicationError()) stateStr += "communication_error, ";
        if (state.isParsingError()) stateStr += "parsing_error, ";
        if (state.isEncoderError()) stateStr += "encoder_error, ";
        binding.textViewState.setText(stateStr);
    }
}
