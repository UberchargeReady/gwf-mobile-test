package com.dyejeekis.gwf_mobile_test.ui.meters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.dyejeekis.gwf_mobile_test.data.remote.api.MeterResponse;
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
    private ApiCallback<MeterResponse> loadDataCb;
    private MetersAdapter adapter;

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
            metersViewModel.loadData(new AppApiCallback<>(getContext(), result -> {
                if (result instanceof Result.Error) {
                    Exception e = ((Result.Error) result).exception;
                    if (e instanceof NetworkUtil.AccessTokenExpiredException) {
                        authViewModel.makeRefreshRequest(new AppApiCallback<>(getContext(),
                                refreshResult -> {
                                    if (refreshResult instanceof Result.Success) {
                                        metersViewModel.loadData(loadDataCb);
                                    } else {
                                        binding.swipeRefresh.setRefreshing(false);
                                        Util.displayShortToast(getContext(),
                                                "Failed to refresh access token");
                                    }
                                }));
                    } else if (e instanceof NetworkUtil.RefreshTokenExpiredException) {
                        authViewModel.makeLogoutRequest(new AppApiCallback<>(getContext(),
                                logoutResult -> {
                                    binding.swipeRefresh.setRefreshing(false);
                                    Util.displayShortToast(getContext(),
                                            "Refresh token expired - Please log in");
                                }));
                    }
                } else {
                    binding.swipeRefresh.setRefreshing(false);
                }
            }));
            //if (user.isAccessTokenValid()) {
            //    metersViewModel.loadData(loadDataCb);
            //} else {
            //    try {
            //        authViewModel.makeRefreshRequest(new AppApiCallback<>(getContext(), result -> {
            //            if (result instanceof Result.Success) {
            //                metersViewModel.loadData(loadDataCb);
            //            } else {
            //                binding.swipeRefresh.setRefreshing(false);
            //                Util.displayShortToast(getContext(),
            //                        "Failed to refresh access token");
            //            }
            //        }));
            //    } catch (NetworkUtil.RefreshTokenExpiredException e) {
            //        authViewModel.makeLogoutRequest(new AppApiCallback<>(getContext(), result -> {
            //            binding.swipeRefresh.setRefreshing(false);
            //            Util.displayShortToast(getContext(),
            //                    "Refresh token expired - Please log in");
            //        }));
            //    }
            //}
        } else {
            binding.swipeRefresh.setRefreshing(false);
            //Util.displayShortToast(getContext(), "Please log in");
        }
    }

    private void refreshData() {
        refreshData(MyApp.getInstance().getCurrentUser());
    }

    private void onMetersUpdated(List<Entity> data) {
        if (data == null || data.isEmpty()) {
            Util.displayShortToast(getContext(), "Error fetching data");
        } else {
            adapter = new MetersAdapter(data, this);
            binding.recyclerViewMeters.setAdapter(adapter);
        }
    }

    private void findMeterById(String meterId) {
        if (MyApp.getInstance().getCurrentUser().isLoggedIn() &&
                adapter != null && adapter.getItems() != null) {
            Util.hideKeyboard(getActivity());
            int position = -1;
            for (Entity item : adapter.getItems()) {
                if (item instanceof Meter && ((Meter) item).getId().equalsIgnoreCase(meterId)) {
                    position = adapter.getItems().indexOf(item);
                    break;
                }
            }
            if (position == -1) {
                Util.displayShortToast(getContext(), "Meter not found");
            } else {
                binding.recyclerViewMeters.getLayoutManager().scrollToPosition(position);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View.OnClickListener onMeterClick(Meter meter) {
        return v -> {
            if (meter.getLatitude() != 404 && meter.getLongitude() != 404) {
                Bundle bundle = new Bundle();
                bundle.putString("id", meter.getId());
                bundle.putFloat("lat", meter.getLatitude());
                bundle.putFloat("lng", meter.getLongitude());

                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_meters_to_map, bundle);
            } else Util.displayShortToast(getContext(), "Invalid coordinates");
        };
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_meter).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findMeterById(searchView.getQuery().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}