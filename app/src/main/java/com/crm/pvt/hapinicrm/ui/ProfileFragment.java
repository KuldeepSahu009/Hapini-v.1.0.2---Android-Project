package com.crm.pvt.hapinicrm.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private static final String TAG = "TAG";
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public ImageView ivProfilePic;
    public  TextView tvProfileName, tvProfileEmail, tvProfilePasscode, tvProfilePassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromProfile).setOnClickListener(v ->
                {
                    Navigation.findNavController(v).navigateUp();
                }
        );

        view.findViewById(R.id.tvProfileEditText).setOnClickListener(
                v ->
                        Navigation.findNavController(v).navigate(R.id.movetoeditprofilefragment)
        );
    }

    private void initializeAllUIComponents(View view) {
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvProfilePassword = view.findViewById(R.id.tvProfilePassword);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfilePasscode = view.findViewById(R.id.tvProfilePasscode);
        getprofileinfo();
    }



    private void getprofileinfo(){
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
       String usertype = getshared.getString("type", "no data");
       String passcode=getshared.getString("passcode","no data");
        Log.e(TAG, "getprofileinfo: "+usertype );
        switch (usertype){
            case "crm":
                //Log.e(TAG, "getprofileinfo: "+passcode);

                getcrmdata(passcode);
                break;
            case  "data":
                getdataentrydata(passcode);
                break;
            case "video":
                getvideodata(passcode);


                break;

            case "master":
               // Log.e(TAG, "getprofileinfo: "+"getmaster" );
                getmasterdata();
                break;
        }


    }
    private void getmasterdata(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Masterv2");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                       String passcode=dataSnapshot.getKey();
                       if (passcode.equals("123456")) {
                           String name = dataSnapshot.child("name").getValue().toString();
                           String email=dataSnapshot.child("email").getValue().toString();
                           String password=dataSnapshot.child("password").getValue().toString();
                           String imgurl=dataSnapshot.child("imgurl").getValue().toString();

                           Log.e(TAG, "onDataChange: "+imgurl );

                           Glide.with(getContext()).load(imgurl).into(ivProfilePic);
                           tvProfileName.setText(name);
                           tvProfileEmail.setText(email);
                           tvProfilePasscode.setText(passcode);
                           tvProfilePassword.setText(password);
                       }
                   }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
    private void getcrmdata(String passcodes){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child("CRM");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    Log.e(TAG, "onDataChange: "+key );
                   String passcode=dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)){
                        String password=dataSnapshot.child("password").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String imgurl=dataSnapshot.child("imgurl").getValue().toString();

                        if (!   imgurl.equals("")){
                            Glide.with(getContext()).load(imgurl).into(ivProfilePic);
                        }

                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
                        tvProfilePassword.setText(password);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getdataentrydata(String passcodes){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child("DATA_ENTRY");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    Log.e(TAG, "onDataChange: "+key );
                    String passcode=dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)){
                        String password=dataSnapshot.child("password").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String imgurl=dataSnapshot.child("imgurl").getValue().toString();
                        Log.e(TAG, "onDataChange: "+password+email );


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void getvideodata(String passcodes){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child("VIDEO_EDITOR");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    Log.e(TAG, "onDataChange: "+key );
                    String passcode=dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)){
                        String password=dataSnapshot.child("password").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String imgurl=dataSnapshot.child("imgurl").getValue().toString();
                        Log.e(TAG, "onDataChange: "+password+email );


                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}