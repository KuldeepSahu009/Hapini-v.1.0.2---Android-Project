package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseDashboardBinding;

public class FranchiseDashboardFragment extends Fragment {

    private FragmentFranchiseDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFranchiseDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cvFranchiseChats.setOnClickListener(v -> {
            Navigation
                    .findNavController(v)
                    .navigate(FranchiseDashboardFragmentDirections
                            .actionFranchiseDashboardFragmentToFranchiseUserChatFragment());
        });
    }

    @Override
    public void onStart() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
        super.onPause();

    }
}