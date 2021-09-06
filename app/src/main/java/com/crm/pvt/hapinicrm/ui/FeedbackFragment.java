package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentFeedbackBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;
    private DatabaseReference taskDatabaseReference;
    String taskAssignedTo,name,number,city,task;
    String []taskProgressType = {"","Connected and Interested","Connected and Not Interested","Not Connected"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        taskDatabaseReference = FirebaseDatabase.getInstance()
                .getReference("Task_Assignment_V2")
                .child("CRM_User");

        TaskModel taskModel = FeedbackFragmentArgs.fromBundle(getArguments()).getTaskModel();

        taskAssignedTo = taskModel.getTaskAssignedTo();
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
            int pos = binding.spTaskProgress.getSelectedItemPosition();
            if(pos == 0 ) {
                Snackbar.make(v,"Select Task Progress",Snackbar.LENGTH_SHORT).show();
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
            } else {
                String taskProgress = taskProgressType[pos];
                String feedback = Objects.requireNonNull(binding.etFeedback.getText()).toString();
                if(feedback.isEmpty()) {
                    Snackbar.make(v,"Enter feedback first",Snackbar.LENGTH_SHORT).show();
                    binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
                } else {
                    submitFeedback(feedback,taskProgress);
                }
            }
        });

        binding.ivBackButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.Customer_Feedback, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTaskProgress.setAdapter(adapter);
    }

    private void submitFeedback(String feedback,String taskProgress) {

        TaskModel feedbackModel = new TaskModel(taskAssignedTo,name, number, city, task, taskProgress,feedback);

        taskDatabaseReference.child(taskAssignedTo).child(number).setValue(feedbackModel).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
                Snackbar.make(requireView(),"Feedback submission completed",Snackbar.LENGTH_SHORT).show();
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                binding.pbSubmitFeedback.setVisibility(View.INVISIBLE);
                Snackbar.make(requireView(),"Something went wrong. Please try again.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}