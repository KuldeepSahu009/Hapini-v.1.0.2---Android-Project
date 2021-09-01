package com.crm.pvt.hapinicrm.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.model.notificationmodel;

import java.util.ArrayList;

public class notificationadapter extends RecyclerView.Adapter<notificationadapter.notificationViewHolder>{
private Context context;
ArrayList<notificationmodel> Notifications;

    public notificationadapter(Context context, ArrayList<notificationmodel> notifications) {
        this.context = context;
        this.Notifications = notifications;
    }

    @NonNull
    @Override
    public notificationadapter.notificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificationrecyclerviewlayout, parent, false);
        return new notificationadapter.notificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationadapter.notificationViewHolder holder, int position) {
        notificationmodel Notification=Notifications.get(position);
       holder.passcode.setText(Notification.getPasscode());
        holder.logintime.setText(Notification.getLastlogin());
        holder.logouttime.setText(Notification.getLastlogout());
        holder.totaltime.setText(Notification.getTotalworktime());
    }

    @Override
    public int getItemCount() {
        return Notifications.size();
    }



    public class notificationViewHolder extends RecyclerView.ViewHolder {
        TextView passcode,logintime,logouttime,totaltime;
        public notificationViewHolder(@NonNull View itemView) {
            super(itemView);
            passcode = itemView.findViewById(R.id.notificationpasscode);
            logintime=itemView.findViewById(R.id.lastlogintime);
            logouttime=itemView.findViewById(R.id.lastlogouttime);
            totaltime=itemView.findViewById(R.id.totalworktime);
        }
    }
}
