package com.crm.pvt.hapinicrm.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private static final String TAG = "TAG";
    String usertype;
    public static String user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    public ImageView ivProfilePic;
    public TextView tvProfileName, tvProfileEmail, tvProfilePasscode, tvProfilePassword, edittext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromProfile).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );

        edittext.setOnClickListener(v -> {
           // EditProfileFragment.usertype = usertype;
            Log.e(TAG, "usertype: " + usertype);
            if (usertype.equals("master")) {
                EditProfileFragment.user="";
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment);
               // EditProfileFragment.usertype = usertype;
            } else if (usertype.equals("video")) {
                EditProfileFragment.user="";
                Navigation.findNavController(v).navigate(R.id.videoeditortoeditprofile);
            } else if(usertype.equals("crmuser")){
                    EditProfileFragment.user="crmuser";
                Navigation.findNavController(v).navigate(R.id.crmusermovetoeditprofile);
            }
            else {
                Log.e(TAG, "usertype: " + usertype);
                EditProfileFragment.user="";
                Navigation.findNavController(v).navigate(R.id.movetoeditprofilefragment);
            }

        });


    }

    private void initializeAllUIComponents(View view) {
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvProfilePassword = view.findViewById(R.id.tvProfilePassword);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfilePasscode = view.findViewById(R.id.tvProfilePasscode);
        edittext = view.findViewById(R.id.tvProfileEditText);
        getprofileinfo();
    }


    private void getprofileinfo() {
        String passcode;
        if (user.equals("user")){
        SharedPreferences getshared = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        usertype = getshared.getString("type", "no data");
        passcode = getshared.getString("passcode", "no data");}
        else{
            SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
            usertype = getshared.getString("type", "no data");
            passcode = getshared.getString("passcode", "no data");
        }
        Log.e(TAG, "getprofileinfo: " + usertype);
        switch (usertype) {
            case "crm":
                //Log.e(TAG, "getprofileinfo: "+passcode);
                getcrmdata(passcode);
                break;
            case "data":
                getdataentrydata(passcode);
                break;
            case "video":
                getvideodata(passcode);
                break;
            case "master":
                usertype = "master";
                // Log.e(TAG, "getprofileinfo: "+"getmaster" );
                getmasterdata();
                break;
            case "franchise":
                Log.e(TAG, "getprofileinfo: "+"fracnhsie" );
                getfranchisedata(passcode);
                break;
            case "crmuser":
                getcrmuserprofile(passcode);
        }


    }
    private void getcrmuserprofile(String passcode){
        Log.e(TAG, "getcrmuserprofile: "+passcode );
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm").child(passcode);
               databaseReference .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                       String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                        Log.e(TAG, "onDataChange: "+imgurl );
                        if (!imgurl.equals("")){
                            Glide.with(getContext()).load(imgurl).into(ivProfilePic);
                        }
                        tvProfilePasscode.setText(passcode);
                        tvProfileEmail.setText(email);
                        tvProfileName.setText(name);
                        tvProfilePassword.setText(password);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getmasterdata() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Masterv2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String passcode = dataSnapshot.getKey();
                    if (passcode.equals("234567")) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                        String imgurl = dataSnapshot.child("imgurl").getValue().toString();

                        //Log.e(TAG, "onDataChange: " + imgurl);

                        Glide.with(view).load(imgurl).into(ivProfilePic);
                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
//                        EditProfileFragment.previouspasscode = passcode;
//                        EditProfileFragment.previouspassword = password;


                        tvProfilePassword.setText(password);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getfranchisedata(String passcode){
        DatabaseReference franchiseReference;
        franchiseReference = FirebaseDatabase.getInstance().getReference("franchiseV2").child(passcode);
        franchiseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String password = dataSnapshot.child("password").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                tvProfilePassword.setText(password);
                tvProfilePasscode.setText(passcode);
                tvProfileName.setText(name);
                tvProfileEmail.setText(email);
                Glide.with(getContext()).load(imgurl).into(ivProfilePic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getcrmdata(String passcodes) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child("CRM");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    //Log.e(TAG, "onDataChange: " + key);
                    String passcode = dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)) {
                        String password = dataSnapshot.child("password").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String imgurl = dataSnapshot.child("imgurl").getValue().toString();

                        if (!imgurl.equals("")) {
                            Glide.with(view).load(imgurl).into(ivProfilePic);
                        }

                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
//                        EditProfileFragment.previouspasscode = passcode;
//                        EditProfileFragment.previouspassword = password;


                        tvProfilePassword.setText(password);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getdataentrydata(String passcodes) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child("DATA_ENTRY");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    Log.e(TAG, "onDataChange: " + key);
                    String passcode = dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)) {
                        String password = dataSnapshot.child("password").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                        Log.e(TAG, "onDataChange: " + password + email);
                        if (!imgurl.equals("")) {
                            Glide.with(view).load(imgurl).into(ivProfilePic);
                        }

                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
//                        EditProfileFragment.previouspasscode = passcode;
//                        EditProfileFragment.previouspassword = password;

                        tvProfilePassword.setText(password);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getvideodata(String passcodes) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child("VIDEO_EDITOR");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    Log.e(TAG, "onDataChange: " + key);
                    String passcode = dataSnapshot.child("passcode").getValue().toString();
                    dataSnapshot.child("passcode").getValue().toString();
                    if (passcode.equals(passcodes)) {
                        String password = dataSnapshot.child("password").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                        Log.e(TAG,"Image Url : " + imgurl);
                        Log.e(TAG, "onDataChange: " + password + email);

                        if (!imgurl.equals("")) {
                            Glide.with(view).load(imgurl).into(ivProfilePic);
                        }

                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
//                        EditProfileFragment.previouspasscode = passcode;
//                        EditProfileFragment.previouspassword = password;

                        tvProfilePassword.setText(password);


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}