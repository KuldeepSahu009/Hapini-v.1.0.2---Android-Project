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

import com.crm.pvt.hapinicrm.adapters.TaskListAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTaskListBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding binding;
    private TaskListAdapter taskListAdapter;
    private DatabaseReference taskDatabase;
  //  private ArrayList<TaskModel> taskModels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskDatabase = FirebaseDatabase.getInstance().
                getReference("Task_Assignment_V2").
                child("CRM_User").child(currentUserPasscode);
        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
        taskListAdapter = new TaskListAdapter();
        binding.rvTaskList.setAdapter(taskListAdapter);
        getAllTask();
    }

    private void getAllTask() {

        ArrayList<TaskModel> taskModels = new ArrayList<>();
        taskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
}