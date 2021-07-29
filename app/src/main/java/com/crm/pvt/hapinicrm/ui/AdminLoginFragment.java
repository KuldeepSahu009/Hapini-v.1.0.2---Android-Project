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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminLoginFragment extends Fragment {

    private FragmentAdminLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        auth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(v -> {

            binding.btnLogin.setEnabled(false);
            binding.pbAuth.setVisibility(View.VISIBLE);

            String passcode = Objects.requireNonNull(binding.etPasscode.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();

            if(passcode.length()!=6) {

                binding.etPasscode.setError("Passcode should be 6 characters long");
                binding.btnLogin.setEnabled(true);

            } else if(password.isEmpty()) {

                binding.etPassword.setError("Password cannot be empty");
                binding.btnLogin.setEnabled(true);

            } else {

//                auth.signInWithEmailAndPassword(passcode,password).addOnCompleteListener(task -> {
//                    binding.btnLogin.setEnabled(true);
//                    binding.pbAuth.setVisibility(View.INVISIBLE);
//
//                    if(task.isSuccessful()) {
//                        navigateTo(v,binding.spSelectAdmin.getSelectedItemPosition());
//                    } else {
//                        Snackbar.make(v,"Authorisation Failed",Snackbar.LENGTH_SHORT).show();
//                    }
//                });

                //need to remove these lines.
                binding.pbAuth.setVisibility(View.INVISIBLE);
                binding.btnLogin.setEnabled(true);
                navigateTo(v,binding.spSelectAdmin.getSelectedItemPosition());
            }
        });

        binding.tvForgotPassword.setOnClickListener(v -> Navigation.findNavController(v).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToForgetpassword()));
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
            case 3: Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToVideoEditorNavigation());
                break;
            case 4: Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToMasterDashboardFragment());
                break;
        }
    }
}