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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
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

import java.util.ArrayList;

public class AddAdminFormDetailsFragment extends Fragment {

    private final String TAG = "Add Admin";

    FragmentAddAdminFormDetailsBinding binding;
    String adminType;
    String franchiseadmin;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    private final int[] count = {0};
    private  final boolean[] isAllowed = {true};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminFormDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFormTitle();
        setUpSpinner();

        auth = FirebaseAuth.getInstance();
        binding.btnAddAdminSubmit.setOnClickListener(v -> {

            String email = binding.etEmail.getText().toString();
            String name = binding.etName.getText().toString();
            String mobileNo = binding.etMobileNumber.getText().toString();
            String whatsAppNo = binding.etWhatsappNumber.getText().toString();
            String state = binding.spinner.getSelectedItem().toString();
            String city = binding.etCityName.getText().toString();
            String location = binding.etLocality.getText().toString();
            String passcode = binding.etPasscode.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (binding.cvAddAdminFormTermsAndCondition.isChecked()) {
                if (email.isEmpty() || name.isEmpty() || mobileNo.isEmpty() || whatsAppNo.isEmpty() || state.isEmpty() || city.isEmpty() || location.isEmpty() ||
                        passcode.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "All Fields are necessary", Snackbar.LENGTH_LONG).show();
                } else if (passcode.length() != 6) {
                    Snackbar.make(v, "Enter correct passcode", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Creating Admin");
                    progressDialog.show();

                    enterDataToFirebase(name, email, mobileNo, whatsAppNo,state ,  city, location, passcode, password);

                }
            } else {
                Snackbar.make(v, "Please Accept all Terms and Conditions!.", Snackbar.LENGTH_LONG).show();
            }

        });

        binding.ivBackFromAddAdminFormFragment.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.india_states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(stateAdapter);
    }

    private void enterDataToFirebase(String name, String email, String mobileNo, String whatsAppNo,String state, String city, String location, String passcode, String password) {

        String addedBy = "";

        if(Splashscreen.isFranchise) {
            addedBy = Splashscreen.passcode;
        }
        Admin admin = new Admin(name, email, mobileNo, whatsAppNo, passcode, password, state , city , location, "",addedBy);

        if (adminType == "CRM") {

            if (Splashscreen.isFranchise) {

                DatabaseReference franchiseDbRef = FirebaseDatabase.getInstance()
                        .getReference("crm_by_franchise")
                        .child(Splashscreen.passcode);
                franchiseDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            count[0] = (int) snapshot.getChildrenCount();
                            if (count[0] < 2) {

                            } else {
                                isAllowed[0] = false;
                            }
                            addCRMAdminByFranchise(admin);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            if(isAllowed[0]) {
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

    private void addCRMAdminByFranchise(Admin admin)
    {
        if (isAllowed[0]) {
            DatabaseReference franchiseDbRef = FirebaseDatabase.getInstance()
                    .getReference("crm_by_franchise")
                    .child(Splashscreen.passcode);
            franchiseDbRef.child(admin.getPasscode()).setValue(admin);
            Toast.makeText(getContext() , "CRM Admin Added",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext() , "Limit of current Franchise is full.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

}