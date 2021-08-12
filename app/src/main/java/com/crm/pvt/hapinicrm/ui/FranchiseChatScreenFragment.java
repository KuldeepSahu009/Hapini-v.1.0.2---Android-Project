package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.adapters.ChatAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseChatScreenBinding;
import com.crm.pvt.hapinicrm.model.Chat;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FranchiseChatScreenFragment extends Fragment {

    private FragmentFranchiseChatScreenBinding binding;
    private ChatAdapter chatAdapter;
    private DatabaseReference chatReference;
    private String franchisePasscode = "123456";
    private String franchiseName = "Random Name";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFranchiseChatScreenBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrackUserModel user = FranchiseChatScreenFragmentArgs.fromBundle(getArguments()).getUser();
        chatReference = FirebaseDatabase
                .getInstance()
                .getReference("Requests")
                .child("Chat_Module")
                .child(franchisePasscode)
                .child(user.getPasscode());
        initializeRecyclerViewChat();

        binding.btnSendMessage.setOnClickListener(v -> {
            String message = binding.etMessage.getText().toString();
            if(message.isEmpty()) return;
            Chat chat = new Chat(franchiseName,message);
            chatReference.push().setValue(chat);
        });
    }

    private void initializeRecyclerViewChat() {
        chatAdapter = new ChatAdapter();
        binding.rvChats.setAdapter(chatAdapter);
        getAllChats();
    }

    private void getAllChats() {
        ArrayList<Chat> chats = new ArrayList<>();
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot chatSnapshot: snapshot.getChildren()) {
                    Chat chat = chatSnapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                chatAdapter.setChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}