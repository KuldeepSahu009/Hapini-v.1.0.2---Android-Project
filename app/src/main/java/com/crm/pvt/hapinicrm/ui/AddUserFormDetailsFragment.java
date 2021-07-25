package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAddUserFormDetailsBinding;
import com.google.android.material.snackbar.Snackbar;

public class AddUserFormDetailsFragment extends Fragment {

    private FragmentAddUserFormDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserFormDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = "";
        if(AddUserFragment.addUserType != null) {
            if (AddUserFragment.addUserType.equals("CRM")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO CRM IN FUTURE
                 title = " ADD CRM USER";
            } else if (AddUserFragment.addUserType.equals("DE"))
            {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO DATA ENTRY IN FUTURE
                title = " ADD DATA ENTRY USER";
            }
            else
            {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO VIDEO EDITOR IN FUTURE
                title = " ADD VIDEO EDITOR USER";
            }
        }
        binding.tvAddUserFormDashboardTitle.setText(title);

        binding.btnAddUserSubmit.setOnClickListener( v-> {
            if(binding.cvAddUserFormTermsAndCondition.isChecked()) {
                // Add User to Model
            } else {
                Snackbar.make(v,"Please Accept all Terms and Conditions!.",Snackbar.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.ivBackFromAddUserFormFragment).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }
}