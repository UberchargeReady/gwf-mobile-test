package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dyejeekis.gwf_mobile_test.MyApp;
import com.dyejeekis.gwf_mobile_test.R;
import com.dyejeekis.gwf_mobile_test.data.model.Entity;
import com.dyejeekis.gwf_mobile_test.data.model.Meter;
import com.dyejeekis.gwf_mobile_test.data.model.User;
import com.dyejeekis.gwf_mobile_test.data.remote.ApiCallback;
import com.dyejeekis.gwf_mobile_test.data.remote.AppApiCallback;
import com.dyejeekis.gwf_mobile_test.data.remote.Result;
import com.dyejeekis.gwf_mobile_test.data.remote.api.Response;
import com.dyejeekis.gwf_mobile_test.databinding.FragmentMetersBinding;
import com.dyejeekis.gwf_mobile_test.ui.auth.AuthViewModel;
import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;
import com.dyejeekis.gwf_mobile_test.util.Util;

import java.util.List;

public class MetersFragment extends Fragment implements MeterListener, SwipeRefreshLayout.OnRefreshListener {

    private AuthViewModel authViewModel;
    private MetersViewModel metersViewModel;
    private FragmentMetersBinding binding;
    private ApiCallback<Response> loadDataCb;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
        loadDataCb = new AppApiCallback<>(getContext(), result -> {
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutable().observe(getViewLifecycleOwner(), this::onUserUpdated);
        metersViewModel = new ViewModelProvider(requireActivity()).get(MetersViewModel.class);
        metersViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), this::onMetersUpdated);

        binding = FragmentMetersBinding.inflate(inflater, container, false);
        binding.recyclerViewMeters.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefresh.setOnRefreshListener(this);
        if (metersViewModel.getMutableLiveData().getValue() == null) {
            refreshData();
        }
    }

    private void onUserUpdated(User user) {
        if (user.isLoggedIn()) {
            binding.textViewLogin.setVisibility(View.GONE);
            binding.recyclerViewMeters.setVisibility(View.VISIBLE);
        } else {
            binding.textViewLogin.setVisibility(View.VISIBLE);
            binding.recyclerViewMeters.setVisibility(View.GONE);
        }
    }

    private void refreshData(User user) {
        if (user.isLoggedIn()) {
            binding.swipeRefresh.setRefreshing(true);
            if (user.isAccessTokenValid()) {
                metersViewModel.loadData(loadDataCb);
            } else {
                try {
                    authViewModel.makeRefreshRequest(new AppApiCallback<>(getContext(), result -> {
                        binding.swipeRefresh.setRefreshing(false);
                        if (result instanceof Result.Success) {
                            metersViewModel.loadData(loadDataCb);
                        } else Util.displayShortToast(getContext(),
                                "Failed to refresh access token");
                    }));
                } catch (NetworkUtil.RefreshTokenExpiredException e) {
                    authViewModel.makeLogoutRequest(new AppApiCallback<>(getContext(), result -> {
                        binding.swipeRefresh.setRefreshing(false);
                        Util.displayShortToast(getContext(),
                                "Refresh token expired - Please log in");
                    }));
                }
            }
        } else {
            binding.swipeRefresh.setRefreshing(false);
            Util.displayShortToast(getContext(), "Please log in");
        }
    }

    private void refreshData() {
        refreshData(MyApp.getInstance().getCurrentUser());
    }

    private void onMetersUpdated(List<Entity> data) {
        if (data == null || data.isEmpty()) {
            Util.displayShortToast(getContext(), "Error fetching data");
        } else {
            MetersAdapter adapter = new MetersAdapter(data, this);
            binding.recyclerViewMeters.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View.OnClickListener onMeterClick(Meter meter) {
        // TODO: 9/26/2021
        return null;
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_meter:
                // TODO: 9/26/2021
                return true;
            case R.id.action_refresh:
                refreshData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}