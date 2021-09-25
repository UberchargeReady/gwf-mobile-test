package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dyejeekis.gwf_mobile_test.data.model.Entity;
import com.dyejeekis.gwf_mobile_test.data.model.User;
import com.dyejeekis.gwf_mobile_test.databinding.FragmentMetersBinding;
import com.dyejeekis.gwf_mobile_test.ui.auth.AuthViewModel;
import com.dyejeekis.gwf_mobile_test.util.Util;

import java.util.List;

public class MetersFragment extends Fragment implements View.OnClickListener {

    private AuthViewModel authViewModel;
    private MetersViewModel metersViewModel;
    private FragmentMetersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutable().observe(getViewLifecycleOwner(), this::onUserUpdated);
        metersViewModel = new ViewModelProvider(this).get(MetersViewModel.class);
        metersViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), this::onMetersUpdated);

        binding = FragmentMetersBinding.inflate(inflater, container, false);
        binding.recyclerViewMeters.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    private void onUserUpdated(User user) {
        if (user.isLoggedIn()) {
            binding.textViewLogin.setVisibility(View.GONE);
            binding.recyclerViewMeters.setVisibility(View.VISIBLE);

            metersViewModel.loadData();
        } else {
            binding.textViewLogin.setVisibility(View.VISIBLE);
            binding.recyclerViewMeters.setVisibility(View.GONE);
        }
    }

    private void onMetersUpdated(List<Entity> data) {
        if (data != null) {
            MetersAdapter adapter = new MetersAdapter(data, this);
            binding.recyclerViewMeters.setAdapter(adapter);
        } else {
            Util.displayShortToast(getContext(), "Error fetching data");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        // TODO: 9/25/2021
    }
}