package com.crm.pvt.hapinicrm.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
        name = getArguments().getString("NAME");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpImages(view);
        binding.verifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2").child(name).removeValue();
                Toast.makeText(getContext(), name + " is Verified", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigateUp();
            }
        });

    }

    private void setUpImages(View v) {

        try {
            File localFile1 = File.createTempFile("Aadhaar Card Back", "jpg");
            File localFile2 = File.createTempFile("Aadhaar Card Front", "jpg");
            File localFile = File.createTempFile("Pan Card Front", "jpg");
            storageReference = FirebaseStorage.getInstance().getReference().child("Verification Of Documents From Master V2").child(name);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2").child(name);
            final String[] passCode = new String[1];
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        passCode[0] = dataSnapshot.getKey();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (passCode[0] != null) {
                storageReference.child(passCode[0]).child("Aadhaar Card Back/jpg").getFile(localFile1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile1.getAbsolutePath());
                        binding.imgFrontSide.setImageBitmap(bitmap);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                        Toast.makeText(getContext(), name + " " + passCode[0], Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Loading data..!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to load data!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (passCode[0] != null) {
                storageReference.child(passCode[0]).child("Aadhaar Card Front/jpg").getFile(localFile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                        binding.imgBackSide.setImageBitmap(bitmap);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                        Toast.makeText(getContext(), "Loading data..!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to load data!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (passCode[0] != null) {
                storageReference.child(passCode[0]).child("Pan Card Front/jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        binding.imgPanCard.setImageBitmap(bitmap);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                        Toast.makeText(getContext(), "Loading data..!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to load data!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
        catch(IOException e){
            e.printStackTrace();
        }

        
    }

}