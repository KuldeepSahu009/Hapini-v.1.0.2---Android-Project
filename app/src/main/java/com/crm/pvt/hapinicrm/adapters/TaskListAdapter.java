package com.crm.pvt.hapinicrm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.databinding.TaskListLayoutBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.util.TaskCallback;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{

    ArrayList<TaskModel> taskModels;
    TaskCallback taskCallback;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskListLayoutBinding binding = TaskListLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaskModel taskModel = taskModels.get(position);
        holder.binding.tvCustomerName.setText(taskModel.getCustomerName());
        holder.binding.tvCustomerNumber.setText(taskModel.getCustomerNumber());
        holder.binding.tvCustomerCity.setText(taskModel.getCustomerCity());
        holder.binding.tvTask.setText(taskModel.getTask());
//        holder.binding.ivCall.setOnClickListener(v -> taskCallback.callToCustomer(taskModel.getCustomerNumber()));

    }

    @Override
    public int getItemCount() {
        if(taskModels != null)
            return taskModels.size();
        else
            return 0;
    }

    public void setTaskModels(ArrayList<TaskModel> taskModels) {
        this.taskModels = taskModels;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TaskListLayoutBinding binding;
        public ViewHolder(@NonNull TaskListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
