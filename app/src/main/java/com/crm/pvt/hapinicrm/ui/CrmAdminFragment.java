package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuth;

public class CrmAdminFragment extends Fragment {
    private FragmentCrmAdminBinding binding;
    private Boolean login = true;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentCrmAdminBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        String attendancepasscode = bundle.getString("crmadminpasscode","");

        Bundle bundle1=new Bundle();
        bundle.putString("todialog",attendancepasscode);

        if (login()) {
            login = false;
            Attendancedialogue attendancedialogue = new Attendancedialogue();
            attendancedialogue.setArguments(bundle1);
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

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
        binding.ivBackFromCrmAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });


        binding.ivCrmLogout.setOnClickListener(v ->{
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

            AlertDialog attendanceDialog = builder.create();
            attendanceDialog.show();
        });

    }

    private boolean login() {
        return login;
    }
}