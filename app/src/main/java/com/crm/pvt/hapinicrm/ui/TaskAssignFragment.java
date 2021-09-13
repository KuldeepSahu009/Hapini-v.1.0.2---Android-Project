package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentTaskAssignBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskAssignFragment extends Fragment {

    FragmentTaskAssignBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskAssignBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> admins= new ArrayList<>();
        admins.add("Select Admin");
        admins.add("CRM Admin");
        admins.add("CRM User");
        admins.add("Franchise");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this.getContext(), android.R.layout.simple_spinner_item, admins);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.adminSpinner.setAdapter(adapter);

        binding.taskAssignSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int positionSelected = binding.adminSpinner.getSelectedItemPosition();

                    switch (positionSelected){
                        case 0 : Snackbar.make(v , "Select the Admin Type" , Snackbar.LENGTH_LONG).show();
                                 break;
                        case 1 : assignTaskToCrmAdmin();
                                 break;
                        case 2 : assignTaskcrmUser();
                                 break;
                        case 3:
                            assigntasktofranchise();
                            break;


                }
            }
        });
    }

    void assignTaskToCrmAdmin(){
        Navigation.findNavController(getView()).navigate(TaskAssignFragmentDirections.actionTaskAssignFragmentToCsvfilefromfranchise2());
    }

    void assignTaskcrmUser (){
        Navigation.findNavController(getView()).navigate(TaskAssignFragmentDirections.actionTaskAssignFragmentToCrmadmincsvfilesend2());
    }
    void assigntasktofranchise(){
        Navigation.findNavController(getView()).navigate(TaskAssignFragmentDirections.movetofranchisetask());

    }

}