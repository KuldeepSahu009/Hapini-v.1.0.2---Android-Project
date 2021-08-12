package com.crm.pvt.hapinicrm;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.ui.UserLoginFragmentDirections;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class forgetpassword_user extends Fragment {
private View binding;
EditText getpasswordemailuser;
EditText passcodepassworduser;
EditText usertype;
Button submituser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=inflater.inflate(R.layout.fragment_forgetpassword_user, container, false);
        return binding.getRootView();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getpasswordemailuser=view.findViewById(R.id.getUserResetPasswordEmail);
        passcodepassworduser=view.findViewById(R.id.passcodeForgetPasswordUser);
        usertype=view.findViewById(R.id.forgetPasswordUserType);
        submituser=view.findViewById(R.id.getUserPasswordButton);

        submituser.setOnClickListener(v -> {
            String email=getpasswordemailuser.getText().toString();
            String passcode=passcodepassworduser.getText().toString();
            String Usertype=usertype.getText().toString();
            if(!email.equals("")&&(passcode.length()==6)) {
                senddetails(email, passcode, Usertype);
                Toast.makeText(getContext(),"Details Submitted",LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(),"Not Valid Details",LENGTH_SHORT).show();
            }
        });

    }

    private void senddetails(String email, String passcode, String usertype) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Requests").child("Forgotpassword").child(passcode);
        myRef.child("emails").setValue(email);
        myRef.child("passcodes").setValue(passcode);
        myRef.child("UserType").setValue(usertype);
        myRef.child("reason").setValue("Request for password");
     getdatafromfirebase();
    }

    private void getdatafromfirebase() {
        //I am working

    }
}