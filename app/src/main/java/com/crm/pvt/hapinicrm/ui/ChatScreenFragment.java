package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.currentUser;
import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.currentUserPasscode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.ChatAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentChatScreenBinding;
import com.crm.pvt.hapinicrm.model.Chat;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatScreenFragment extends Fragment {

    private FragmentChatScreenBinding binding;
    private DatabaseReference chatReference;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatScreenBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrackUserModel franchise = ChatScreenFragmentArgs.fromBundle(getArguments()).getFranchise();
        chatReference = FirebaseDatabase
                .getInstance()
                .getReference("Requests")
                .child("Chat_Module")
                .child(franchise.getPasscode())
                .child(currentUserPasscode);

        initializeRecyclerViewChats();
        binding.tvFranchiseAdminName.setText(franchise.getName());

        binding.ivBackButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        binding.btnSendMessage.setOnClickListener(v -> {
            String message = binding.etMessage.getText().toString();
            if(message.isEmpty()) return;
            Chat chat = new Chat(currentUser.getPasscode(),currentUser.getName(),message);
            chatReference.push().setValue(chat);
            binding.etMessage.setText("");
        });
    }

    private void initializeRecyclerViewChats() {
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
