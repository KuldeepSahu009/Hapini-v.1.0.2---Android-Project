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
import com.crm.pvt.hapinicrm.adapters.ChatPreviewAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserChatBinding;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.ChatPreviewClickCallback;
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
import java.util.List;


public class CrmUserChatFragment extends Fragment implements ChatPreviewClickCallback {

    private FragmentCrmUserChatBinding binding;
    private ChatPreviewAdapter adapter;
    private DatabaseReference databaseReference;
    private static final String TAG = "CrmUserChatFragment";
    TrackUserModel currentUser;
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
    public void navigateToChatsScreen(TrackUserModel franchise) {
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
        ArrayList<TrackUserModel> franchises = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG,snapshot.getChildren().toString());
                for (DataSnapshot franchiseSnapshot : snapshot.getChildren()) {
                    TrackUserModel franchise = franchiseSnapshot.getValue(TrackUserModel.class);
                    assert franchise != null;
                    franchise.setName(franchise.getName()+" (Franchise)");
                    franchises.add(franchise);
                }
                binding.pbCrmUserChat.setVisibility(View.INVISIBLE);
                adapter.setFranchises(franchises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(Splashscreen.spUsersData != null)
        {
            FirebaseDatabase.getInstance().getReference("usersv2/crm")
                    .child(Splashscreen.spUsersData.getString("passcode",""))
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                currentUser = task.getResult().getValue(TrackUserModel.class);
                            }
                        }
                    });
        }



        FirebaseDatabase.getInstance().getReference("adminV2/CRM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot franchiseSnapshot : snapshot.getChildren()) {
                    TrackUserModel admin = franchiseSnapshot.getValue(TrackUserModel.class);
                    if(admin!=null) {
                        admin.setName(admin.getName() + " (Admin)");
                        if (currentUser != null && currentUser.getAddedBy() != null) {
                            if (currentUser.getAddedBy().equals(admin.getPasscode())) {
                                franchises.add(admin);
                            }
                        }
                    }
                }
                if(franchises.get(0).getAddedBy() != null)
                FirebaseDatabase.getInstance().getReference("franchiseV2").child(franchises.get(0).getAddedBy())
                            .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                    TrackUserModel franchise = task.getResult().getValue(TrackUserModel.class);
                                    franchise.setName(franchise.getName() + " (Franchise)");
                                    franchises.add(franchise);
                                binding.pbCrmUserChat.setVisibility(View.INVISIBLE);
                                adapter.setFranchises(franchises);
                            }
                        }
                    });

                binding.pbCrmUserChat.setVisibility(View.INVISIBLE);
                adapter.setFranchises(franchises);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
}