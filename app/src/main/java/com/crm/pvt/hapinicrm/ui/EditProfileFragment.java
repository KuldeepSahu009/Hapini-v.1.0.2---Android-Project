package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.model.Masteradmin;
import com.crm.pvt.hapinicrm.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
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
import java.util.Objects;


public class EditProfileFragment extends Fragment {
    private View thisView;
    private EditText etProfileName, etProfileEmail, etProfilePasscode, etProfilePassword;
    private ImageView profileimg, savedetails;
    private CheckBox checkBox;
    public Uri ivProfilePicURI;
    private String TAG = "TAG";
    public static String userpasscode;
    public static String user;
    public Admin admin;
    Masteradmin masteradminmodel;
   // public static String usertype, previouspasscode, previouspassword;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String  passcode, usertype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thisView = view;
       // getinfo(usertype, previouspasscode);
        //Log.e(TAG, "onViewCreated: " + usertype);

        initializeAllUIComponents(view);
       // Log.e(TAG, "onViewCreated: " + previouspasscode);
        //Log.e(TAG, "onViewCreated: "+usertype );
        //Log.e(TAG, "onViewCreatedusertype: " + usertype);
        if (!user.equals("crmuser")){
        SharedPreferences  prefs = getContext().getSharedPreferences("infos", Context.MODE_PRIVATE);
        passcode=prefs.getString("passcode","no data");
        usertype=prefs.getString("type","no dtata");
            Log.e(TAG, "onViewCreated: "+passcode+usertype);
        }else{
            SharedPreferences  prefs = getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
            passcode=prefs.getString("passcode","no data");
            usertype=prefs.getString("type","no dtata");
            usertype="crmuser";
            passcode=userpasscode;

        }




