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
import com.crm.pvt.hapinicrm.databinding.FragmentDataEntryAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DataEntryAdminFragment extends Fragment {

    private FragmentDataEntryAdminBinding binding;
    private Boolean login = true;
    private FirebaseAuth auth;
    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDataEntryAdminBinding.inflate(inflater, container, false);
         bundle = new Bundle();
        bundle.putString("data", "dataUser");

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

        auth = FirebaseAuth.getInstance();

        binding.dataentryadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserFragment.addUserType = "DE";
                Navigation.findNavController(v).navigate(DataEntryAdminFragmentDirections.showdataentryadduserfragment());
            }
        });
        binding.dataentrytrackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.showtrackdataentryusers, bundle);


            }
        });
        binding.dataentrysetprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DataEntryAdminFragmentDirections.setprofileofdataentry());
            }
        });
        //
        binding.dataentryadminBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
        binding.dataentryaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.dataentryaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DataEntryAdminFragmentDirections.showaddtask());
            }
        });

        binding.dataentrydaminlogout.setOnClickListener(v -> {
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