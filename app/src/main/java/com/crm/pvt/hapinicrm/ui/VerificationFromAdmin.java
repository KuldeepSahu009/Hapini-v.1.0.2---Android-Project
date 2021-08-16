package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentVerificationFromAdminBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class VerificationFromAdmin extends Fragment {

    FragmentVerificationFromAdminBinding binding;
    Uri imageUri, imageUri1, imageUri2;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificationFromAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.selectFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "Upload image of .jpg Format only" , Toast.LENGTH_SHORT).show();
                uploadImage("Aadhaar Front");
            }
        });

        binding.selectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "Upload image of .jpg Format only" , Toast.LENGTH_SHORT).show();
                uploadImage("Aadhaar Back");
            }
        });

        binding.selectPanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "Upload image of .jpg Format only" , Toast.LENGTH_SHORT).show();
                uploadImage("Pan Card");
            }
        });
        binding.uploadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });
    }

    private void uploadImage(String s) {
        Intent intent = new Intent();
        intent.setType("image/jpg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (s.equals("Aadhaar Front")) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        }
        if (s.equals("Aadhaar Back")) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
        }
        if (s.equals("Pan Card")) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(requireContext()).load(imageUri).into(binding.imageFront);
            binding.imageFront.setBackgroundResource(0);
        }
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            imageUri1 = data.getData();
            Glide.with(requireContext()).load(imageUri1).into(binding.imageBack);
            binding.imageBack.setBackgroundResource(0);
        }
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            imageUri2 = data.getData();
            Glide.with(requireContext()).load(imageUri2).into(binding.imagePanCard);
            binding.imagePanCard.setBackgroundResource(0);
        }
    }

    private void uploadToFirebase() {
        if (imageUri != null) {

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Please wait...");
            progressDialog.show();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Verification Of Documents From Master V2").child(binding.editTextName.getText().toString());
            storageReference.child(binding.editTextPasscode.getText().toString()).child("Aadhaar Card Front").putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2");
                                    databaseReference.child(binding.editTextName.getText().toString()).child(binding.editTextPasscode.getText().toString()).child("Aadhaar Card Front").setValue(uri.toString());
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Aadhaar Card Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to Upload!!", Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
        if (imageUri1!= null) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Verification Of Documents From Master V2").child(binding.editTextName.getText().toString());
            storageReference.child(binding.editTextPasscode.getText().toString()).child("Aadhaar Card Back").putFile(imageUri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2");
                                    databaseReference.child(binding.editTextName.getText().toString()).child(binding.editTextPasscode.getText().toString()).child("Aadhaar Card Back").setValue(uri.toString());
                                }
                            });
                            Toast.makeText(getContext(), "Aadhaar Card Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed to upload!!", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (imageUri2 != null) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Verification Of Documents From Master V2").child(binding.editTextName.getText().toString());
            storageReference.child(binding.editTextPasscode.getText().toString()).child("Pan Card Front").putFile(imageUri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2");
                                    databaseReference.child(binding.editTextName.getText().toString()).child(binding.editTextPasscode.getText().toString()).child("Pan Card Front").setValue(uri.toString());
                                    Navigation.findNavController(requireView()).navigateUp();
                                }
                            });
                            Toast.makeText(getContext(), "Pan Card Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed to Upload!!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireView()).navigateUp();
                }
            });
        }
    }
    @Override
    public void onStart() {
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onPause() {
        if(Splashscreen.spUsersData != null)
            if(!Splashscreen.spUsersData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("users").child("crm")
                        .child(Splashscreen.spUsersData.getString("passcode","null")).removeValue();
        super.onPause();

    }

    //Remove it after adding logout functionality
    @Override
    public void onDestroy() {
        super.onDestroy();
        Splashscreen.spUsersData.edit().clear().commit();
    }
}