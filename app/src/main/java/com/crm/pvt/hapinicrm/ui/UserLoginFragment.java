package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentUserLoginBinding;
import com.google.android.material.snackbar.Snackbar;


public class UserLoginFragment extends Fragment {

    private FragmentUserLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        binding.btnLoginUser.setOnClickListener(v -> {
            boolean isValid = validateCredentials();
            if(isValid) {
                int pos = binding.spSelectUser.getSelectedItemPosition();
                navigateTo(v, pos);
            } else {
                Snackbar.make(v,"Authentication Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.tvForgotPasswordUser.setOnClickListener(v->
                Navigation.findNavController(v).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToForgetpasswordUser()));
    }

    private boolean validateCredentials() {
        if(binding.etPasscodeUser.getText().toString().length() != 6 ) {
            binding.etPasscodeUser.setError("Passcode should be 6 characters long");
            return false;
        } else if(binding.etPasswordUser.getText().toString().isEmpty()) {
            binding.etPasswordUser.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.select_user_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectUser.setAdapter(adapter);
    }

    private void navigateTo(View view, int pos) {
        switch (pos) {
            case 1:
                Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToCrmUserFragment());
                break;
            case 2:
                Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToDataEntryUserFragment());
                break;
            case 3:
                Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToVideoEditorUserFragment());
                break;
        }
    }
}