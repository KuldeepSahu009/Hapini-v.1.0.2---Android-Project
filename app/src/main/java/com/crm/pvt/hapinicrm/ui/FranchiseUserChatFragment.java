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
import com.crm.pvt.hapinicrm.adapters.FranchiseChatPreviewAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseUserChatBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.FranchiseChatPreviewClickCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FranchiseUserChatFragment extends Fragment implements FranchiseChatPreviewClickCallback {

    private FragmentFranchiseUserChatBinding binding;
    private FranchiseChatPreviewAdapter chatPreviewAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFranchiseUserChatBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pbFranchiseUserChat.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
        initializeRecyclerView();
    }

    @Override
    public void navigateToChatsScreen(TrackUserModel user) {
        Navigation
                .findNavController(requireView())
                .navigate(FranchiseUserChatFragmentDirections
                        .actionFranchiseUserChatFragmentToFranchiseChatScreenFragment(user));
    }

    private void initializeRecyclerView() {
        chatPreviewAdapter = new FranchiseChatPreviewAdapter(this);
        binding.rvFranchiseUserChat.setAdapter(chatPreviewAdapter);
        getAllUsers();
    }

    private void getAllUsers() {
        ArrayList<TrackUserModel> users = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot usersSnapshot: snapshot.getChildren()) {
                    TrackUserModel user = usersSnapshot.getValue(TrackUserModel.class);
                    users.add(user);
                }
                binding.pbFranchiseUserChat.setVisibility(View.INVISIBLE);
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
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
        super.onPause();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("franchises")
                        .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                        .setValue("active");
    }
}