package com.crm.pvt.hapinicrm.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;

import java.util.ArrayList;

public class VerificationRequestsAdapter extends RecyclerView.Adapter<VerificationRequestsAdapter.VerificationRequestViewHolder> {

    Context context;
    ArrayList<String> names;

    public VerificationRequestsAdapter(Context context, ArrayList<String> names) {
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public VerificationRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.verification_request_view , parent , false);
        return new VerificationRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationRequestViewHolder holder, int position) {
        String name = names.get(position);
        holder.name.setText("Verification Request from " + name);
        holder.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle userName = new Bundle();
                userName.putString("NAME" , name);
                Navigation.findNavController(v).navigate(R.id.action_crmUserVerification_to_verificationOfUser , userName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    static class VerificationRequestViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button btnVerify;
        public VerificationRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.verifyingName);
            btnVerify = itemView.findViewById(R.id.btnVerify);
        }
    }
}
