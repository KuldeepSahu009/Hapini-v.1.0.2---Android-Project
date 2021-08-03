package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TrackAdminAdapter extends RecyclerView.Adapter<TrackAdminAdapter.TrackAdminViewHolder> {

    Context context;
    List<Admin> admins;

    public TrackAdminAdapter( Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TrackAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_admin_details,parent,false);
        return new TrackAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdminViewHolder holder, int position) {
        Admin admin=admins.get(position);
        holder.name.setText(admin.getName());
        holder.email.setText(admin.getEmail());
        holder.mobile.setText(admin.getPhoneno());
        holder.whatsappno.setText(admin.getWhatsappno());
        holder.passcode.setText(admin.getPasscode());
        holder.password.setText(admin.getPassword());
        holder.location.setText(admin.getLocation());

        if (!admin.getImgurl().isEmpty()){
            Glide.with(context).load(admin.getImgurl()).into(holder.profilepic);
        }

    }

    @Override
    public int getItemCount() {
        if(admins == null) {
            return 0;
        }
        return admins.size();
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
        notifyDataSetChanged();
    }

    static class TrackAdminViewHolder extends RecyclerView.ViewHolder{
        ImageView profilepic;

        TextView name, email, mobile, location,whatsappno,password,passcode;

        public TrackAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trackadminname);
            email = itemView.findViewById(R.id.trackadminemailid);
            mobile = itemView.findViewById(R.id.trackadminphoneno);
            location = itemView.findViewById(R.id.trackadminlocation);
            whatsappno = itemView.findViewById(R.id.trackadminwhatsappno);
            password = itemView.findViewById(R.id.trackadminpassword);
            passcode = itemView.findViewById(R.id.trackadminpasscode);
            profilepic=itemView.findViewById(R.id.trackadminprofilepic);

        }
    }
}
