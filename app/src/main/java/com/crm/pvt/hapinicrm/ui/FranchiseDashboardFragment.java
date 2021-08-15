package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseDashboardBinding;

public class FranchiseDashboardFragment extends Fragment {

    private FragmentFranchiseDashboardBinding binding;
    public static String addAdminTypes;
    private Bundle admin;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFranchiseDashboardBinding.inflate(inflater,container,false);
        admin = new Bundle();
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
        binding.adminaddfromfranchise.setOnClickListener(v -> {
            addAdminTypes = "CRM";
            Navigation.findNavController(v).navigate(FranchiseDashboardFragmentDirections.
                    actionFranchiseDashboardFragmentToAddAdminFormDetailsFragment2());
        });
        binding.trackadminaddfromfranchise.setOnClickListener(v -> {
            admin.putString("ADMIN" , "crm");
            Navigation.findNavController(v).navigate(R.id.action_franchiseDashboardFragment_to_adminDataViewFragment2,admin);
        });
    }
}