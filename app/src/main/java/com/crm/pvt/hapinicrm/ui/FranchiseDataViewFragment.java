package com.crm.pvt.hapinicrm.ui;

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
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.adapters.TrackFranchiseAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FranchiseDataViewFragment extends Fragment implements DataCallBackTackFranchise {

    FragmentFranchiseDataViewBinding binding;
    TrackFranchiseAdapter trackFranchiseAdapter;
    ArrayList<Franchise> franchiseList = new ArrayList<>();
    private List<String> activeUserList;
    private static final String TAG = "TAG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFranchiseDataViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.trackFranchiseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        franchiseList = new ArrayList<>();
        activeUserList = new ArrayList<>();
        trackFranchiseAdapter = new TrackFranchiseAdapter(getContext(), franchiseList,activeUserList, this);
        binding.trackFranchiseRecyclerView.setAdapter(trackFranchiseAdapter);

        Snackbar.make(view, "Loading data please wait ", Snackbar.LENGTH_SHORT).show();

        getFranchiseData();

        CrmAdminFragment.activeStatusReference.child("franchises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    activeUserList.add(snapshot.getKey());

                }
                trackFranchiseAdapter = new TrackFranchiseAdapter(getContext(), franchiseList, activeUserList , FranchiseDataViewFragment.this);
                binding.trackFranchiseRecyclerView.setAdapter(trackFranchiseAdapter);
                trackFranchiseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {
                activeUserList.clear();
                if(datasnapshot.child("activeV2/franchises").exists())
                {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        activeUserList.add(snapshot.getKey());

                    }

                }
                trackFranchiseAdapter = new TrackFranchiseAdapter(getContext(), franchiseList, activeUserList , FranchiseDataViewFragment.this);
                binding.trackFranchiseRecyclerView.setAdapter(trackFranchiseAdapter);
                trackFranchiseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        CrmAdminFragment.activeStatusReference.child("franchises").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {
                activeUserList.clear();
                if(datasnapshot.child("activeV2/franchises").exists())
                {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        activeUserList.add(snapshot.getKey());

                    }

                }
                trackFranchiseAdapter = new TrackFranchiseAdapter(getContext(), franchiseList, activeUserList , FranchiseDataViewFragment.this);
                binding.trackFranchiseRecyclerView.setAdapter(trackFranchiseAdapter);
                trackFranchiseAdapter.notifyDataSetChanged();
                Log.i("LOGGGG","hhhh");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.etSearchFranchise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getsearchdata(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    void getFranchiseData() {
        DatabaseReference franchiseReference;
        franchiseReference = FirebaseDatabase.getInstance().getReference("franchiseV2");
        franchiseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                franchiseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Franchise franchiseObject;
                    franchiseObject = new Franchise(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    franchiseList.add(franchiseObject);
                }
                trackFranchiseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void remove(Franchise franchise) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("franchiseV2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (key.equals(franchise.getPasscode())) {
                        DatabaseReference reference1 = reference.child(key);
                        reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(getContext(), "franchise deleted", Toast.LENGTH_LONG).show();
                                franchiseList.clear();
                                getFranchiseData();

                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getsearchdata(String s) {
       getFranchiseSort(s);

    }

    private void getFranchiseSort(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("franchiseV2").orderByChild("name")
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                franchiseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Franchise franchiseObject;
                    franchiseObject = new Franchise(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    franchiseList.add(franchiseObject);
                }
                trackFranchiseAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}