package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentMasterDashboardBinding;

public class MasterDashboardFragment extends Fragment {

    private FragmentMasterDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMasterDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cvAddAdmin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToAddAdminFragment())
        );
        binding.cvTrackAdmin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToTrackAdminFragment())
        );
        binding.cvAddUser.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToAddUserFragment())
        );
        binding.cvTrackUser.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToTrackUserFragment())
        );
        binding.cvProfile.setOnClickListener( v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToProfileFragment())
        );
        binding.ivBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

        binding.cvTaskAssign.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToTaskAssignFragment()));
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MainActivity.class));
                getActivity().finish();
            }
        });
    }
}