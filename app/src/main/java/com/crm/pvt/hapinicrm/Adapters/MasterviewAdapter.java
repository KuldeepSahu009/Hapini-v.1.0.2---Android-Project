package com.crm.pvt.hapinicrm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.MasterviewModel;

import org.jetbrains.annotations.NotNull;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MasterviewAdapter extends RecyclerView.Adapter<MasterviewAdapter.ViewHolder> {
private Context context;

    private final List<MasterviewModel> masterviewmodel;

    public MasterviewAdapter( List<MasterviewModel> masterviewmodel) {

        this.masterviewmodel = masterviewmodel;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewalladminlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MasterviewAdapter.ViewHolder holder, int position) {
        MasterviewModel masterViewModellist=masterviewmodel.get(position);
        holder.name.setText(masterViewModellist.getName());
        holder.email.setText(masterViewModellist.getEmail());
        holder.contactnumber.setText(masterViewModellist.getContactnumber());
        holder.address.setText(masterViewModellist.getLocation());
    }

    @Override
    public int getItemCount() {
        return masterviewmodel.size();
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
TextView name,email,contactnumber,address;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.adminnamefromstorage);
            email=itemView.findViewById(R.id.adminemailfromstorage);
            contactnumber=itemView.findViewById(R.id.adminnumberfromstorage);
            address=itemView.findViewById(R.id.adminlocationfromstorage);
        }
    }
}