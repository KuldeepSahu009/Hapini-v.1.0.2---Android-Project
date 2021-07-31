package com.crm.pvt.hapinicrm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDataViewFragment extends Fragment {

    FragmentAdminDataViewBinding binding;
    List<Admin> admins = new ArrayList<>();
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
        trackAdminAdapter = new TrackAdminAdapter(getContext(), admins);
        binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);

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
                trackAdminAdapter = new TrackAdminAdapter(getContext() , adminList);
                binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                trackAdminAdapter.notifyDataSetChanged();
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
                trackAdminAdapter = new TrackAdminAdapter(getContext() , adminList);
                binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                trackAdminAdapter.notifyDataSetChanged();
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
                trackAdminAdapter = new TrackAdminAdapter(getContext() , adminList);
                binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                trackAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}