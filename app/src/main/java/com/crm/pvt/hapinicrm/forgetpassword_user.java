package com.crm.pvt.hapinicrm;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.isUserLoggedIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TextView;
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
            if(!email.equals("")&&(passcode.length()==6)&&(Usertype.contains("crm")||
                    Usertype.contains("data")||Usertype.contains("video"))) {
                senddetails(email, passcode, Usertype);


            }
            else{
               Toast toast= Toast.makeText(getContext(),"Not Valid Details",LENGTH_SHORT);
                View views=toast.getView();
                views.setBackgroundColor(Color.WHITE);
                TextView toastmessage=(TextView) toast.getView().findViewById(android.R.id.message);
                toastmessage.setTextColor(Color.BLACK);
                toast.show();
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
     getdatafromfirebase(passcode,usertype);
    }

    private void getdatafromfirebase(String passcodes, String usertype) {
        DatabaseReference myref2 = FirebaseDatabase.getInstance().getReference("usersv2").child(usertype);
        // Read from the database
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (key.equals(passcodes)) {
                        String getpassword = dataSnapshot.child("password").getValue().toString();
                        Toast toast= Toast.makeText(getContext(),"Password is"+getpassword,LENGTH_LONG);
                        View views=toast.getView();
                        views.setBackgroundColor(Color.WHITE);
                        TextView toastmessage=(TextView) toast.getView().findViewById(android.R.id.message);
                        toastmessage.setTextColor(Color.BLACK);

                        toast.show();
                    }
                    else{
                        Toast.makeText(getContext(),"No Such Detail Available",LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}