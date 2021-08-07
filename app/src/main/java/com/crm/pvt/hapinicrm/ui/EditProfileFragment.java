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
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    public static String usertype,previouspasscode,previouspassword;
    ProgressDialog progressDialog;
    String name, password, passcode, email;
    FirebaseUser firebaseUser;
    private Boolean updateauth=false;


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
        Log.e(TAG, "onViewCreated: "+previouspasscode );
        Log.e(TAG, "updprevioudpassword "+previouspassword);
        //Log.e(TAG, "onViewCreatedusertype: " + usertype);
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
        name = etProfileName.getText().toString();
        email = etProfileEmail.getText().toString();
        passcode = etProfilePasscode.getText().toString();
        password = etProfilePassword.getText().toString();
        // Log.e(TAG, "getdata: " + name + email + passcode + password);
        if (checkBox.isChecked()) {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(passcode) || TextUtils.isEmpty(password) || ivProfilePicURI == null) {
                Snackbar.make(view, "Please provide all the info", Snackbar.LENGTH_LONG).show();
            } else if (passcode.length() < 6) {
                Snackbar.make(view, "Please enter a valid passcode", Snackbar.LENGTH_LONG).show();
            } else {
                setdata(ivProfilePicURI);
            }
        } else {
            Snackbar.make(view, "Please accept the terms and conditions", Snackbar.LENGTH_LONG).show();
        }


    }

    private void setdata(Uri imgurl) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating");
        progressDialog.show();


        //setpasscode will be updated in future

        if (imgurl != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("profilepicv2");
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
                        Log.e(TAG, "photo: "+downloadUri );
                        updateprofile(downloadUri.toString());


                    } else {
                        // Handle failures
                        // ...
                        Snackbar.make(thisView, "unable to upload img", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void updateprofile(String downloaduri) {
        Log.e(TAG, "updateprofile: "+"updateprofile" );
        String postids;
        String prepostid;
        switch (usertype) {
            case "master":
                 postids=passcode+"@masteradmin.com";
                updatemasterprofile(downloaduri);
                break;
            case "crm":
                prepostid=previouspasscode=previouspasscode+"@crmadmin.com";
                 postids=passcode+"@crmadmin.com";
               if(updateadminprofile(downloaduri,postids,prepostid)){
                   getpreviousinfodatabase(downloaduri,"CRM");
               }
                break;
            case "data":
                 postids=passcode+"@deadmin.com";
                 prepostid=previouspasscode=previouspasscode+"@deadmin.com";
                Log.e(TAG, "updateprofile: "+"data" );
//                if(updateadminprofile(downloaduri,postids)){
//                    getpreviousinfodatabase(downloaduri,"DATA_ENTRY");
//                }
                if (
                updateadminprofile(downloaduri,postids,prepostid)){
                    getpreviousinfodatabase(downloaduri,"DATA_ENTRY");
                };
                break;

            case "video":
                 postids=passcode+"@veadmin.com";
                prepostid=previouspasscode=previouspasscode+"@veadmin.com";
                if(updateadminprofile(downloaduri,postids,prepostid)){
                    getpreviousinfodatabase(downloaduri,"VIDEO_EDITOR");
                }
                break;


        }
    }

    private void updatemasterprofile(String downloaduri) {



        Log.e(TAG, "updatemasterprofile:" + "master");

    }

    private Boolean updateadminprofile(String downloaduri,String postids,String previouspostid) {
        ;
        Log.e(TAG, "updateadminprofile: "+postids );
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        Log.e(TAG, "updateadminprofile: "+uid );
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(previouspostid, previouspassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(postids)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User email address updated.");
                                            user.updatePassword(password)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User password updated.");
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                        //----------------------------------------------------------\\
                    }
                });
        return updateauth;


    }
    private void getpreviousinfodatabase(String downloaduri,String usertypes){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child(usertypes);
        reference.child(previouspasscode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                Admin admin=snapshot.getValue(Admin.class);
                Log.e(TAG, "onDataChange: "+admin.getPasscode()+admin.getName() );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}


