package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.viewholder.Trackuserviewholders;

import java.util.List;

public class TrackUserAdapter extends RecyclerView.Adapter<Trackuserviewholders> {
    private final Context context;
    private final List<TrackUserModel> trackUserModelList;

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

        holder.name.setText(tempmodel.getName());
        holder.email.setText(tempmodel.getEmail());
        holder.phone.setText(tempmodel.getPhoneno());
        holder.whatsappno.setText(tempmodel.getWhatsappno());
        holder.passcode.setText(tempmodel.getPasscode());
        holder.password.setText(tempmodel.getPassword());
        holder.location.setText(tempmodel.getLocation());

        if(!tempmodel.getImgurl().equals("")){
            Glide.with(context).load(tempmodel.getImgurl()).into(holder.profileimg);
        }
    }

    @Override
    public int getItemCount() {
        return trackUserModelList.size();
    }
}
