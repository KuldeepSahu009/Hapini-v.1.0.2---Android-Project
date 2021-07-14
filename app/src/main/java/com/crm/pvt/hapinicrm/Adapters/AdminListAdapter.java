package com.crm.pvt.hapinicrm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder> {

    private final List<Admin> adminList;

    public AdminListAdapter(List<Admin> adminList) {
        this.adminList = adminList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_preview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminListAdapter.ViewHolder holder, int position) {
        Admin admin = adminList.get(position);
        holder.tvName.setText(admin.getName());
        holder.tvType.setText(admin.getType());
        holder.tvDoj.setText(admin.getDoj());
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvType;
        private final TextView tvDoj;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvAdminName);
            tvType = itemView.findViewById(R.id.tvAdminType);
            tvDoj = itemView.findViewById(R.id.tvAdminJoiningDate);
        }
    }
}