package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.adapters.ChatPreviewAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserChatBinding;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.util.ChatPreviewClickCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CrmUserChatFragment extends Fragment implements ChatPreviewClickCallback {

    private FragmentCrmUserChatBinding binding;
    private ChatPreviewAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmUserChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pbCrmUserChat.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("franchiseV2");
        initializeRecyclerView();
    }

    @Override
    public void navigateToChatsScreen(Franchise franchise) {
        Navigation.findNavController(requireView())
                .navigate(CrmUserChatFragmentDirections
                        .actionCrmUserChatFragmentToChatScreenFragment(franchise));
    }

    private void initializeRecyclerView() {
        adapter = new ChatPreviewAdapter(this);
        binding.rvCrmUserChat.setAdapter(adapter);
        getAllFranchises();
    }

    private void getAllFranchises() {
        ArrayList<Franchise> franchises = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot franchiseSnapshot : snapshot.getChildren()) {
                    Franchise franchise = franchiseSnapshot.getValue(Franchise.class);
                    franchises.add(franchise);
                }
                binding.pbCrmUserChat.setVisibility(View.INVISIBLE);
                adapter.setFranchises(franchises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}