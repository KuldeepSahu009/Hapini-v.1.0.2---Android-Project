package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        admins.add("Data Entry Admin");
        admins.add("Video Editor Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this.getContext(), android.R.layout.simple_spinner_item, admins);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.adminSpinner.setAdapter(adapter);

        binding.taskAssignSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( binding.etAssignTask.getText().toString().isEmpty() && binding.passCode.getText().toString().isEmpty()){
                    Snackbar.make(v,"All Fields are necessary",Snackbar.LENGTH_LONG).show();
                }
                else if (binding.passCode.getText().toString().length() != 6){
                    Snackbar.make(v, "Enter a Valid Pass Code" , Snackbar.LENGTH_LONG).show();
                }
                else {
                    int positionSelected = binding.adminSpinner.getSelectedItemPosition();
                    String task = binding.etAssignTask.getText().toString();
                    switch (positionSelected){
                        case 0 : Snackbar.make(v , "Select the Admin Type" , Snackbar.LENGTH_LONG).show();
                                 break;
                        case 1 : assignTaskToCrmAdmin(task);
                                 break;
                        case 2 : assignTaskToDataEntryAdmin(task);
                                 break;
                        case 3 : assignTaskToVideoEditorAdmin(task);
                                 break;
                    }
                }
            }
        });
    }

    void assignTaskToCrmAdmin(String task){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Task_Assignment").child("CRM_Admin");
        databaseReference.child(binding.passCode.getText().toString()).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Task is Assigned to CRM Admin",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Failed to Assign Task. Please try again later...",Toast.LENGTH_LONG).show();
            }
        });
    }

    void assignTaskToDataEntryAdmin (String task){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Task_Assignment").child("Data_Entry_Admin");
        databaseReference.child(binding.passCode.getText().toString()).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Task is Assigned to Data Entry Admin",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Failed to Assign Task. Please try again later...",Toast.LENGTH_LONG).show();
            }
        });
    }

    void assignTaskToVideoEditorAdmin (String task){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Task_Assignment").child("Video_Editor_Admin");
        databaseReference.child(binding.passCode.getText().toString()).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Task is Assigned to Video Editor Admin",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Failed to Assign Task. Please try again later...",Toast.LENGTH_LONG).show();
            }
        });
    }
}