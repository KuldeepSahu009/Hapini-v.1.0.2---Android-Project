package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.currentUserPasscode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentFeedbackBinding;
import com.crm.pvt.hapinicrm.model.FeedbackModel;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;
    private DatabaseReference feedbackDatabaseReference,taskDatabase;
    String name,number,city,task;
    private TaskModel taskModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedbackDatabaseReference = FirebaseDatabase.getInstance()
                .getReference("Feedback_V2")
                .child("CRM_User")
                .child(currentUserPasscode);

        taskDatabase = FirebaseDatabase.getInstance().
                getReference("Task_Assignment_V2").
                child("CRM_User").child(currentUserPasscode);

        taskModel = FeedbackFragmentArgs.fromBundle(getArguments()).getTaskModel();

        name = taskModel.getCustomerName();
        number = taskModel.getCustomerNumber();
        city = taskModel.getCustomerCity();
        task = taskModel.getTask();

        binding.tvCustomerName.setText(name);
        binding.tvCustomerNumber.setText(number);
        binding.tvCustomerCity.setText(city);
        binding.tvTask.setText(task);

        binding.btnSubmitFeedback.setOnClickListener(v -> {
            binding.pbSubmitFeedback.setVisibility(View.VISIBLE);
            String feedback = Objects.requireNonNull(binding.etFeedback.getText()).toString();
            if(feedback.isEmpty()) {
                Snackbar.make(v,"Enter feedback first",Snackbar.LENGTH_SHORT).show();
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
            } else {
                submitFeedback(feedback);
            }
        });
    }

    private void submitFeedback(String feedback) {

        FeedbackModel feedbackModel = new FeedbackModel(name, number, city, task, feedback);

        feedbackDatabaseReference.child(number).setValue(feedbackModel).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
                Snackbar.make(requireView(),"Feedback submission completed",Snackbar.LENGTH_SHORT).show();
                removeTaskFromTheList();
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
                Snackbar.make(requireView(),"Something went wrong. Please try again.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void removeTaskFromTheList() {
        taskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FeedbackFragment","Searching task for removal...");
                for(DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    TaskModel temp = Objects.requireNonNull(taskSnapshot.getValue(TaskModel.class));
                    if(temp.getCustomerNumber().equals(taskModel.getCustomerNumber())
                        && temp.getCustomerName().equals(taskModel.getCustomerName())) {
                        Log.i("FeedbackFragment","taskModel found");
                        taskSnapshot.getRef().removeValue();
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
}