        Log.e(TAG, "onViewCreatedshared: "+passcode+usertype );


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
//        etProfileName = view.findViewById(R.id.etProfileName);
//        etProfileEmail = view.findViewById(R.id.etProfileEmail);
//        etProfilePasscode = view.findViewById(R.id.etProfilePasscode);
//        etProfilePassword = view.findViewById(R.id.etProfilePassword);
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
                Glide.with(requireContext()).load(ivProfilePicURI).into(profileimg);

            }
        }
    }

    private void getdata(View view) {
//        name = etProfileName.getText().toString();
//        email = etProfileEmail.getText().toString();
//        passcode = etProfilePasscode.getText().toString();
//        password = etProfilePassword.getText().toString();
        // Log.e(TAG, "getdata: " + name + email + passcode + password);
//        if (checkBox.isChecked()) {
//            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(passcode) || TextUtils.isEmpty(password) || ivProfilePicURI == null) {
//                Snackbar.make(view, "Please provide all the info", Snackbar.LENGTH_LONG).show();
//            } else if (passcode.length() < 6) {
//                Snackbar.make(view, "Please enter a valid passcode", Snackbar.LENGTH_LONG).show();
//            } else {
//                setdata(ivProfilePicURI);
//            }
//        } else {
//            Snackbar.make(view, "Please accept the terms and conditions", Snackbar.LENGTH_LONG).show();
//        }

        setdata(ivProfilePicURI);

    }

    private void setdata(Uri imgurl) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //setpasscode will be updated in future

        if (imgurl != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("profilepicv2");
            StorageReference filerefernece = storageReference.child(System.currentTimeMillis() + "");
            StorageTask uploadtask = filerefernece.putFile(imgurl);
            Task<Uri> urlTask = uploadtask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return filerefernece.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.e(TAG, "photo: " + downloadUri);
                    updateprofile(downloadUri.toString());


                } else {
                    // Handle failures
                    // ...
                    progressDialog.dismiss();
                    Snackbar.make(thisView, "unable to upload img", Snackbar.LENGTH_LONG).show();
                }
            });
        }else{
            progressDialog.dismiss();
            Snackbar.make(thisView, "Please choose an Image", Snackbar.LENGTH_LONG).show();
        }
    }

    private void updateprofile(String downloaduri) {
        Log.e(TAG, "updateprofile: " + usertype);
        String postids;
        String prepostid;
        switch (usertype) {
            case "master":
//                postids = passcode + "@masteradmin.com";
//                prepostid = previouspasscode + "@masteradmin.com";
               // updateadminprofile(downloaduri, postids, prepostid, "master");
                updatemasterprofile(downloaduri);

                break;
            case "crm":
//                prepostid = previouspasscode + "@crmadmin.com";
//                postids = passcode + "@crmadmin.com";
                //updateadminprofile(downloaduri, postids, prepostid, "CRM");

                updateadminprofiles(downloaduri,"CRM");

                break;
            case "data":
//                postids = passcode + "@deadmin.com";
//                prepostid = previouspasscode + "@deadmin.com";
                Log.e(TAG, "updateprofile: " + "data");
//

                //updateadminprofile(downloaduri, postids, prepostid, "DATA_ENTRY");


                break;

            case "video":
//                postids = passcode + "@veadmin.com";
//                prepostid = previouspasscode + "@veadmin.com";
//                updateadminprofile(downloaduri, postids, prepostid, "VIDEO_EDITOR");

                break;
            case "franchise":
                Log.e(TAG, "updateprofile: "+passcode );
                updatefranchisefragmnet(downloaduri);
                break;
            case "crmuser":
                updatecrmuser(downloaduri);
                break;


        }
    }
    private void updatecrmuser(String downloaduri){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm").child(passcode);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.e(TAG, "onDataChange: "+dataSnapshot1.getKey() );
                }
                String addedby=dataSnapshot.child("addedBy").getValue().toString();
                String city=dataSnapshot.child("city").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                //String imgurl=dataSnapshot.child("imgurl").getValue().toString();
                String location=dataSnapshot.child("locality").getValue().toString();
                String name=dataSnapshot.child("name").getValue().toString();
                String passcode=dataSnapshot.child("passcode").getValue().toString();
                String password=dataSnapshot.child("password").getValue().toString();
                String phoneno=dataSnapshot.child("mobileNo").getValue().toString();
                String state=dataSnapshot.child("state").getValue().toString();
                String whatsappno=dataSnapshot.child("whatsAppNo").getValue().toString();
                HashMap<String,String>hashMap=new HashMap<>();
                hashMap.put("addedBy",addedby);
                hashMap.put("city",city);
                hashMap.put("email",email);
                hashMap.put("locality",location);
                hashMap.put("mobileNo",phoneno);
                hashMap.put("name",name);
                hashMap.put("passcode",passcode);
                hashMap.put("password",password);
                hashMap.put("state",state);
                hashMap.put("whatsAppNo",whatsappno);
                hashMap.put("imgurl",downloaduri);


                User user=new User(name,email,phoneno,whatsappno,state,city,location,passcode,password,addedby,downloaduri);
                databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"updated",Toast.LENGTH_LONG).show();
                       // Navigation.findNavController(thisView).navigateUp();

                      
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updatefranchisefragmnet(String downloaduri){
        DatabaseReference franchiseReference;
        franchiseReference = FirebaseDatabase.getInstance().getReference("franchiseV2").child(passcode);
        franchiseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Log.e(TAG, "onDataChange: "+snapshot.getKey() );
                }
                String city=dataSnapshot.child("city").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String imgurl=dataSnapshot.child("imgurl").getValue().toString();
                String location=dataSnapshot.child("location").getValue().toString();
                String name=dataSnapshot.child("name").getValue().toString();
                String passcode=dataSnapshot.child("passcode").getValue().toString();
                String password=dataSnapshot.child("password").getValue().toString();
                String phoneno=dataSnapshot.child("phoneno").getValue().toString();
                String state=dataSnapshot.child("state").getValue().toString();
                String whatsappno=dataSnapshot.child("whatsappno").getValue().toString();

                Franchise franchise=new Franchise(name,email,phoneno,whatsappno,passcode,password,state,city,location,downloaduri);
                franchiseReference.setValue(franchise).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        NavHostFragment navHostFragment = (NavHostFragment) getFragmentManager().findFragmentById(R.id.editprofileadmins);
                        if (navHostFragment != null) {

                            NavController navController = navHostFragment.getNavController();
                            navController.navigateUp();


                        }
                       // Navigation.findNavController(getView()).navigateUp();
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateadminprofiles(String downloaduri,String type){
        Log.e(TAG, "updateadminprofiles: "+type );
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("adminV2").child(type)
                .child(passcode);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     //   Log.e(TAG, "onDataChangekey: "+snapshot.getKey() );
                        //Admin adminObject = snapshot.getValue(Admin.class);
                      //  Log.e(TAG, "onDataChange: "+adminObject.getPasscode()+adminObject.getAddedBy() );

                       // Log.e(TAG, "onDataChange: "+dataSnapshot.getKey() );
                        String addedby=dataSnapshot.child("addedBy").getValue().toString();
                        String city=dataSnapshot.child("city").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String imgurl=dataSnapshot.child("imgurl").getValue().toString();
                        String location=dataSnapshot.child("location").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String passcode=dataSnapshot.child("passcode").getValue().toString();
                        String password=dataSnapshot.child("password").getValue().toString();
                        String phoneno=dataSnapshot.child("phoneno").getValue().toString();
                        String state=dataSnapshot.child("state").getValue().toString();
                        String whatsappno=dataSnapshot.child("whatsappno").getValue().toString();
                        Admin admin=new Admin(name,email,phoneno,whatsappno,passcode,password,state,city,location,downloaduri,addedby);
                        reference.setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Navigation.findNavController(getView()).navigateUp();
                            }
                        });

                       // Log.e(TAG, "onDataChange: "+adminObject.getAddedBy() );
                        //Log.e(TAG, "onDataChange: "+adminObject.getPasscode()+adminObject.getAddedBy() );
                       // Log.e(TAG, "onDataChange: "+dataSnapshot.getKey() );

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void updatemasterprofile(String downloaduri) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Masterv2")
                .child(passcode);
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Log.e(TAG, "onDataChange: "+dataSnapshot.getKey() );
               }
               String email=snapshot.child("email").getValue().toString();
               String name=snapshot.child("name").getValue().toString();
               String passcode=snapshot.child("passcode").getValue().toString();
               String password=snapshot.child("password").getValue().toString();
               HashMap<String,String> hashMap=new HashMap<>();
               hashMap.put("email",email);
               hashMap.put("name",name);
               hashMap.put("passcode",passcode);
               hashMap.put("password",password);
               hashMap.put("imgurl",downloaduri);
               reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       progressDialog.dismiss();
                       Navigation.findNavController(getView()).navigateUp();
                   }
               });

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });





    }

