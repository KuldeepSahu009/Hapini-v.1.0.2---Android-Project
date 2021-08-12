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
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.ui.DataCallBackTackFranchise;
import com.crm.pvt.hapinicrm.ui.Datacallbacktrackuser;

import java.util.ArrayList;
import java.util.List;

public class TrackFranchiseAdapter extends RecyclerView.Adapter<TrackFranchiseAdapter.TrackFranchiseViewHolder>{
    Context context;
    ArrayList<Franchise> franchises;
    DataCallBackTackFranchise dataCallBackTackFranchise;
    private static final String TAG = "TAG";
    private List<String> activeUserList;
    public TrackFranchiseAdapter( Context context,ArrayList<Franchise> franchises,List<String> activeUserList ,DataCallBackTackFranchise dataCallBackTackFranchise){
        this.context = context;
        this.franchises=franchises;
        this.dataCallBackTackFranchise=dataCallBackTackFranchise;
        this.activeUserList = activeUserList;
    }

    @NonNull
    @Override
    public TrackFranchiseAdapter.TrackFranchiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_admin_details,parent,false);
        return new TrackFranchiseAdapter.TrackFranchiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackFranchiseAdapter.TrackFranchiseViewHolder holder, int position) {
        Franchise franchise=franchises.get(position);
        holder.activeStatusFranchise.setImageResource(R.drawable.red_dot);
        holder.name.setText(franchise.getName());
        holder.email.setText(franchise.getEmail());
        holder.mobile.setText(franchise.getPhoneno());
        holder.whatsappno.setText(franchise.getWhatsappno());
        holder.passcode.setText(franchise.getPasscode());
        holder.password.setText(franchise.getPassword());
        holder.location.setText(franchise.getLocation());

        for(String passcode : activeUserList)
        {
            if(passcode.equals(franchise.getPasscode()))
            {
                holder.activeStatusFranchise.setImageResource(R.drawable.green_dot);
                break;
            }
        }

        holder.deleteAdmin.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Attention");
            builder.setMessage("Do you want to delete this franchise?");
            builder.setCancelable(true);

            builder.setPositiveButton("ok", (dialog, which) -> {
                if (franchises.size()>0){
                    dataCallBackTackFranchise.remove(franchises.get(position));}
                dialog.dismiss();
            });

            builder.setNegativeButton("cancel", (dialog, which) -> {
                Log.e(TAG, "onClick: "+"cancel" );
                dialog.dismiss();
            });

            AlertDialog deleteAdminDialog = builder.create();
            deleteAdminDialog.show();
        });

        if (!franchise.getImgurl().isEmpty()){
            Glide.with(context).load(franchise.getImgurl()).into(holder.profilepic);
        }

    }



    @Override
    public int getItemCount() {

        return franchises.size();
    }



    static class TrackFranchiseViewHolder extends RecyclerView.ViewHolder{
        ImageView profilepic,deleteAdmin , activeStatusFranchise;

        TextView name, email, mobile, location,whatsappno,password,passcode;

        public TrackFranchiseViewHolder(@NonNull View itemView) {
            super(itemView);
            activeStatusFranchise = itemView.findViewById(R.id.trackAdminStatus);
            name = itemView.findViewById(R.id.trackadminname);
            email = itemView.findViewById(R.id.trackadminemailid);
            mobile = itemView.findViewById(R.id.trackadminphoneno);
            location = itemView.findViewById(R.id.trackadminlocation);
            whatsappno = itemView.findViewById(R.id.trackadminwhatsappno);
            password = itemView.findViewById(R.id.trackadminpassword);
            passcode = itemView.findViewById(R.id.trackadminpasscode);
            profilepic=itemView.findViewById(R.id.trackadminprofilepic);
            deleteAdmin = itemView.findViewById(R.id.trackadmindeleteprofile);
        }
    }
}
