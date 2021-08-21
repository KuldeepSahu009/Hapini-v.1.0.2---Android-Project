package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminDataViewFragment extends Fragment implements Datacallbacktrackuser {

    FragmentAdminDataViewBinding binding;
    TrackAdminAdapter trackAdminAdapter;
    ArrayList<Admin> adminList = new ArrayList<>();
    private List<String> activeUserList;
    String admin;
    public static String type = "";
    String adminpasscode;
    String adminpassword;
    private static final String TAG = "TAG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDataViewBinding.inflate(inflater, container, false);
        admin = getArguments().getString("ADMIN");
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        adminpasscode=getshared.getString("passcode","no data");
        adminpassword=getshared.getString("password","no data");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.trackAdminRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adminList = new ArrayList<>();
        activeUserList = new ArrayList<>();
        trackAdminAdapter = new TrackAdminAdapter(getContext(), adminList, activeUserList,this);
        binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);

        Snackbar.make(view, "Loading data please wait ", Snackbar.LENGTH_SHORT).show();

        switch (admin) {
            case "crm":
                type = "CRM";
                getCrmAdminData();
                break;
            case "video_editor":
                type = "VIDEO_EDITOR";
                getVideoEditorAdminData();
                break;
            case "data_entry":
                type = "DATA_ENTRY";
                getDataEntryAdminData();
                break;
        }
        CrmAdminFragment.activeStatusReference.child("admins").child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    activeUserList.add(snapshot.getKey());

                }
                trackAdminAdapter = new TrackAdminAdapter(getContext(), adminList, activeUserList , AdminDataViewFragment.this);
                binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                trackAdminAdapter.notifyDataSetChanged();
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
                    if(datasnapshot.child("activeV2/admins").child(type).exists())
                    {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            activeUserList.add(snapshot.getKey());

                        }

                    }
                    trackAdminAdapter = new TrackAdminAdapter(getContext(),adminList,activeUserList,AdminDataViewFragment.this);
                    binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                    trackAdminAdapter.notifyDataSetChanged();
                    Log.i("LOGGGG","hhhh");
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            CrmAdminFragment.activeStatusReference.child("admins").child(type).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {
                    activeUserList.clear();
                    if(datasnapshot.child("activeV2/admins").child(type).exists())
                    {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            activeUserList.add(snapshot.getKey());

                        }

                    }
                    trackAdminAdapter = new TrackAdminAdapter(getContext(),adminList,activeUserList,AdminDataViewFragment.this);
                    binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);
                    trackAdminAdapter.notifyDataSetChanged();
                    Log.i("LOGGGG","hhhh");
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        binding.etSearchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shorteddataadmin.type=type;
                Navigation.findNavController(v).navigate(R.id.movetoshorteddataadminfragment);
            }
        });


    }


    void getCrmAdminData() {

        TrackAdminAdapter.usertyepes = type;
        DatabaseReference crmReference;
        crmReference = FirebaseDatabase.getInstance().getReference("adminV2");
        crmReference.child("CRM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Admin adminObject;
                    adminObject = new Admin(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getVideoEditorAdminData() {

        TrackAdminAdapter.usertyepes = type;
        DatabaseReference videoEditorReference;
        videoEditorReference = FirebaseDatabase.getInstance().getReference("adminV2");
        videoEditorReference.child("VIDEO_EDITOR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Admin adminObject;
                    adminObject = new Admin(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getDataEntryAdminData() {

        Log.e(TAG, "getDataEntryAdminData: " + "dataenrty");
        TrackAdminAdapter.usertyepes = type;
        DatabaseReference dataEntryReference;
        dataEntryReference = FirebaseDatabase.getInstance().getReference("adminV2");
        dataEntryReference.child("DATA_ENTRY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Admin adminObject;
                    adminObject = new Admin(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void remove(Admin admin, String usertype) {
        Log.e(TAG, "remove: " + usertype);
        String postString = "";
        if (usertype == "CRM") {
            postString = "@crmadmin.com";
        }
        if (usertype == "VIDEO_EDITOR") {
            postString = "@veadmin.com";
        }
        if (usertype == "DATA_ENTRY") {
            postString = "@deadmin.com";
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype);
        String finalPostString = postString;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    String passcode = dataSnapshot.child("passcode").getValue().toString();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (admin!=null){
                    auth.signInWithEmailAndPassword(
                            admin.getPasscode() + finalPostString,
                            admin.getPassword()
                    ).addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            auth.getCurrentUser().delete();
                        }
                        auth.signInWithEmailAndPassword(adminpasscode + "@masteradmin.com",adminpassword);
                    });}

                    if (passcode.equals(admin.getPasscode())) {
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype)
                                .child(key);
                        if(currentFranchise != null) {
                            DatabaseReference franchiseDbRef = FirebaseDatabase
                                    .getInstance()
                                    .getReference("crm_by_franchise")
                                    .child(currentFranchise.getPasscode());

                            franchiseDbRef.child(admin.getPasscode()).removeValue();
                        }
                        reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                              // TODO This toast too keeps crashing
                             //   Toast.makeText(requireActivity().getParent(), "admin deleted", Toast.LENGTH_LONG).show();
                                type = usertype;
                                adminList.clear();
                                switch (usertype) {
                                    case "DATA_ENTRY":
                                        getDataEntryAdminData();
                                        break;
                                    case "VIDEO_EDITOR":
                                        getVideoEditorAdminData();
                                        break;
                                    case "CRM":
                                        getCrmAdminData();
                                        break;
                                }

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