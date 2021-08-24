package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.StartFragment.selectedAdmin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUsersBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.model.User;
import com.crm.pvt.hapinicrm.util.UserClickCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TrackUsers extends Fragment implements UserClickCallback {

    private static final String TAG = "TAG";
    private FragmentTrackUsersBinding binding;
    private TrackUserAdapter trackUserAdapter;
    private List<TrackUserModel> trackUserModelList;
    private List<String> activeUserList;
    private ArrayList<User> user;
    private String data;
    public static String userType;
    UserClickCallback userClickCallback;
    TextView searchuser;

    private DatabaseReference crm, de, ve;


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
        searchuser = binding.searchuser;

        searchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.showshorteduserdatafragment);
                Shorteddataadmin.type = userType;
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        userClickCallback = this;
    }

    private void setupRecyclerView() {

        trackUserModelList = new ArrayList<>();
        activeUserList = new ArrayList<>();
        binding.rvTrackUser.setLayoutManager(new LinearLayoutManager(getContext()));
        trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
        binding.rvTrackUser.setAdapter(trackUserAdapter);

        switch (data) {
            case "crmUser":
                Toast.makeText(getContext(), "Loading Data....", Toast.LENGTH_LONG).show();
                getCrmData();
                break;
            case "videoUser":
                Toast.makeText(getContext(), "Loading Data....", Toast.LENGTH_LONG).show();
                getVideoEditorData();
                break;
            case "dataUser":
                Toast.makeText(getContext(), "Loading Data....", Toast.LENGTH_LONG).show();
                getDataEntryOperatorData();
                break;
        }
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
                if (datasnapshot.child("activeV2/users").child(userType).exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        activeUserList.add(snapshot.getKey());

                    }

                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
                Log.i("LOGGGG", "hhhh");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        CrmAdminFragment.activeStatusReference.child("users").child(userType).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {
                activeUserList.clear();
                if (datasnapshot.child("activeV2/users").child(userType).exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        activeUserList.add(snapshot.getKey());

                    }

                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
                Log.i("LOGGGG", "hhhh");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCrmData() {
        crm = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
        crm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    TrackUserModel user = userSnapshot.getValue(TrackUserModel.class);
                    trackUserModelList.add(user);
                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CrmAdminFragment.activeStatusReference.child("users").child(userType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    activeUserList.add(snapshot.getKey());

                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getDataEntryOperatorData() {
        de = FirebaseDatabase.getInstance().getReference("usersv2").child("data");

        de.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    TrackUserModel user = snapshot.getValue(TrackUserModel.class);
                    trackUserModelList.add(user);
                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        CrmAdminFragment.activeStatusReference.child("users").child(userType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    activeUserList.add(snapshot.getKey());

                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
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
        Query query = ve.child("video");
        ///
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    TrackUserModel user = snapshot.getValue(TrackUserModel.class);
                    trackUserModelList.add(user);
                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        CrmAdminFragment.activeStatusReference.child("users").child(userType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    activeUserList.add(snapshot.getKey());

                }
                trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList, activeUserList, userClickCallback);
                binding.rvTrackUser.setAdapter(trackUserAdapter);
                trackUserAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onStart() {
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    default:
                        break;
                }

            }

        super.onStart();

    }

    @Override
    public void onPause() {
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    default:
                        break;
                }

            }
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    default:
                        break;
                }

            }
    }

    @Override
    public void onDestroy() {
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    default:
                        break;
                }

            }
        super.onDestroy();
    }

    @Override
    public void navigateToTaskList(String userPasscode) {
        Bundle bundle = new Bundle();
        bundle.putString("userPasscode", userPasscode);
        if (selectedAdmin == 1) {
            Navigation.findNavController(requireView()).navigate(R.id.action_alltrackusersfragment_to_taskListFragment, bundle);
        } else if(selectedAdmin == 2) {
            Navigation.findNavController(requireView()).navigate(R.id.action_trackuserscardviewfragment_to_taskListFragment2, bundle);
        }
    }
}
