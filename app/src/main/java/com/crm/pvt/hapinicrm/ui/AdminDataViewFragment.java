package com.crm.pvt.hapinicrm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDataViewFragment extends Fragment implements Datacallbacktrackuser {

    FragmentAdminDataViewBinding binding;
    TrackAdminAdapter trackAdminAdapter;
    ArrayList<Admin> adminList = new ArrayList<>();
    String admin;
    public static String type = "";
    private static final String TAG = "TAG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDataViewBinding.inflate(inflater , container , false);
        admin = getArguments().getString("ADMIN");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.trackAdminRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adminList=new ArrayList<>();
        trackAdminAdapter = new TrackAdminAdapter(getContext(),adminList,this);
        binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);

        Snackbar.make(view,"Loading data please wait ",Snackbar.LENGTH_SHORT).show();

        switch (admin) {
            case "crm":
                getCrmAdminData();
                break;
            case "video_editor":
                getVideoEditorAdminData();
                break;
            case "data_entry":
                getDataEntryAdminData();
                break;
        }

        binding.etSearchAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               getsearchdata(s.toString(),type);

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }


     void getCrmAdminData() {
        type = "CRM";
         TrackAdminAdapter.usertyepes=type;
        DatabaseReference crmReference;
        crmReference = FirebaseDatabase.getInstance().getReference("adminV2");
        crmReference.child("CRM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getVideoEditorAdminData() {
        type = "VIDEO_EDITOR";
        TrackAdminAdapter.usertyepes=type;
        DatabaseReference videoEditorReference;
        videoEditorReference = FirebaseDatabase.getInstance().getReference("adminV2");
        videoEditorReference.child("VIDEO_EDITOR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getDataEntryAdminData() {
        type = "DATA_ENTRY";
        Log.e(TAG, "getDataEntryAdminData: "+"dataenrty" );
        TrackAdminAdapter.usertyepes=type;
        DatabaseReference dataEntryReference;
        dataEntryReference = FirebaseDatabase.getInstance().getReference("adminV2");
        dataEntryReference.child("DATA_ENTRY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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
                Toast.makeText( getContext() , error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void remove(Admin admin,String usertype) {
        Log.e(TAG, "remove: "+usertype );
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child(usertype);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String key=dataSnapshot.getKey();

                    String passcode=dataSnapshot.child("passcode").getValue().toString();
                   ;
                    if (passcode.equals(admin.getPasscode())){
                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("adminV2").child(usertype)
                                .child(key);
                        reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                             Toast.makeText(getContext(),"admin deleted",Toast.LENGTH_LONG).show();
                             type=usertype;
                             adminList.clear();
                             switch (usertype){
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
    private void getsearchdata(String s,String usertypes){
        switch (usertypes){
            case "CRM":
            getcrmshortedata(s,usertypes);
            break;
            case  "DATA_ENTRY":
                getdataentryshorteddata(s,usertypes);
            case "VIDEO_EDITOR":
                getvideoeditorshorteddata(s,usertypes);
        }

    }
    private void getcrmshortedata(String s,String usertype){
        Log.e(TAG, "getshortedata: "+s+usertype );
        Query query=FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild("name")
                .startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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

            }
        });
    }
    private void getdataentryshorteddata(String s,String usertype){
        Log.e(TAG, "getdataentryshorteddata: "+s+usertype );
        Query query=FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild("name")
                .startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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

            }
        });
    }
    private void getvideoeditorshorteddata(String s,String usertype){
        Log.e(TAG, "getvideoeditorshorteddata: "+s+usertype );
        Query query=FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild("name")
                .startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Admin adminObject;
                    adminObject = new Admin( dataSnapshot.child("name").getValue().toString() ,
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

            }
        });
    }
}