package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.FranchiseChatPreviewAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseUserChatBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.FranchiseChatPreviewClickCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
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
        FirebaseDatabase.getInstance().getReference("adminV2/CRM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot usersSnapshot: snapshot.getChildren()) {
                    TrackUserModel admin = usersSnapshot.getValue(TrackUserModel.class);
                    admin.setName(admin.getName()+" (Admin)");
                    //check for addedBywhich
                    if(Splashscreen.spAdminsData != null)
                    {
                        if(Splashscreen.spAdminsData.getString("passcode","").equals(admin.getAddedBy()))
                        {
                            users.add(admin);
                        }
                    }
                }

                FirebaseDatabase.getInstance().getReference("usersv2/crm").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(DataSnapshot userSP : task.getResult().getChildren()) {
                                TrackUserModel user = userSP.getValue(TrackUserModel.class);
                                for(TrackUserModel admin : users) {
                                    assert user != null;
                                    if(user.getAddedBy().equals(admin.getPasscode())) {
                                        user.setName(user.getName()+" (User)");
                                        users.add(user);
                                    }
                                }
                            }
                            binding.pbFranchiseUserChat.setVisibility(View.INVISIBLE);
                            chatPreviewAdapter.setUsers(users);
                        }
                        else
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                binding.pbFranchiseUserChat.setVisibility(View.INVISIBLE);
                chatPreviewAdapter.setUsers(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}