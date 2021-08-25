package com.crm.pvt.hapinicrm.ui;

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
import com.crm.pvt.hapinicrm.databinding.FragmentAddAdminFormDetailsBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentAddFranchiseFormDetailsBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddFranchiseFormDetailsFragment extends Fragment {

    private final String TAG = "Add Franchise";

    FragmentAddFranchiseFormDetailsBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddFranchiseFormDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpStateSpinner();

        auth = FirebaseAuth.getInstance();

        binding.btnAddFranchiseSubmit.setOnClickListener(v -> {

            String email = binding.etEmail.getText().toString();
            String name = binding.etName.getText().toString();
            String mobileNo = binding.etMobileNumber.getText().toString();
            String whatsAppNo = binding.etWhatsappNumber.getText().toString();
            String state = binding.spinner.getSelectedItem().toString();
            String city = binding.etCityDetail.getText().toString();
            String location = binding.etLocality.getText().toString();
            String passcode = binding.etPasscode.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (binding.cvAddFranchiseFormTermsAndCondition.isChecked()) {
                if (email.isEmpty() || name.isEmpty() || mobileNo.isEmpty() || whatsAppNo.isEmpty() || city.isEmpty() || location.isEmpty() ||
                        passcode.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "All Fields are necessary", Snackbar.LENGTH_LONG).show();
                } else if (passcode.length() != 6) {
                    Snackbar.make(v, "Enter correct passcode", Snackbar.LENGTH_LONG).show();
                }
                else {
                    progressDialog=new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Creating Franchise");
                    progressDialog.show();
                    enterDataToFirebase(name,email,mobileNo,whatsAppNo, state,city,location,passcode,password);
                }
            }
            else {
                Snackbar.make(v, "Please Accept all Terms and Conditions!.", Snackbar.LENGTH_LONG).show();
            }

        });

        binding.ivBackFromAddFranchiseFormFragment.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
    }

    private void setUpStateSpinner() {
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.india_states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(stateAdapter);
    }

    private void enterDataToFirebase (String name, String email, String mobileNo, String whatsAppNo, String state , String city, String location, String passcode, String password) {

        Franchise franchise = new Franchise( name , email , mobileNo , whatsAppNo , passcode , password , state , city , location, "");
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("franchiseV2").child(passcode);
            databaseReference.setValue(franchise).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Franchise Successfully Entered",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigateUp();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Franchise is not Entered",Toast.LENGTH_LONG).show();
                }
            });
        }
}