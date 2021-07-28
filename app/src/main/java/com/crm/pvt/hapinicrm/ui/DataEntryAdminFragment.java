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
import com.crm.pvt.hapinicrm.databinding.FragmentDataEntryAdminBinding;

public class DataEntryAdminFragment extends Fragment {

    private FragmentDataEntryAdminBinding binding;
    private Boolean login=true;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDataEntryAdminBinding.inflate(inflater,container,false);

        if (login()) {
            login=false;
            Attendancedialogue attendancedialogue = new Attendancedialogue();
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
        }

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.dataentryadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserFragment.addUserType="DE";

            }
        });
        binding.dataentrytrackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("data","dataUser");


            }
        });
        binding.dataentrysetprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }
    private boolean login(){

        return login;
    }
}