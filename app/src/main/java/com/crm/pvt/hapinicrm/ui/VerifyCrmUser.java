package com.crm.pvt.hapinicrm.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
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

//public static String getMimeType(String url) {
//        String type = null;
//        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
//        if (extension != null) {
//        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        }
//        return type;
//        }

public class VerifyCrmUser extends Fragment {

    FragmentVerifyCrmUserBinding binding;
    String name;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    boolean isExitingFrag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerifyCrmUserBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        name = getArguments().getString("NAME");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpImages();
        binding.verifyUser.setOnClickListener(v -> {
            isExitingFrag =true;
            FirebaseDatabase.getInstance().getReference().child("Verification Of Documents From Master V2").child(name).removeValue();
            FirebaseStorage.getInstance().getReference().child("Verification Of Documents From Master V2").child(name).delete();
            Toast.makeText(getContext(), name + " is Verified", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(VerifyCrmUserDirections.actionVerificationOfUserToCrmAdminFragment());
        });

    }

    private void setUpImages() {

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
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        passCode[0] = dataSnapshot.getKey();
                    if (!isExitingFrag)
                    getImages(passCode[0] , localFile ,localFile1 ,localFile2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    boolean thirdFlag = false;
    private void getImages(String passcode , File localFile , File localFile1 , File localFile2)
    {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading Images Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();

        storageReference.child(passcode).child("Aadhaar Card Front").getFile(localFile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                binding.imgFrontSide.setImageBitmap(bitmap);
                binding.imgFrontSide.setBackgroundResource(0);
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                double progress = 100.0 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount();
                progressDialog.setMessage((int)progress+"% Downloaded.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.setMessage(e.getMessage());
                progressDialog.dismiss();
            }
        });



            storageReference.child(passcode).child("Aadhaar Card Back").getFile(localFile1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile1.getAbsolutePath());
                    binding.imgBackSide.setImageBitmap(bitmap);
                    binding.imgBackSide.setBackgroundResource(0);
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                    double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    if(!progressDialog.isShowing())
                    {
                        progressDialog.show();
                        thirdFlag =true;
                        progressDialog.setMessage((int) progress+"% downloaded.");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.setMessage(e.getMessage());
                    progressDialog.dismiss();
                }
            });



            storageReference.child(passcode).child("Pan Card Front").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.imgPanCard.setImageBitmap(bitmap);
                    binding.imgPanCard.setBackgroundResource(0);
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                    double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    if(!progressDialog.isShowing() && thirdFlag) {
                        progressDialog.show();
                        thirdFlag = false;
                        progressDialog.setMessage((int) progress+"% downloaded.");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.setMessage(e.getMessage());
                    progressDialog.dismiss();
                }
            });

    }
    @Override
    public void onStart() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null"))
                        .setValue("active");
    }

    @Override
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
                CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                        .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null"))
            CrmAdminFragment.activeStatusReference.child("admins").child("CRM")
                    .child(Splashscreen.spAdminsData.getString("passcode","null")).removeValue();
    }

}