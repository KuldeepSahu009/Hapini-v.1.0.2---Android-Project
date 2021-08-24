package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.VerificationRequestsAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentVerificationRequestsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerificationRequests extends Fragment {

    FragmentVerificationRequestsBinding binding;
    ArrayList<String> names = new ArrayList<>();
    VerificationRequestsAdapter verificationRequestsAdapter;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificationRequestsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2");
        verificationRequestsAdapter = new VerificationRequestsAdapter(getContext() , names);

        binding.requestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.requestRecyclerView.setAdapter(verificationRequestsAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String name = data.getKey();
                    names.add(name);
                }
                verificationRequestsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onStart() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
            CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                    .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
    }
}

