package com.crm.pvt.hapinicrm.ui;

import static android.app.Activity.RESULT_OK;

import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import android.provider.OpenableColumns;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmadmincsvfilesendBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class crmadmincsvfilesend extends Fragment {
    Uri imageuri = null;
    String displayName = null;
    DatabaseReference databaseReference;
    private FragmentCrmadmincsvfilesendBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCrmadmincsvfilesendBinding.inflate(inflater, container, false);
    return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding.selectcsvfile.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("text/csv");
            startActivityForResult(intent,1);
        });
        binding.submitcsvtouser.setOnClickListener(v -> {
            String passcode=binding.crmuserpasscodecsv.getText().toString();
            String filename=binding.selectcsvfile.getText().toString();
            if(!filename.equals("Send Csv File To Crm User")&&(passcode.isEmpty()==false)){
                uploadtofirebase(displayName);
            }
            else {
                Toast.makeText(getContext(),"Wrong Details",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadtofirebase(String displayName) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("usersv2").child("crm");
        storageReference.child(binding.crmuserpasscodecsv.getText().toString()).child(displayName).putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("usersv2");
                                databaseReference.child("crm").child(binding.crmuserpasscodecsv.getText().toString()).child("CSV FILE").setValue(uri.toString());
                            }
                        });

                        Toast.makeText(getContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigateUp();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Failed to Upload!!", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageuri = data.getData();
            String uriString = imageuri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(imageuri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        binding.selectcsvfile.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                binding.selectcsvfile.setText(displayName);
            }


        }
    }
}