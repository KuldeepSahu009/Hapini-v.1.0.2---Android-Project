package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class TrackAdminAdapter extends RecyclerView.Adapter<TrackAdminAdapter.TrackAdminViewHolder> {

    Context context;
    List<Admin> admins = new ArrayList<>();

    public TrackAdminAdapter( Context context , List<Admin> admins){
        this.context = context;
        this.admins = admins;
    }

    @NonNull
    @Override
    public TrackAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_admin_details,parent,false);
        return new TrackAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdminViewHolder holder, int position) {

        holder.name.setText(admins.get(position).getName());
        holder.email.setText(admins.get(position).getEmail());
        holder.mobile.setText(admins.get(position).getContactNumber());
        holder.location.setText(admins.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }

    static class TrackAdminViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, mobile, location;

        public TrackAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameAdmin);
            email = itemView.findViewById(R.id.adminEmail);
            mobile = itemView.findViewById(R.id.adminMobile);
            location = itemView.findViewById(R.id.adminLocation);
        }
    }
}
