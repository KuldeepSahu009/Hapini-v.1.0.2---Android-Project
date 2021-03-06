package com.crm.pvt.hapinicrm.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentCsvfilefromfranchiseBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentMasterDashboardBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentMastertofranchiseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class Mastertofranchise extends Fragment {

    private FragmentMastertofranchiseBinding binding;
    Uri imageuri;
    String displayName;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMastertofranchiseBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        binding.submitcsvfilefranchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcode=binding.passcodeforcsvfilefranchise.getText().toString();
                String filename=binding.csvfileselectfranchise.getText().toString();
                if(!filename.equals("Select The Csv File To Crm Admin")&&(passcode.isEmpty()==false)){
                    uploadtofirebase(displayName);
                }
                else {
                    Toast.makeText(getContext(),"Wrong Details",Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.csvfileselectfranchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                startActivityForResult(intent,1);

            }
        });
    }
    private void uploadtofirebase(String displayName) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("CSVFILEFROMFRANCHISE");
        storageReference.child(binding.passcodeforcsvfilefranchise.getText().toString()).child(displayName).putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("MASTERTOFRANCHISE");
                                databaseReference.child(binding.passcodeforcsvfilefranchise.getText().toString()).child("CSV FILE").setValue(uri.toString());
                                databaseReference.child(binding.passcodeforcsvfilefranchise.getText().toString()).child("Csv File name").setValue(displayName);
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
                        binding.csvfileselectfranchise.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                binding.csvfileselectfranchise.setText(displayName);
            }


        }
    }
}