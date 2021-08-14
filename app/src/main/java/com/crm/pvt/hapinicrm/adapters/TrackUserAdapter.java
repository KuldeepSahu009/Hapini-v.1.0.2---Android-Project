package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.ui.CrmAdminFragment;
import com.crm.pvt.hapinicrm.ui.TrackUserFragment;
import com.crm.pvt.hapinicrm.ui.TrackUsers;
import com.crm.pvt.hapinicrm.viewholder.Trackuserviewholders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TrackUserAdapter extends RecyclerView.Adapter<Trackuserviewholders> {
    private  Context context;
    private  List<TrackUserModel> trackUserModelList;
    private  List<String> activeUserList;

    public TrackUserAdapter(Context context, List<TrackUserModel> trackUserModelList , List<String> activeUserList) {
        this.context = context;
        this.trackUserModelList = trackUserModelList;
         this.activeUserList = activeUserList;

    }

    public TrackUserAdapter(Context context, List<TrackUserModel> trackUserModelList) {
        this.context = context;
        this.trackUserModelList = trackUserModelList;
    }



    @NonNull
    @Override
    public Trackuserviewholders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_user_info_card_view,parent,false);
        return new Trackuserviewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Trackuserviewholders holder, int position) {
        TrackUserModel tempmodel=trackUserModelList.get(position);

        holder.activeStatusUser.setImageResource(R.drawable.red_dot);
        holder.name.setText(tempmodel.getName());
        holder.email.setText(tempmodel.getEmail());
        holder.phone.setText(tempmodel.getPhoneno());
        holder.whatsappno.setText(tempmodel.getWhatsappno());
        holder.passcode.setText(tempmodel.getPasscode());
        holder.password.setText(tempmodel.getPassword());
        holder.location.setText(tempmodel.getLocation());

        if(activeUserList != null) {
            for (String passcode : activeUserList) {
                if (passcode.equals(tempmodel.getPasscode())) {
                    holder.activeStatusUser.setImageResource(R.drawable.green_dot);
                    break;
                }
            }
        }
        //For Deletion of User
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usersv2").child(TrackUsers.userType);
                Toast.makeText(v.getContext(), "Deleting User....",Toast.LENGTH_LONG).show();
                reference.child(tempmodel.getPasscode()).removeValue();
                trackUserModelList.clear();
                notifyDataSetChanged();
            }
        });


        if(!tempmodel.getImgurl().equals("")){
            Glide.with(context)
                    .load(tempmodel.getImgurl())
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(holder.profileimg);
        }
    }
    @Override
    public int getItemCount() {
        return trackUserModelList.size();
    }
}
