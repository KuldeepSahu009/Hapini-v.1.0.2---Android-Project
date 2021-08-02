package com.crm.pvt.hapinicrm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDataViewFragment extends Fragment {

    FragmentAdminDataViewBinding binding;
    TrackAdminAdapter trackAdminAdapter;
    List<Admin> adminList = new ArrayList<>();
    String admin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDataViewBinding.inflate(inflater , container , false);
        admin = getArguments().getString("ADMIN");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.trackAdminRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trackAdminAdapter = new TrackAdminAdapter(getContext());
        binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);

        Snackbar.make(view,"Loading data please wait ",Snackbar.LENGTH_SHORT).show();

        switch (admin) {
            case "crm":
                getCrmAdminData();
                break;
            case "video_editor":
                getVideoEditorAdminData();
                break;
            case "data_entry":
                getDataEntryAdminData();
                break;
        }

        binding.etSearchAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    trackAdminAdapter.setAdmins(adminList);
                    return;
                }
                List<Admin> admins = new ArrayList<>();
                for(Admin admin : adminList) {
                    String name = admin.getName();
                    if(name.toLowerCase().contains(s.toString().toLowerCase())) {
                        admins.add(admin);
                    }
                }
                trackAdminAdapter.setAdmins(admins);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

     void getCrmAdminData() {
        DatabaseReference crmReference;
        crmReference = FirebaseDatabase.getInstance().getReference("adminV2");
        crmReference.child("CRM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
                                             dataSnapshot.child("email").getValue().toString(),
                                             dataSnapshot.child("phoneno").getValue().toString(),
                                             dataSnapshot.child("whatsappno").getValue().toString(),
                                             dataSnapshot.child("passcode").getValue().toString(),
                                             dataSnapshot.child("password").getValue().toString(),
                                             dataSnapshot.child("location").getValue().toString(),
                                             dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.setAdmins(adminList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getVideoEditorAdminData() {
        DatabaseReference videoEditorReference;
        videoEditorReference = FirebaseDatabase.getInstance().getReference("adminV2");
        videoEditorReference.child("VIDEO_EDITOR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.setAdmins(adminList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getDataEntryAdminData() {
        DatabaseReference dataEntryReference;
        dataEntryReference = FirebaseDatabase.getInstance().getReference("adminV2");
        dataEntryReference.child("DATA_ENTRY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.setAdmins(adminList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}