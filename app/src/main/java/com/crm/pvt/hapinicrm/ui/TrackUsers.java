package com.crm.pvt.hapinicrm.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUsersBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    public static String userType;
    EditText searchuser;

    private DatabaseReference crm,de,ve;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrackUsersBinding.inflate(inflater, container, false);

        data = getArguments().getString("data");
        switch (data) {
            case "crmUser":
                userType = "crm";
                break;
            case "videoUser":
                userType = "video";
                break;
            case "dataUser":
                userType = "data";
                break;
        }
        Log.e(TAG, "onCreateView: " + data);
        searchuser=binding.searchuser;

        searchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userenter=s.toString();
                showsearchdata(userenter,data);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        Query query=de.child("video");
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
        Query query=ve.child("data");
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
    private void showsearchdata(String searchout,String usertype){

        switch (usertype){
            case "crmUser":
                getcrmusershorteddata(searchout,"crm");
                break;
            case "videoUser":
                getcrmusershorteddata(searchout,"video");
                break;
            case "dataUser":
                getcrmusershorteddata(searchout,"data");


        }
    }
    private void getcrmusershorteddata(String searchout,String usertype){
        Log.e(TAG, "getcrmusershorteddata: "+searchout );
        Query query=FirebaseDatabase.getInstance().getReference("usersv2").child(usertype).orderByChild("name")
                .startAt(searchout).endAt(searchout+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackUserModelList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){


                        String name=dataSnapshot.child("name").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String mobileno=dataSnapshot.child("mobileNo").getValue().toString();
                        String whatsappno=dataSnapshot.child("whatsAppNo").getValue().toString();
                        String passcode=dataSnapshot.child("passcode").getValue().toString();
                        String password=dataSnapshot.child("password").getValue().toString();
                        String location=dataSnapshot.child("city").getValue().toString();
                        trackUserModelList.add(new TrackUserModel(name,email,mobileno,whatsappno,passcode,password,location,""));


                    Log.e(TAG, "onDataChange: "+name+email );

                }
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}