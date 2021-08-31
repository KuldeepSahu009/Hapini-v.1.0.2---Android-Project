package com.crm.pvt.hapinicrm.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.adapters.TrackFranchiseAdapter;
import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentShorteddataadminBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Shorteddataadmin extends Fragment implements View.OnClickListener{
    FragmentShorteddataadminBinding fragmentShorteddataadminBinding;

    EditText search;
    static String type;
    String adminpasscode;
    String adminpassword;
    RecyclerView recyclerView;
    ImageView back;
    TextView shortbyname,addedby;
    TextView shortbylocation;
    TextView shortbycity;
    String shortvariable;
    private static final String TAG = "TAG";
    TrackAdminAdapter trackAdminAdapter;
    TrackUserAdapter trackUserAdapter;
    List<TrackUserModel> trackUserModelList;
    ArrayList<Admin> adminList;
    ArrayList<Franchise> franchiseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentShorteddataadminBinding= FragmentShorteddataadminBinding.inflate(inflater, container, false);

        search=fragmentShorteddataadminBinding.searchadmins;
        back=fragmentShorteddataadminBinding.shoretddataback;
        franchiseList=new ArrayList<>();
        initializeall();
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        adminpasscode=getshared.getString("passcode","no data");
        adminpassword=getshared.getString("password","no data");


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adminList=new ArrayList<>();
        trackAdminAdapter=new TrackAdminAdapter(getContext(),  adminList,datacallbacktrackuser);
        trackUserModelList=new ArrayList<>();
        trackUserAdapter =new TrackUserAdapter(getContext(),trackUserModelList);
        recyclerView.setAdapter(trackAdminAdapter);
        if (type.equals("franchise")){
            addedby.setVisibility(View.INVISIBLE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onCreateView: "+type );
                getsearchdata(s.toString(), type);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return fragmentShorteddataadminBinding.getRoot();
    }
    private void initializeall(){
        search=fragmentShorteddataadminBinding.searchadmins;
        back=fragmentShorteddataadminBinding.shoretddataback;
        addedby=fragmentShorteddataadminBinding.shortedbyaddedby;
        shortbyname=fragmentShorteddataadminBinding.shortedbyname;
        shortbycity=fragmentShorteddataadminBinding.shortedbycity;
        shortbylocation=fragmentShorteddataadminBinding.shortedbylocation;
        recyclerView=fragmentShorteddataadminBinding.showshorteddata;

        shortbyname.setOnClickListener(this::onClick);
        shortbycity.setOnClickListener(this::onClick);
        shortbylocation.setOnClickListener(this::onClick);
        addedby.setOnClickListener(this::onClick);




    }

    private void getsearchdata(String s, String usertypes) {
        /// Log.e(TAG, "getsearchdata: "+usertypes );
        //Log.e(TAG, "getsearchdata: "+type );
        switch (usertypes) {
            case "CRM":
                // Log.e(TAG, "getsearchdata: "+ s+usertypes);
                recyclerView.setAdapter(trackAdminAdapter);
                getcrmshortedata(s, usertypes);
                break;
            case "DATA_ENTRY":
                Log.e(TAG, "getsearchdata: "+ s+usertypes);
                recyclerView.setAdapter(trackAdminAdapter);
                getdataentryshorteddata(s, usertypes);
                break;
            case "VIDEO_EDITOR":
                //Log.e(TAG, "getsearchdata: "+ s+usertypes);
                recyclerView.setAdapter(trackAdminAdapter);
                getvideoeditorshorteddata(s, usertypes);
                break;
            case "crm":
                getcrmusershorteddata(s,"crm");
                recyclerView.setAdapter(trackUserAdapter);
                break;
            case "video":
                getcrmusershorteddata(s,"video");
                recyclerView.setAdapter(trackUserAdapter);
                break;
            case "data":
                getcrmusershorteddata(s,"data");
                recyclerView.setAdapter(trackUserAdapter);
                break;
            case "franchise":
                Log.e(TAG, "getsearchdata: "+s.toString()+usertypes );
                getFranchiseSort(s);




        }

    }
    private void getcrmusershorteddata(String searchout,String usertype){
        if (shortvariable.equals("location")){
            shortvariable="locality";
        }
        Log.e(TAG, "getcrmusershorteddata: "+searchout );
        Query query=FirebaseDatabase.getInstance().getReference("usersv2").child(usertype).orderByChild(shortvariable)
                .startAt(searchout).endAt(searchout+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackUserModelList.clear();
                //Log.e(TAG, "onDataChange: "+snapshot.getKey() );
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){



                    String name=dataSnapshot.child("name").getValue().toString();
                    String email=dataSnapshot.child("email").getValue().toString();
                    String mobileno=dataSnapshot.child("mobileNo").getValue().toString();
                    String whatsappno=dataSnapshot.child("whatsAppNo").getValue().toString();
                    String passcode=dataSnapshot.child("passcode").getValue().toString();
                    String password=dataSnapshot.child("password").getValue().toString();
                    String state = dataSnapshot.child("state").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String location=dataSnapshot.child("locality").getValue().toString();
                    String addedBy =dataSnapshot.child("addedBy").getValue().toString();
                    trackUserModelList.add(new TrackUserModel(name,email,mobileno,whatsappno,passcode,password,state , city , location, addedBy , ""));


                    Log.e(TAG, "onDataChange: "+name+email );

                }
                trackUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shortedbyname:
                shortvariable="name";
                setalldisable();
                shortbyname.setTextColor(getResources().getColor(R.color.blue));
                shortbyname.setBackground( getResources().getDrawable(R.drawable.selectedshortedbuttonback));
                break;

            case R.id.shortedbycity:
                shortvariable="city";
                setalldisable();
                shortbycity.setTextColor(getResources().getColor(R.color.blue));
                shortbycity.setBackground( getResources().getDrawable(R.drawable.selectedshortedbuttonback));
                break;
            case R.id.shortedbylocation:
                shortvariable="location";
                setalldisable();
                shortbylocation.setTextColor(getResources().getColor(R.color.blue));
                shortbylocation.setBackground( getResources().getDrawable(R.drawable.selectedshortedbuttonback));
                break;

            case R.id.shortedbyaddedby:
                shortvariable="addedBy";
                setalldisable();
                addedby.setTextColor(getResources().getColor(R.color.blue));
                addedby.setBackground( getResources().getDrawable(R.drawable.selectedshortedbuttonback));


        }

    }

    private void setalldisable(){
        shortbylocation.setTextColor(getResources().getColor(R.color.black));
        shortbyname.setTextColor(getResources().getColor(R.color.black));
        shortbycity.setTextColor(getResources().getColor(R.color.black));
        addedby.setTextColor(getResources().getColor(R.color.black));

        shortbycity.setBackground( getResources().getDrawable(R.drawable.shortedbuttonback));
        shortbyname.setBackground( getResources().getDrawable(R.drawable.shortedbuttonback));
        shortbylocation.setBackground( getResources().getDrawable(R.drawable.shortedbuttonback));
        addedby.setBackground( getResources().getDrawable(R.drawable.shortedbuttonback));




    }
    private void getcrmshortedata(String s, String usertype) {
        Log.e(TAG, "getshortedata: " + s + usertype+shortvariable);
        Query query = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild(shortvariable)
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
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
                            dataSnapshot.child("state").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString(),
                            dataSnapshot.child("addedBy").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getdataentryshorteddata(String s, String usertype) {
        Log.e(TAG, "getdataentryshorteddata: " + s + usertype);
        Query query = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild(shortvariable)
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
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
                            dataSnapshot.child("state").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString(),
                            dataSnapshot.child("addedBy").getValue().toString());
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getvideoeditorshorteddata(String s, String usertype) {
        Log.e(TAG, "getvideoeditorshorteddata: " + s + usertype);
        Query query = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype).orderByChild(shortvariable)
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
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
                            dataSnapshot.child("state").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString(),
                            dataSnapshot.child("addedBy").getValue().toString());
                    Log.e(TAG, "onDataChange: "+dataSnapshot.child("name").getValue().toString() );
                    adminList.add(adminObject);
                }
                trackAdminAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    Datacallbacktrackuser datacallbacktrackuser=new Datacallbacktrackuser() {
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
                            reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(getContext(), "admin deleted", Toast.LENGTH_LONG).show();



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
    };
    private void getFranchiseSort(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("franchiseV2").orderByChild(shortvariable)
                .startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                franchiseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Franchise franchiseObject;
                    franchiseObject = new Franchise(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phoneno").getValue().toString(),
                            dataSnapshot.child("whatsappno").getValue().toString(),
                            dataSnapshot.child("passcode").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("state").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            dataSnapshot.child("location").getValue().toString(),
                            dataSnapshot.child("imgurl").getValue().toString());
                    //dataSnapshot.child("addedBy").getValue().toString();
                    franchiseList.add(franchiseObject);
                }
                TrackFranchiseAdapter trackFranchiseAdapter=new TrackFranchiseAdapter(getContext(),franchiseList,dataCallBackTackFranchise);
                recyclerView.setAdapter(trackFranchiseAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    DataCallBackTackFranchise dataCallBackTackFranchise=new DataCallBackTackFranchise() {
        @Override
        public void remove(Franchise franchise) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("franchiseV2");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getKey();

                        if (key.equals(franchise.getPasscode())) {
                            DatabaseReference reference1 = reference.child(key);
                            reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(getContext(), "franchise deleted", Toast.LENGTH_LONG).show();
                                    franchiseList.clear();

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
    };


}