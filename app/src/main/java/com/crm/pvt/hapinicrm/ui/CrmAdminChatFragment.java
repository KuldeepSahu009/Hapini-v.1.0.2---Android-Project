package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.CrmAdminChatPreviewAdapter;
import com.crm.pvt.hapinicrm.adapters.FranchiseChatPreviewAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminChatBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseUserChatBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.CrmAdminChatPreviewClickCallback;
import com.crm.pvt.hapinicrm.util.FranchiseChatPreviewClickCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CrmAdminChatFragment extends Fragment implements CrmAdminChatPreviewClickCallback {

    private FragmentCrmAdminChatBinding binding;
    private CrmAdminChatPreviewAdapter chatPreviewAdapter;
    private DatabaseReference databaseReference;
    private String currentAdminPasscode = "";
    final Admin[] admin = {null};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrmAdminChatBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pbCrmAdminChat.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
        currentAdminPasscode = Objects.requireNonNull(
                Objects.requireNonNull(
                        FirebaseAuth.getInstance().getCurrentUser()
                ).getEmail()).substring(0,6);


        DatabaseReference adminRef = FirebaseDatabase.getInstance()
                .getReference("adminV2")
                .child("CRM").getRef();
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot adminSnapshot: snapshot.getChildren()) {
                    if(Objects.equals(adminSnapshot.getKey(), currentAdminPasscode)) {
                        admin[0] = adminSnapshot.getValue(Admin.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        initializeRecyclerView();
    }

    @Override
    public void navigateToChatsScreen(TrackUserModel user) {
        Navigation
                .findNavController(requireView())
                .navigate(CrmAdminChatFragmentDirections
                        .actionCrmAdminChatFragmentToCrmAdminChatScreenFragment(user));
    }

    private void initializeRecyclerView() {
        chatPreviewAdapter = new CrmAdminChatPreviewAdapter(this);
        binding.rvCrmAdminChats.setAdapter(chatPreviewAdapter);
        getAllUsers();
    }

    private void getAllUsers() {
        ArrayList<TrackUserModel> users = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot usersSnapshot: snapshot.getChildren()) {
                    TrackUserModel user = usersSnapshot.getValue(TrackUserModel.class);
                    assert user != null;
                    if(user.getAddedBy().equals(currentAdminPasscode)) {
                        user.setName(user.getName() + " (User)");
                        users.add(user);
                    }
                }
                binding.pbCrmAdminChat.setVisibility(View.INVISIBLE);
                chatPreviewAdapter.setUsers(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("franchiseV2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot usersSnapshot: snapshot.getChildren()) {
                    TrackUserModel franchise = usersSnapshot.getValue(TrackUserModel.class);
                    if(admin[0] != null) {
                        assert franchise != null;
                        if (admin[0].getAddedBy().equals(franchise.getPasscode())) {
                            franchise.setName(franchise.getName() + " (Franchise)");
                            users.add(franchise);
                        }
                    }
                }
                binding.pbCrmAdminChat.setVisibility(View.INVISIBLE);
                chatPreviewAdapter.setUsers(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
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
    public void onResume() {
        super.onResume();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
    }
}