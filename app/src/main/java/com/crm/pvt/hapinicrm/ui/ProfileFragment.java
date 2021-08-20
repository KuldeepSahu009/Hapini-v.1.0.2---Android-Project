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
            EditProfileFragment.usertype = usertype;
            Log.e(TAG, "usertype: " + usertype);
            if (usertype.equals("master")) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment);
                EditProfileFragment.usertype = usertype;
            } else if (usertype.equals("video")) {
                Navigation.findNavController(v).navigate(R.id.videoeditortoeditprofile);
            } else {
                Log.e(TAG, "usertype: " + usertype);
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
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        usertype = getshared.getString("type", "no data");
        String passcode = getshared.getString("passcode", "no data");
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
        }


    }

    private void getmasterdata() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Masterv2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String passcode = dataSnapshot.getKey();
                    if (passcode.equals("123456")) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                        String imgurl = dataSnapshot.child("imgurl").getValue().toString();

                        Log.e(TAG, "onDataChange: " + imgurl);

                        Glide.with(view).load(imgurl).into(ivProfilePic);
                        tvProfileName.setText(name);
                        tvProfileEmail.setText(email);
                        tvProfilePasscode.setText(passcode);
                        EditProfileFragment.previouspasscode = passcode;
                        EditProfileFragment.previouspassword = password;


                        tvProfilePassword.setText(password);
                    }
                }

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
                    Log.e(TAG, "onDataChange: " + key);
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
                        EditProfileFragment.previouspasscode = passcode;
                        EditProfileFragment.previouspassword = password;


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
                        EditProfileFragment.previouspasscode = passcode;
                        EditProfileFragment.previouspassword = password;

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
                        EditProfileFragment.previouspasscode = passcode;
                        EditProfileFragment.previouspassword = password;

                        tvProfilePassword.setText(password);


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
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
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
    public void onResume() {
        super.onResume();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
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
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
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
    public void onDestroy() {
        super.onDestroy();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
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
    }
}