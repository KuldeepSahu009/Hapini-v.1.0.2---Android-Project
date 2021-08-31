package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseDashboardBinding;

public class FranchiseDashboardFragment extends Fragment {

    private FragmentFranchiseDashboardBinding binding;
    public static String addAdminTypes;
    private boolean attendance=false;
    private Bundle admin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFranchiseDashboardBinding.inflate(inflater,container,false);
        admin = new Bundle();
        if (attendance==false){
            Attendancedialogue attendancedialogue = new Attendancedialogue(getContext());
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
            Attendancedialogue.type="franchiseadmin";
            attendance=true;
        }
        return binding.getRoot();






    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cvFranchiseChats.setOnClickListener(v -> Navigation
                .findNavController(v)
                .navigate(FranchiseDashboardFragmentDirections
                        .actionFranchiseDashboardFragmentToFranchiseUserChatFragment()));

        binding.addadminfromfranchiseadmin.setOnClickListener(v -> {
            addAdminTypes = "CRM";
            Navigation
                    .findNavController(v)
                    .navigate(FranchiseDashboardFragmentDirections
                            .actionFranchiseDashboardFragmentToAddAdminFormDetailsFragment2());
        });

        binding.sendcsvfile.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(FranchiseDashboardFragmentDirections
                    .actionFranchiseDashboardFragmentToCsvfilefromfranchise());
        });

        binding.trackadminfromfranchiseadmin.setOnClickListener(v -> {
            admin.putString("ADMIN","crm");
            Navigation
                    .findNavController(v)
                    .navigate(R.id.action_franchiseDashboardFragment_to_adminDataViewFragment2,admin);
        });

        binding.franchiseadminlogout.setOnClickListener(v->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                if(Splashscreen.spAdminsData != null)
                Splashscreen.spAdminsData.edit().clear().commit();
                Navigation.findNavController(v).navigate(FranchiseDashboardFragmentDirections.actionFranchiseDashboardFragmentToStartFragment());
            });
            builder.setNegativeButton("No", (dialog, which) -> {
            });

            AlertDialog logoutDialog = builder.create();
            logoutDialog.show();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();

    }
    @Override
    public void onResume() {
        super.onResume();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                        .setValue("active");
    }
}