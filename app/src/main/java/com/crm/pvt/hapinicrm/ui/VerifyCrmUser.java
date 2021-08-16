package com.crm.pvt.hapinicrm.ui;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_LONG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.VerificationRequestsAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentVerifyCrmUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VerifyCrmUser extends Fragment {

    FragmentVerifyCrmUserBinding binding;
    String name;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerifyCrmUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpImages(view);
        binding.verifyUser.setOnClickListener(v -> {
                FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2").child(name);
                Toast.makeText(getContext(), name + " is Verified", Toast.LENGTH_SHORT).show();


        });
        binding.backButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(VerifyCrmUserDirections.actionVerificationOfUserToCrmAdminFragment());
        });

    }

    private void setUpImages(View v) {
DatabaseReference myref2=FirebaseDatabase.getInstance().getReference("Verification Of Documents From Master V2");
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String getimagelinkfront = dataSnapshot.child("passcodess").child("Aadhaar Card Front").getValue(String.class);
                        String getimagelinkback=dataSnapshot.child("passcodess").child("Aadhaar Card Back").getValue(String.class);
                        String getpancardlink=dataSnapshot.child("passcodess").child("Pan Card Front").getValue(String.class);
                        Glide.with(getContext()).load(getimagelinkfront).into(binding.imgFrontSide);
                    Glide.with(getContext()).load(getimagelinkback).into(binding.imgBackSide) ;
                    Glide.with(getContext()).load(getpancardlink).into(binding.imgPanCard) ;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        onPause();
    }



}