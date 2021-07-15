package com.crm.pvt.hapinicrm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.MasterviewModel;
import com.crm.pvt.hapinicrm.model.videoeditorviewmodel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class videoeditoradminAdapter extends RecyclerView.Adapter<videoeditoradminAdapter.ViewHolder>{
    private final List<videoeditorviewmodel> videoeditorviewmodelList;

    public videoeditoradminAdapter(List<videoeditorviewmodel> videoeditorviewmodelList) {
        this.videoeditorviewmodelList = videoeditorviewmodelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewallvideoeditoruserlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull videoeditoradminAdapter.ViewHolder holder, int position) {
        videoeditorviewmodel videoeditorviewmodel=videoeditorviewmodelList.get(position);
        holder.name.setText(videoeditorviewmodel.getName());
        holder.email.setText(videoeditorviewmodel.getEmail());
        holder.contactnumber.setText(videoeditorviewmodel.getContactnumber());
        holder.address.setText(videoeditorviewmodel.getLocation());
    }

    @Override
    public int getItemCount() {
        return videoeditorviewmodelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,contactnumber,address;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.usernamevideoeditorfromstorage);
            email=itemView.findViewById(R.id.useremailvideoeditorfromstorage);
            contactnumber=itemView.findViewById(R.id.usernumbervideoeditorfromstorage);
            address=itemView.findViewById(R.id.userlocationvideoeditorfromstorage);
        }
    }
}
