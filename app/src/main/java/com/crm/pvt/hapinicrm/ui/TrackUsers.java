package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUsersBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TrackUsers extends Fragment {

    private static final String TAG = "TAG";
    private FragmentTrackUsersBinding binding;
    private TrackUserAdapter trackUserAdapter;
    private List<TrackUserModel> trackUserModelList;
    private ArrayList<User> user;
    private String data;

    private DatabaseReference crm,de,ve;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrackUsersBinding.inflate(inflater, container, false);

        data = getArguments().getString("data");
        Log.e(TAG, "onCreateView: " + data);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        trackUserModelList = new ArrayList<>();
        binding.rvTrackUser.setLayoutManager(new LinearLayoutManager(getContext()));
        trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList);

        binding.rvTrackUser.setAdapter(trackUserAdapter);

        switch (data) {
            case "crmUser":
                Toast.makeText(getContext(),"Loading Data....",Toast.LENGTH_LONG).show();
                getCrmData();
                break;
            case "videoUser":
                Toast.makeText(getContext(),"Loading Data....",Toast.LENGTH_LONG).show();
                getVideoEditorData();
                break;
            case "dataUser":
                Toast.makeText(getContext(),"Loading Data....",Toast.LENGTH_LONG).show();
                getDataEntryOperatorData();
                break;
        }

    }

    private void getCrmData() {
         crm = FirebaseDatabase.getInstance().getReference("usersv2");
        Query query=crm.child("crm");
        ///
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                 trackUserModelList.add(new TrackUserModel((snapshot.child("name").getValue().toString()),(snapshot.child("email").getValue().toString()),
                         (snapshot.child("mobileNo").getValue().toString()),(snapshot.child("whatsAppNo").getValue().toString()),(snapshot.child("passcode").getValue().toString()),
                         (snapshot.child("password").getValue().toString()),(snapshot.child("city").getValue().toString()),(snapshot.child("").getValue().toString())));

                }
                trackUserAdapter=new TrackUserAdapter(getContext(),trackUserModelList);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getDataEntryOperatorData() {
        de = FirebaseDatabase.getInstance().getReference("usersv2");
        Query query=de.child("data");
        ///
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    trackUserModelList.add(new TrackUserModel((snapshot.child("name").getValue().toString()),(snapshot.child("email").getValue().toString()),
                            (snapshot.child("mobileNo").getValue().toString()),(snapshot.child("whatsAppNo").getValue().toString()),(snapshot.child("passcode").getValue().toString()),
                            (snapshot.child("password").getValue().toString()),(snapshot.child("city").getValue().toString()),(snapshot.child("").getValue().toString())));

                }
                trackUserAdapter=new TrackUserAdapter(getContext(),trackUserModelList);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getVideoEditorData() {
        ve = FirebaseDatabase.getInstance().getReference("usersv2");
        Query query=ve.child("video");
        ///
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    trackUserModelList.add(new TrackUserModel((snapshot.child("name").getValue().toString()),(snapshot.child("email").getValue().toString()),
                            (snapshot.child("mobileNo").getValue().toString()),(snapshot.child("whatsAppNo").getValue().toString()),(snapshot.child("passcode").getValue().toString()),
                            (snapshot.child("password").getValue().toString()),(snapshot.child("city").getValue().toString()),(snapshot.child("").getValue().toString())));

                }
                trackUserAdapter=new TrackUserAdapter(getContext(),trackUserModelList);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}