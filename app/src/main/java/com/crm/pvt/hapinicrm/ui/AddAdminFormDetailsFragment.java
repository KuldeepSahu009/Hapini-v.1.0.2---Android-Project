package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.databinding.FragmentAddAdminFormDetailsBinding;
import com.google.android.material.snackbar.Snackbar;

public class AddAdminFormDetailsFragment extends Fragment {

    private FragmentAddAdminFormDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminFormDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFormTitle();
        binding.btnAddUserSubmit.setOnClickListener(v -> {
            if (binding.cvAddAdminFormTermsAndCondition.isChecked()) {
                // Add User to Model
            } else {
                Snackbar.make(v, "Please Accept all Terms and Conditions!.", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.ivBackFromAddAdminFormFragment.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }

    private void setFormTitle() {
        String title = "";
        if (AddAdminFragment.addAdminType != null) {
            if (AddAdminFragment.addAdminType.equals("CRM")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO CRM IN FUTURE
                title = " ADD CRM ADMIN";
            } else if (AddAdminFragment.addAdminType.equals("DE")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO DATA ENTRY IN FUTURE
                title = " ADD DATA ENTRY ADMIN";
            } else {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO VIDEO EDITOR IN FUTURE
                title = " ADD VIDEO EDITOR ADMIN";
            }
        }
        binding.tvAddAdminFormDashboardTitle.setText(title);
    }
}