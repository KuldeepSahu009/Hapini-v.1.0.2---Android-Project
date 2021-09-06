package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminGiveTaskBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CrmAdminGiveTaskFragment extends Fragment {

    private FragmentCrmAdminGiveTaskBinding binding;
    private DatabaseReference taskDatabaseReference;
    private DatabaseReference userReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmAdminGiveTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskDatabaseReference = FirebaseDatabase.getInstance().getReference("Task_Assignment_V2").child("CRM_User");
        userReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
        binding.btnAssignTask.setOnClickListener(v -> {
            binding.pbTask.setVisibility(View.VISIBLE);
            giveTaskToUser();
        });

        binding.ivBack.setOnClickListener( v -> {
            Navigation.findNavController(v).navigateUp();
        });

    }

    private void giveTaskToUser() {

        String userPasscode = Objects.requireNonNull(binding.etGivePasscodeTask.getText()).toString();
        String customerName = Objects.requireNonNull(binding.etNameOfCustomer.getText()).toString();
        String customerNumber = Objects.requireNonNull(binding.etMobileNumber.getText()).toString();
        String customerCity = Objects.requireNonNull(binding.etCity.getText()).toString();
        String task = Objects.requireNonNull(binding.etTaskDescription.getText()).toString();

        TaskModel taskModel = new TaskModel(
                userPasscode,
                customerName,
                customerNumber,
                customerCity,
                task,
                "Not Completed",
                ""
        );

        if(userPasscode.length() != 6) {

            binding.etGivePasscodeTask.setError("Enter valid passcode");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else if (customerNumber.length() != 10 ) {

            binding.etMobileNumber.setError("Please enter a valid number");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else if(task.length() == 0) {

            binding.etTaskDescription.setError("Enter task");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else {

            userReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String key;
                    TrackUserModel user = null;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        key = dataSnapshot.getKey();
                        assert key != null;
                        if (key.equals(userPasscode)) {
                            user = dataSnapshot.getValue(TrackUserModel.class);
                            break;
                        }

                    }
                    if(user != null) {
                        TrackUserModel finalUser = user;
                        taskDatabaseReference.child(userPasscode).child(customerNumber).setValue(taskModel).addOnCompleteListener(setTask -> {
                            if(setTask.isSuccessful()) {
                                Snackbar.make(binding.getRoot(),"Task Assigned to " + finalUser.getName() + " ",Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(binding.getRoot(),"Something went wrong !!! Try Again ",Snackbar.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        binding.etGivePasscodeTask.setError("Enter valid user passcode");
                    }
                    binding.pbTask.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }
}