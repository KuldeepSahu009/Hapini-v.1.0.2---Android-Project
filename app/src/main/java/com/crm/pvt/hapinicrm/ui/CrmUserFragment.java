package com.crm.pvt.hapinicrm.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserBinding;
import com.crm.pvt.hapinicrm.util.TaskCallback;



public class CrmUserFragment extends Fragment {

    private FragmentCrmUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvSeeTaskAssigned.setOnClickListener(v -> Navigation.findNavController(v).navigate(CrmUserFragmentDirections.actionCrmUserFragmentToTaskListFragment()));
    }
//for status pupose don't delete it
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
        Splashscreen.spUsersData.edit().clear().commit();
    }
}