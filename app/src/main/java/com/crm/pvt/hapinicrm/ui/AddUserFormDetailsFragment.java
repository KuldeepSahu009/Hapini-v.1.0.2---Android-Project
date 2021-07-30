package com.crm.pvt.hapinicrm.ui;

import android.app.ProgressDialog;
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
import com.crm.pvt.hapinicrm.databinding.FragmentAddUserFormDetailsBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserFormDetailsFragment extends Fragment {

    private FragmentAddUserFormDetailsBinding binding;
    User user;
    String usertypes;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserFormDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFormTitle();
        binding.btnAddUserSubmit.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String name = binding.etName.getText().toString();
            String mobileno = binding.etMobileNumber.getText().toString();
            String whatsappno = binding.etWhatsappNumber.getText().toString();
            String city = binding.etCity.getText().toString();
            String location = binding.etLocality.getText().toString();
            String passcode = binding.etPasscode.getText().toString();
            String password = binding.etPassword.getText().toString();

            if (binding.cvAddUserFormTermsAndCondition.isChecked()) {
                if (email.isEmpty() || name.isEmpty() || mobileno.isEmpty() || whatsappno.isEmpty() || city.isEmpty() || location.isEmpty() ||
                        passcode.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v,"All Fields are necessary",Snackbar.LENGTH_LONG).show();

                } else if (passcode.length() != 6) {
                    Snackbar.make(v,"Enter correct passcode",Snackbar.LENGTH_LONG).show();
                }else{
                    progressDialog=new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Creating user");
                    progressDialog.show();
                        enterdatatofirebase(name,email,mobileno,whatsappno,city,location,passcode,password);
                }

            } else {
                Snackbar.make(v, "Please Accept all Terms and Conditions!.", Snackbar.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.ivBackFromAddUserFormFragment).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }

    private void setFormTitle() {
        String title = "";
        if (AddUserFragment.addUserType != null) {
            if (AddUserFragment.addUserType.equals("CRM")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO CRM IN FUTURE
                usertypes="crm";
                title = " ADD CRM USER";
            } else if (AddUserFragment.addUserType.equals("DE")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO DATA ENTRY IN FUTURE
                usertypes="data";
                title = " ADD DATA ENTRY USER";
            } else {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO VIDEO EDITOR IN FUTURE
                usertypes="video";
                title = " ADD VIDEO EDITOR USER";
            }
        }
        binding.tvAddUserFormDashboardTitle.setText(title);
    }
    private void enterdatatofirebase(String name,String email,String phoneno,String whatsappno,String city,String location,String passcode,String password){
        User user=new User(name,email,phoneno,whatsappno,city,location,passcode,password);

        if (usertypes=="crm"){

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("usersv2").child(usertypes);
            databaseReference.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"crm user entered",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"crm usernot entered",Toast.LENGTH_LONG).show();
                }
            });
        }
        if (usertypes=="data"){

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("usersv2").child(usertypes);
            databaseReference.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"dataentry user entered",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"dataentry usernot entered",Toast.LENGTH_LONG).show();
                }
            });
        }
        if (usertypes=="video"){

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("usersv2").child(usertypes);
            databaseReference.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"video user entered",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"video usernot entered",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}