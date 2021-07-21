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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class crmadminAdapter extends RecyclerView.Adapter<crmadminAdapter.Viewholders> {
    private Context context;
    private List<crmviewmodel> crmviewmodelList;

    public crmadminAdapter(Context context, List<crmviewmodel> crmviewmodelList) {
        this.context = context;
        this.crmviewmodelList = crmviewmodelList;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewalladminlist_v2,parent,false);
        return new Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull crmadminAdapter.Viewholders holder, int position) {
        crmviewmodel tempcrmviewmodel=crmviewmodelList.get(position);
        holder.name.setText(tempcrmviewmodel.getName());
        holder.email.setText(tempcrmviewmodel.getEmail());
        holder.contactnumber.setText(tempcrmviewmodel.getContactnumber());
        holder.address.setText(tempcrmviewmodel.getLocation());

    }

    @Override
    public int getItemCount() {
        return crmviewmodelList.size();
    }

    class Viewholders extends RecyclerView.ViewHolder{
        private TextView name,email,contactnumber,address;

        public Viewholders(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.anyadminnamefromstorage);
            email=itemView.findViewById(R.id.anyadminemailfromstorage);
            contactnumber=itemView.findViewById(R.id.anyadminnumberfromstorage);
            address=itemView.findViewById(R.id.anyadminlocationfromstorage);
        }
    }
}
