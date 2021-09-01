package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentMasterDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MasterDashboardFragment extends Fragment {

    private FragmentMasterDashboardBinding binding;
    FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMasterDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
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
        binding.cvAddFranchise.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionGlobalAddFranchiseFormDetailsFragment())
        );
        binding.cvTrackFranchise.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionGlobalFranchiseDataViewFragment())
        );
        binding.cvProfile.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     ProfileFragment.user="";
                                                     Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToProfileFragment());
                                                 }
                                             }
        );
        binding.allmasternotification.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToAllnotification2());
        });
        binding.cvTaskAssign.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterDashboardFragmentDirections.actionMasterDashboardFragmentToTaskAssignFragment()));
        binding.ivLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                auth.signOut();
                Navigation.findNavController(v).navigateUp();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
            });
            AlertDialog logoutDialog = builder.create();
            logoutDialog.show();
        });
    }
}