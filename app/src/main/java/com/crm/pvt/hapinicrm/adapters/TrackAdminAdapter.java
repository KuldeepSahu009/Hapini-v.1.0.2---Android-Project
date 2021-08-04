package com.crm.pvt.hapinicrm.adapters;

import static com.crm.pvt.hapinicrm.ui.AdminDataViewFragment.type;

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

        holder.deleteAdmin.setOnClickListener(v -> {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child(type);

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Attention");
            builder.setMessage("Do you want to delete this admin?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes",(dialog, which) -> reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        if(admin.getPasscode().equals(Objects.requireNonNull(dataSnapshot.getValue(Admin.class)).getPasscode())){

                            Log.i("TrackAdminAdapter","Admin Found");
                            reference.child(Objects.requireNonNull(dataSnapshot.getKey())).removeValue().addOnSuccessListener(unused -> {
                                admins.remove(admin);
                                setAdmins(admins);
                                Snackbar.make(v,"Admin removed ",Snackbar.LENGTH_SHORT).show();
                            });

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            }));
            builder.setNegativeButton("No",(dialog, which) -> { });

            AlertDialog deleteAdminDialog = builder.create();
            deleteAdminDialog.show();
        });

        if (!admin.getImgurl().isEmpty()){
            Glide.with(context).load(admin.getImgurl()).into(holder.profilepic);
        }

    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        if(admins == null) {
            return 0;
        }
        return admins.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
        notifyDataSetChanged();
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
