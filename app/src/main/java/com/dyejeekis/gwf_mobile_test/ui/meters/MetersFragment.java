package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dyejeekis.gwf_mobile_test.databinding.FragmentMetersBinding;

public class MetersFragment extends Fragment {

    private MetersViewModel metersViewModel;
    private FragmentMetersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        metersViewModel =
                new ViewModelProvider(this).get(MetersViewModel.class);

        binding = FragmentMetersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}