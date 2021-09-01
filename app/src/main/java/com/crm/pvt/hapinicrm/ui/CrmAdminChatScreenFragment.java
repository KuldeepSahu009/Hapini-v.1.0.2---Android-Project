package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.ChatAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentChatScreenBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminChatScreenBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseChatScreenBinding;
import com.crm.pvt.hapinicrm.model.Chat;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CrmAdminChatScreenFragment extends Fragment {

    private FragmentCrmAdminChatScreenBinding binding;
    private ChatAdapter chatAdapter;
    private DatabaseReference chatReference;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrmAdminChatScreenBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrackUserModel user = CrmAdminChatScreenFragmentArgs.fromBundle(getArguments()).getUser();
        chatReference = FirebaseDatabase
                .getInstance()
                .getReference("Requests")
                .child("Chat_Module")
                .child(CrmAdminFragment.currentCRMAdmin.getPasscode())
                .child(user.getPasscode());
        initializeRecyclerViewChat();

        binding.tvUserName.setText(user.getName());

        //for get name of current admin

        binding.btnSendMessage.setOnClickListener(v -> {
            String message = binding.etMessage.getText().toString();
            if(message.isEmpty()) return;
            Chat chat = new Chat(CrmAdminFragment.currentCRMAdmin.getPasscode(),CrmAdminFragment.currentCRMAdmin.getName(),message);
            chatReference.push().setValue(chat);
            binding.etMessage.setText("");
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
                chats.clear();
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