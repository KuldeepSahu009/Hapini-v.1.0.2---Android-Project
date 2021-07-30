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
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminBinding;

public class CrmAdminFragment extends Fragment {
    private FragmentCrmAdminBinding binding;
    private Boolean login = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentCrmAdminBinding.inflate(inflater, container, false);
        if (login()) {
            login = false;
            Attendancedialogue attendancedialogue = new Attendancedialogue();
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.crmadminAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserFragment.addUserType = "CRM";
                Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToAdduserdata());
            }
        });
        binding.crmadmintrackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "crmUser");
                Navigation.findNavController(v).navigate(R.id.action_crmAdminFragment_to_alltrackusersfragment, bundle);


            }
        });
        binding.crmadminprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToSetrpofile());
            }
        });
        //
        binding.ivBackFromCrmDashboardFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });


    }

    private boolean login() {
        return login;
    }
}