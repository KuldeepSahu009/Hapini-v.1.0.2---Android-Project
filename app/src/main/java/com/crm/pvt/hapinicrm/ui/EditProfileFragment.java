package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class EditProfileFragment extends Fragment {
    private View thisView;
    private EditText etProfileName, etProfileEmail, etProfilePasscode, etProfilePassword;
    private ImageView profileimg, savedetails;
    private CheckBox checkBox;
    public Uri ivProfilePicURI;
    private String TAG = "TAG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thisView = view;
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromEditMasterDetailFragment).setOnClickListener(v ->

                Navigation.findNavController(v).navigateUp()

        );
        view.findViewById(R.id.btnMasterDetailsChange).setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), 1000);
        });
        savedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + "save details");
                getdata(view);
            }
        });
    }

    private void initializeAllUIComponents(View view) {
        etProfileName = view.findViewById(R.id.etProfileName);
        etProfileEmail = view.findViewById(R.id.etProfileEmail);
        etProfilePasscode = view.findViewById(R.id.etProfilePasscode);
        etProfilePassword = view.findViewById(R.id.etProfilePassword);
        profileimg = view.findViewById(R.id.editprofileimg);
        savedetails = view.findViewById(R.id.ivSaveEditMasterDetailFragment);
        checkBox = view.findViewById(R.id.cvMasterDetailsTermsAndCondition);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ivProfilePicURI = data.getData();
                Glide.with(getContext()).load(ivProfilePicURI).into(profileimg);

            }
        }
    }

    private void getdata(View view) {
        String name = etProfileName.getText().toString();
        String email = etProfileEmail.getText().toString();
        String passcode = etProfilePasscode.getText().toString();
        String password = etProfilePassword.getText().toString();
        Log.e(TAG, "getdata: " + name + email + passcode + password);
        if (checkBox.isChecked()) {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(passcode) || TextUtils.isEmpty(password) || ivProfilePicURI == null) {
                Snackbar.make(view, "Please provide all the info", Snackbar.LENGTH_LONG).show();
            } else {
                setdata(name, email, passcode, password, ivProfilePicURI);
            }
        } else {
            Snackbar.make(view, "Please accept the terms and conditions", Snackbar.LENGTH_LONG).show();
        }


    }

    private void setdata(String name, String email, String passcode, String password, Uri imgurl) {
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating");
        progressDialog.show();


        //setpasscode will be updated in future

        if (imgurl != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("masterprofilepicv2");
            StorageReference filerefernece = storageReference.child(System.currentTimeMillis() + "");
            StorageTask uploadtask = filerefernece.putFile(imgurl);
            Task<Uri> urlTask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filerefernece.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.e(TAG, "onComplete: "+downloadUri.toString() );
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Masterv2");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("email", email);
                        hashMap.put("password", password);
                        hashMap.put("imgurl", downloadUri.toString());

                        reference.child(passcode).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(thisView, "profile setup done", Snackbar.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Navigation.findNavController(thisView).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Snackbar.make(thisView, "unableto setup profile", Snackbar.LENGTH_LONG).show();
                            }
                        });




                    } else {
                        // Handle failures
                        // ...
                        Snackbar.make(thisView,"unable to upload img",Snackbar.LENGTH_LONG).show();
                    }
                }
            });

        }



    }

}


