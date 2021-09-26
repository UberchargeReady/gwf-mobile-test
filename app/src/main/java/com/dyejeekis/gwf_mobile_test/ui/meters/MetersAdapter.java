package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dyejeekis.gwf_mobile_test.data.model.Entity;
import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.data.model.TotalValues;
import com.dyejeekis.gwf_mobile_test.databinding.MeterViewBinding;
import com.dyejeekis.gwf_mobile_test.databinding.TotalValuesViewBinding;

import java.util.List;

public class MetersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_METER = 0;
    public static final int VIEW_TYPE_TOTALS = 1;

    private List<Entity> items;
    private MeterListener meterListener;

    public MetersAdapter(List<Entity> items, MeterListener meterListener) {
        this.items = items;
        this.meterListener = meterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_METER) {
            MeterViewBinding meterBinding = MeterViewBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new MeterViewHolder(meterBinding);
        } else if (viewType == VIEW_TYPE_TOTALS) {
            TotalValuesViewBinding totalsBinding = TotalValuesViewBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new TotalValuesViewHolder(totalsBinding);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Entity item = items.get(position);
        if (holder instanceof MeterViewHolder) {
            ((MeterViewHolder) holder).bindItem((Meter) item, meterListener);
        } else if(holder instanceof TotalValuesViewHolder) {
            ((TotalValuesViewHolder) holder).bindItem((TotalValues) item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Meter)
            return VIEW_TYPE_METER;
        else if (items.get(position) instanceof TotalValues)
            return VIEW_TYPE_TOTALS;
        return super.getItemViewType(position);
    }
}
