package com.dyejeekis.gwf_mobile_test.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dyejeekis.gwf_mobile_test.MyApp;
import com.dyejeekis.gwf_mobile_test.R;
import com.dyejeekis.gwf_mobile_test.data.model.User;
import com.dyejeekis.gwf_mobile_test.data.remote.AppApiCallback;
import com.dyejeekis.gwf_mobile_test.data.remote.Result;
import com.dyejeekis.gwf_mobile_test.data.remote.api.LoginResponse;
import com.dyejeekis.gwf_mobile_test.databinding.FragmentAuthBinding;
import com.dyejeekis.gwf_mobile_test.util.Util;

public class AuthFragment extends Fragment implements View.OnClickListener {

    private AuthViewModel authViewModel;
    private FragmentAuthBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search_meter).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutable().observe(getViewLifecycleOwner(), this::onUserUpdated);

        binding = FragmentAuthBinding.inflate(inflater, container, false);
        binding.includedAccountAuth.buttonLogin.setOnClickListener(this);
        binding.includedAccountInfo.buttonLogout.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_login) {
            Util.hideKeyboard(getActivity());
            String username = binding.includedAccountAuth.editTextUsername.getText().toString();
            String password = binding.includedAccountAuth.editTextPassword.getText().toString();
            binding.includedAccountAuth.buttonLogin.setEnabled(false);
            binding.includedAccountAuth.editTextUsername.setEnabled(false);
            binding.includedAccountAuth.editTextPassword.setEnabled(false);

            authViewModel.makeLoginRequest(username, password,
                    new AppApiCallback<>(getContext(), result -> {
                        binding.includedAccountAuth.buttonLogin.setEnabled(true);
                        binding.includedAccountAuth.editTextUsername.setEnabled(true);
                        binding.includedAccountAuth.editTextPassword.setEnabled(true);
                        //binding.includedAccountAuth.editTextUsername.setText("");
                        binding.includedAccountAuth.editTextPassword.setText("");

                        if (result instanceof Result.Success) {
                            User user = MyApp.getInstance().getCurrentUser();
                            Util.displayShortToast(getContext(), "Logged in as: " +
                                    user.getUsername());
                        }
                        else Util.displayShortToast(getContext(), "Incorrect credentials");
                    }));
        } else if(v.getId() == R.id.button_logout) {
            Util.hideKeyboard(getActivity());
            authViewModel.makeLogoutRequest(new AppApiCallback<>(getContext(), result -> {
                Util.displayShortToast(getContext(), "Logged out");
            }));
        }
    }

    private void onUserUpdated(User user) {
        if (user.isLoggedIn()) {
            binding.includedAccountAuth.layoutAccountAuth.setVisibility(View.GONE);
            binding.includedAccountInfo.layoutAccountInfo.setVisibility(View.VISIBLE);

            binding.includedAccountInfo.textViewUsername.setText(user.getUsername());
        } else {
            binding.includedAccountAuth.layoutAccountAuth.setVisibility(View.VISIBLE);
            binding.includedAccountInfo.layoutAccountInfo.setVisibility(View.GONE);
        }
    }
}