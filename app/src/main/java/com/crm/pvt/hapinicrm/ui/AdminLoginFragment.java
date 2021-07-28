package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class AdminLoginFragment extends Fragment {

    private FragmentAdminLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        binding.btnLogin.setOnClickListener(v -> {
            boolean isValid = validateCredentials();
            if(isValid) {
                int pos = binding.spSelectAdmin.getSelectedItemPosition();
                navigateTo(v, pos);
            } else {
                Snackbar.make(v,"Authentication Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.tvForgotPassword.setOnClickListener(v -> Navigation.findNavController(v).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToForgetpassword()));
    }

    private boolean validateCredentials() {
        if(binding.etPasscode.getText().toString().length() != 6 ) {
            binding.etPasscode.setError("Passcode should be 6 characters long");
            return false;
        } else if(binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.select_admin_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectAdmin.setAdapter(adapter);
    }

    private void navigateTo(View view, int pos) {
        switch (pos) {
            case 1: Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToCrmAdminFragment());
                break;
            case 2:
                Navigation.findNavController(view).navigate(R.id.action_adminLoginFragment_to_dataEntryAdminFragment);
                break;
            case 3: Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToVideoEditorAdminFragment());
                break;
            case 4: Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToMasterDashboardFragment());
                break;
        }
    }
}