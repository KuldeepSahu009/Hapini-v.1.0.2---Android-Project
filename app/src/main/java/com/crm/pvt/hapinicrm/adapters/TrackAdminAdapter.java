package com.crm.pvt.hapinicrm.adapters;

import static com.crm.pvt.hapinicrm.ui.AdminDataViewFragment.type;

import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class TrackAdminAdapter extends RecyclerView.Adapter<TrackAdminAdapter.TrackAdminViewHolder> {

    Context context;
    ArrayList<Admin> admins;
    Datacallbacktrackuser datacallbacktrackuser;
    private static final String TAG = "TAG";
    public static String usertyepes;

    public TrackAdminAdapter( Context context,ArrayList<Admin> admins,Datacallbacktrackuser datacallbacktrackuser){
        this.context = context;
        this.admins=admins;
        this.datacallbacktrackuser=datacallbacktrackuser;
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

        holder.deleteAdmin.setOnClickListener(v -> {



            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Attention");
            builder.setMessage("Do you want to delete this admin?");
            builder.setCancelable(true);

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (admins.size()>0){
                    datacallbacktrackuser.remove(admins.get(position),usertyepes);}
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.e(TAG, "onClick: "+"cancel" );
                    dialog.dismiss();
                }
            });



            AlertDialog deleteAdminDialog = builder.create();
            deleteAdminDialog.show();
        });

        if (!admin.getImgurl().isEmpty()){
            Glide.with(context).load(admin.getImgurl()).into(holder.profilepic);
        }

    }



    @Override
    public int getItemCount() {

        return admins.size();
    }



    static class TrackAdminViewHolder extends RecyclerView.ViewHolder{
        ImageView profilepic,deleteAdmin;

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
            deleteAdmin = itemView.findViewById(R.id.trackadmindeleteprofile);
        }
    }
}
