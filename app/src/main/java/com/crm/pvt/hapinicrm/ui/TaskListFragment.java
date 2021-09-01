package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.currentUserPasscode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.TaskListAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTaskListBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.util.TaskCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskListFragment extends Fragment implements TaskCallback {

    private FragmentTaskListBinding binding;
    private TaskListAdapter taskListAdapter;
    private DatabaseReference taskDatabase;
    private static final int CALL_PERMISSION_CODE = 1;

    //will send this taskModel to feedback form page.
    private TaskModel taskModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String passcode;

        String userPasscode = getArguments().getString("userPasscode");
        if(userPasscode != null && !userPasscode.isEmpty()) {
            passcode = userPasscode;
        }else {
            passcode = currentUserPasscode;
        }
        taskDatabase = FirebaseDatabase.getInstance().
                getReference("Task_Assignment_V2").
                child("CRM_User").child(passcode);
        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
        taskListAdapter = new TaskListAdapter(this);
        binding.rvTaskList.setAdapter(taskListAdapter);
        getAllTask();
    }

    private void getAllTask() {

        ArrayList<TaskModel> taskModels = new ArrayList<>();
        taskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskModels.clear();
                for(DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    TaskModel taskModel = taskSnapshot.getValue(TaskModel.class);
                    Log.i("TaskListFragment",taskModel.toString());
                    taskModels.add(taskModel);
                }
                taskListAdapter.setTaskModels(taskModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void callToCustomer(TaskModel taskModel) {
        this.taskModel = taskModel;
        if(checkPermission(Manifest.permission.CALL_PHONE,CALL_PERMISSION_CODE)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+taskModel.getCustomerNumber()));
            getFeedback.launch(callIntent);
        }
    }

    ActivityResultLauncher<Intent> getFeedback = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Navigation.findNavController(requireView()).
                        navigate(TaskListFragmentDirections.actionTaskListFragmentToFeedbackFragment(taskModel));
            });

    private boolean checkPermission(String permission, int requestCode) {
        String []permissionList = {permission};
        if(ContextCompat.checkSelfPermission(requireContext(),permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), permissionList,requestCode);
            return false;
        } else {
            return true;
        }
    }
}