package com.crm.pvt.hapinicrm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.crmviewmodel;
import com.crm.pvt.hapinicrm.model.dataentryviewmodel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class dataentryadminAdapter extends RecyclerView.Adapter<dataentryadminAdapter.Viewholders>{
    private Context context;
    private List<dataentryviewmodel> dataentryviewmodelList;

    public dataentryadminAdapter(Context context, List<dataentryviewmodel> dataentryviewmodelList) {
        this.context = context;
        this.dataentryviewmodelList = dataentryviewmodelList;
    }

    @NonNull
    @NotNull
    @Override
    public dataentryadminAdapter.Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewalladminlist,parent,false);
        return new Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull dataentryadminAdapter.Viewholders holder, int position) {
        dataentryviewmodel tempcrmviewmodel=dataentryviewmodelList.get(position);
        holder.name.setText(tempcrmviewmodel.getName());
        holder.email.setText(tempcrmviewmodel.getEmail());
        holder.contactnumber.setText(tempcrmviewmodel.getContactnumber());
        holder.address.setText(tempcrmviewmodel.getLocation());

    }

    @Override
    public int getItemCount() {
        return dataentryviewmodelList.size();
    }

    class Viewholders extends RecyclerView.ViewHolder{
        private TextView name,email,contactnumber,address;

        public Viewholders(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.adminnamefromstorage);
            email=itemView.findViewById(R.id.adminemailfromstorage);
            contactnumber=itemView.findViewById(R.id.adminnumberfromstorage);
            address=itemView.findViewById(R.id.adminlocationfromstorage);
        }
    }
}
