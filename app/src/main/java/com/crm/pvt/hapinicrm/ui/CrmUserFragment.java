package com.crm.pvt.hapinicrm.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserBinding;

public class CrmUserFragment extends Fragment {

    private FragmentCrmUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvSeeTaskAssigned.
                setOnClickListener(v ->
                        Navigation.findNavController(v)
                                .navigate(CrmUserFragmentDirections.actionCrmUserFragmentToTaskListFragment()));

        binding.cvChatWithAdmin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(CrmUserFragmentDirections.actionCrmUserFragmentToCrmUserChatFragment());
        });

        binding.cvVerifyYourself.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_crmUserFragment_to_verificationByAdmin));
        binding.ivCrmUserLogout.setOnClickListener(v -> {
            crmUserLogout(v);
        });
    }

    private void crmUserLogout(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Splashscreen.spUsersData.edit().clear().commit();
            Navigation.findNavController(v).navigateUp();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });

        AlertDialog logoutDialog = builder.create();
        logoutDialog.show();
    }


    @Override
    public void onStart() {
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onPause() {
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null")).removeValue();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null")).removeValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null"))
                        .setValue("active");
    }


}