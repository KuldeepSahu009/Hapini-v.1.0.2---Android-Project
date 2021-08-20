package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.databinding.FragmentAddAdminFormDetailsBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAdminFormDetailsFragment extends Fragment {

    private final String TAG = "Add Admin";

    FragmentAddAdminFormDetailsBinding binding;
    String adminType;
    String franchiseadmin;
    ProgressDialog progressDialog;
    FirebaseAuth auth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminFormDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFormTitle();

        auth = FirebaseAuth.getInstance();

        binding.btnAddAdminSubmit.setOnClickListener(v -> {

            String email = binding.etEmail.getText().toString();
            String name = binding.etName.getText().toString();
            String mobileNo = binding.etMobileNumber.getText().toString();
            String whatsAppNo = binding.etWhatsappNumber.getText().toString();
            String city = binding.etCity.getText().toString();
            String location = binding.etLocality.getText().toString();
            String passcode = binding.etPasscode.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (binding.cvAddAdminFormTermsAndCondition.isChecked()) {
                if (email.isEmpty() || name.isEmpty() || mobileNo.isEmpty() || whatsAppNo.isEmpty() || city.isEmpty() || location.isEmpty() ||
                        passcode.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "All Fields are necessary", Snackbar.LENGTH_LONG).show();
                } else if (passcode.length() != 6) {
                    Snackbar.make(v, "Enter correct passcode", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Creating Admin");
                    progressDialog.show();

                    enterDataToFirebase(name, email, mobileNo, whatsAppNo, city, location, passcode, password);

                }
            } else {
                Snackbar.make(v, "Please Accept all Terms and Conditions!.", Snackbar.LENGTH_LONG).show();
            }

        });

        binding.ivBackFromAddAdminFormFragment.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }

    private void enterDataToFirebase(String name, String email, String mobileNo, String whatsAppNo, String city, String location, String passcode, String password) {

        Admin admin = new Admin(name, email, mobileNo, whatsAppNo, passcode, password, location, "");

        DatabaseReference franchiseDbRef = null;
        if(currentFranchise != null) {
            if(!FranchiseDashboardFragment.addAdminTypes.equals(""))
                adminType = FranchiseDashboardFragment.addAdminTypes;
            franchiseDbRef = FirebaseDatabase.getInstance().getReference()
                    .child("crm_by_franchise")
                    .child(currentFranchise.getPasscode());
        }

        if (adminType == "CRM") {

            final int[] count = {0};
            final Admin []crmAdmin = {admin};

            if (currentFranchise != null) {

                franchiseDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            count[0] = (int) snapshot.getChildrenCount();
                            if(count[0] <= 2 && crmAdmin[0] != null) {
                                Log.i("AddAdmin",String.valueOf(count[0]));
                                addCrmAdmin(crmAdmin[0]);
                                crmAdmin[0] = null;
                                Toast.makeText(getContext(), "CRM Admin Successfully Entered", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(requireView()).navigateUp();
                            } else {
                                progressDialog.dismiss();
                               // TODO This Toast keeps crashing need to fix this
                              //  Toast.makeText(requireActivity().getApplicationContext(),"Max 2 crm admins can be added",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            else {

                auth.createUserWithEmailAndPassword(passcode + "@crmadmin.com", password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Admin created");
                    } else {
                        Log.i(TAG, "Something went wrong");
                    }
                });

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adminV2").child(adminType).child(passcode);
                databaseReference.setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "CRM Admin Successfully Entered", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(requireView()).navigateUp();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "CRM Admin is not Entered", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        if (adminType == "DATA_ENTRY") {

            auth.createUserWithEmailAndPassword(passcode + "@deadmin.com", password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Admin created");
                } else {
                    Log.i(TAG, "Something went wrong");
                }
            });

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adminV2").child(adminType).child(passcode);
            databaseReference.setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Data Entry Admin Successfully Entered", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigateUp();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Data Entry Admin is not Entered", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (adminType == "VIDEO_EDITOR") {

            auth.createUserWithEmailAndPassword(passcode + "@veadmin.com", password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Admin created");
                } else {
                    Log.i(TAG, "Something went wrong");
                }
            });

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adminV2").child(adminType).child(passcode);
            databaseReference.setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Video Editor Admin Successfully Entered", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigateUp();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Video Editor Admin is not Entered", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    void setFormTitle() {
        String title = "";
        if (AddAdminFragment.addAdminType != null) {
            if (AddAdminFragment.addAdminType.equals("CRM")) {
                adminType = "CRM";
                //VISIBLE EDITTEXT SPECIALLY RELATED TO CRM IN FUTURE
                title = " ADD CRM ADMIN";
            } else if (AddAdminFragment.addAdminType.equals("DE")) {
                adminType = "DATA_ENTRY";
                //VISIBLE EDITTEXT SPECIALLY RELATED TO DATA ENTRY IN FUTURE
                title = " ADD DATA ENTRY ADMIN";
            } else {
                adminType = "VIDEO_EDITOR";
                //VISIBLE EDITTEXT SPECIALLY RELATED TO VIDEO EDITOR IN FUTURE
                title = " ADD VIDEO EDITOR ADMIN";
            }
        } else if (FranchiseDashboardFragment.addAdminTypes != null) {
            adminType = "CRM";
            title = "ADD CRM ADMIN";
        }
        binding.tvAddAdminFormDashboardTitle.setText(title);
    }

    //Separate function to add crm admin when franchise adds him/her.
    private void addCrmAdmin(Admin admin) {
        DatabaseReference franchiseDbRef = FirebaseDatabase
                .getInstance()
                .getReference("crm_by_franchise")
                .child(currentFranchise.getPasscode());

        franchiseDbRef.child(admin.getPasscode()).setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("AddAdmin","Admin added in franchise db");
            }
        });

        auth.createUserWithEmailAndPassword(admin.getPasscode() + "@crmadmin.com", admin.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "Admin created");
            } else {
                Log.i(TAG, "Something went wrong");
            }
        });

        /**
         * this code creating admin in adminV2 also after created in crm_by_franchise
         * use this if strategy 'll change in future
         */


       /*
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adminV2").child(adminType).child(admin.getPasscode());
        databaseReference.setValue(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "CRM Admin Successfully Entered", Toast.LENGTH_LONG).show();
                Navigation.findNavController(requireView()).navigateUp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "CRM Admin is not Entered", Toast.LENGTH_LONG).show();
            }
        });

        */
    }
}