//    private void updateadminprofile(String downloaduri, String postids, String previouspostid, String usertype) {
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signInWithEmailAndPassword(previouspostid, previouspassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
//                firebaseAuth.getCurrentUser().updateEmail(postids).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        firebaseAuth.getCurrentUser().updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                if (usertype.equals("master")) {
//                                    setnewmasternode(downloaduri);
//                                }
//                                setnewnode(downloaduri, admin, usertype);
//                                Toast.makeText(getContext(), "success password", Toast.LENGTH_LONG).show();
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "failed password", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(), "failed to change", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }


//    private void setnewnode(String downloaduri, Admin admin, String usertype) {
//
//
//        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype)
//                .child(passcode);
//        Admin admin1 = new Admin(
//                name,
//                email,
//                admin.getPhoneno(),
//                admin.getWhatsappno(),
//                passcode, password,
//                admin.getState(),
//                admin.getCity(),
//                admin.getLocation(),
//                downloaduri,
//                admin.getAddedBy());
//        databaseReference1.setValue(admin1).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.e(TAG, "onSuccess: " + "success to set");
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child(usertype)
//                        .child(previouspasscode);
//                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(), "success to remove", Toast.LENGTH_LONG).show();
//                        SharedPreferences.Editor editor = getContext().getSharedPreferences("infos", Context.MODE_PRIVATE).edit();
//                        editor.putString("passcode", passcode);
//                        editor.apply();
//                        Navigation.findNavController(thisView).navigateUp();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(), "failed to remove", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(getContext(), "unable to set", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }

    private void getinfo(String usertype, String passcode) {
        Log.e(TAG, "getinfo: " + usertype + passcode);
        switch (usertype) {
            case "crm":
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adminV2").child("CRM")
                        .child(passcode);
                Log.e(TAG, "getinfo: " + passcode);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        admin = snapshot.getValue(Admin.class);
                        if (admin != null) {
                            admin = admin;
                            Log.e(TAG, "getinfoadmin: " + admin.getPasscode() + admin.getName());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "unable toobtained", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case "video":
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("adminV2").child("VIDEO_EDITOR")
                        .child(passcode);
                Log.e(TAG, "getinfo: " + passcode);
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        admin = snapshot.getValue(Admin.class);
                        if (admin != null) {
                            admin = admin;
                            Log.e(TAG, "getinfoadmin: " + admin.getPasscode() + admin.getName());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "unable toobtained", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case "data":
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("adminV2").child("DATA_ENTRY")
                        .child(passcode);
                Log.e(TAG, "getinfo: " + passcode);
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        admin = snapshot.getValue(Admin.class);
                        if (admin != null) {
                            admin = admin;
                            Log.e(TAG, "getinfoadmin: " + admin.getPasscode() + admin.getName());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "unable toobtained", Toast.LENGTH_LONG).show();
                    }
                });
                break;


        }

    }

//    private void setnewmasternode(String downloaduri) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Masterv2");
//        Masteradmin masteradmin = new Masteradmin(email, downloaduri, name, password, passcode);
//
//        reference.child(passcode).setValue(masteradmin).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.e(TAG, "onSuccess: " + "successfullywritten");
//                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Masterv2").child(previouspasscode);
//                reference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        progressDialog.dismiss();
//                        SharedPreferences.Editor editor = getContext().getSharedPreferences("infos", Context.MODE_PRIVATE).edit();
//                        editor.putString("passcode", passcode);
//                        editor.apply();
//                        Navigation.findNavController(thisView).navigateUp();
//                        Toast.makeText(getContext(), "successfully removed master", Toast.LENGTH_LONG).show();
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(), "unable to delete", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(getContext(), "unable to write", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }


}


