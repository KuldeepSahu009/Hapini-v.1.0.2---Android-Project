package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.ui.Datacallbacktrackuser;

import java.util.ArrayList;
import java.util.List;

public class TrackAdminAdapter extends RecyclerView.Adapter<TrackAdminAdapter.TrackAdminViewHolder> {

    Context context;
    ArrayList<Admin> admins;
    Datacallbacktrackuser datacallbacktrackuser;
    private static final String TAG = "TAG";
    public static String usertyepes;
    private List<String> activeUserList;

    public TrackAdminAdapter(Context context, ArrayList<Admin> admins, List<String> activeUserList, Datacallbacktrackuser datacallbacktrackuser) {
        this.context = context;
        this.admins = admins;
        this.datacallbacktrackuser = datacallbacktrackuser;
        this.activeUserList = activeUserList;
    }

    public TrackAdminAdapter(Context context, ArrayList<Admin> admins, Datacallbacktrackuser datacallbacktrackuser) {
        this.context = context;
        this.admins = admins;
        this.datacallbacktrackuser = datacallbacktrackuser;
    }

    @NonNull
    @Override
    public TrackAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_admin_details, parent, false);
        return new TrackAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdminViewHolder holder, int position) {
        Admin admin = admins.get(position);
        holder.activeStatusAdmin.setImageResource(R.drawable.red_dot);
        holder.name.setText(admin.getName());
        holder.email.setText(admin.getEmail());
        holder.mobile.setText(admin.getPhoneno());
        holder.whatsappno.setText(admin.getWhatsappno());
        holder.passcode.setText(admin.getPasscode());
        holder.password.setText(admin.getPassword());
        holder.state.setText(admin.getState());
        holder.city.setText(admin.getCity());
        holder.location.setText(admin.getLocation());

        if (activeUserList != null) {
            for (String passcode : activeUserList) {
                if (passcode.equals(admin.getPasscode())) {
                    holder.activeStatusAdmin.setImageResource(R.drawable.green_dot);
                    break;
                }
            }
        }

        holder.deleteAdmin.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Attention");
            builder.setMessage("Do you want to delete this admin?");
            builder.setCancelable(true);

            builder.setPositiveButton("ok", (dialog, which) -> {
                if (admins.size() > 0) {
                    datacallbacktrackuser.remove(admins.get(position), usertyepes);
                }
                dialog.dismiss();
            });

            builder.setNegativeButton("cancel", (dialog, which) -> {
                Log.e(TAG, "onClick: " + "cancel");
                dialog.dismiss();
            });

            AlertDialog deleteAdminDialog = builder.create();
            deleteAdminDialog.show();
        });

        if (!admin.getImgurl().isEmpty()) {
            Glide.with(context)
                    .load(admin.getImgurl())
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(holder.profilepic);
        }

    }


    @Override
    public int getItemCount() {
        return admins.size();
    }


    static class TrackAdminViewHolder extends RecyclerView.ViewHolder {
        ImageView profilepic, deleteAdmin, activeStatusAdmin;

        TextView name, email, mobile, state , city, location, whatsappno, password, passcode;

        public TrackAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            activeStatusAdmin = itemView.findViewById(R.id.trackAdminStatus);
            name = itemView.findViewById(R.id.trackadminname);
            email = itemView.findViewById(R.id.trackadminemailid);
            mobile = itemView.findViewById(R.id.trackadminphoneno);
            state = itemView.findViewById(R.id.trackadminstate);
            city = itemView.findViewById(R.id.trackadmincity);
            location = itemView.findViewById(R.id.trackadminlocation);
            whatsappno = itemView.findViewById(R.id.trackadminwhatsappno);
            password = itemView.findViewById(R.id.trackadminpassword);
            passcode = itemView.findViewById(R.id.trackadminpasscode);
            profilepic = itemView.findViewById(R.id.trackadminprofilepic);
            deleteAdmin = itemView.findViewById(R.id.trackadmindeleteprofile);
        }
    }
}