package com.crm.pvt.hapinicrm;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class forgetpassword extends Fragment {
EditText passwordemail;
EditText getpasscode;
EditText Admintype;
Button submit;
private View binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= inflater.inflate(R.layout.fragment_forgetpassword, container, false);
        return binding.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passwordemail=view.findViewById(R.id.getresetpasswordemail);
        getpasscode=view.findViewById(R.id.passcodeforgetpassword);
        Admintype=view.findViewById(R.id.admintype);
        submit=view.findViewById(R.id.Getpasswordbutton);
        submit.setOnClickListener(v -> {
            String email=passwordemail.getText().toString();
            String passcode=getpasscode.getText().toString();
            String Adminstype=Admintype.getText().toString();
            if(!email.equals("")&&(passcode.length()==6)&&(Adminstype.contains("crm")||
                    Adminstype.contains("data")||Adminstype.contains("video"))) {
                Toast.makeText(getContext(),"Loading...",LENGTH_SHORT).show();
               if(Adminstype.equals("crm")){
                   Adminstype="CRM";
               }
               if(Adminstype.equals("data")){
                   Adminstype="DATA_ENTRY";
               }
               if(Adminstype.equals("video")){
                   Adminstype="VIDEO_EDITOR";
               }
                senddetails(email, passcode, Adminstype);

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

    private void senddetails(String email, String passcode, String adminstype) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Requests").child("Forgotpassword").child(passcode);
        myRef.child("emails").setValue(email);
        myRef.child("passcodes").setValue(passcode);
        myRef.child("AdminType").setValue(adminstype);
        myRef.child("reason").setValue("Request for password");
        getdatafromfirebase(passcode,adminstype);
    }

    private void getdatafromfirebase(String passcode, String adminstype) {
        DatabaseReference myref2 = FirebaseDatabase.getInstance().getReference("adminV2").child(adminstype);
        // Read from the database
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (key.equals(passcode)) {
                        String getpassword = dataSnapshot.child("password").getValue().toString();
                        Toast toast = Toast.makeText(getContext(), "Password is: " + getpassword, LENGTH_LONG);
                        View views = toast.getView();
                        views.setBackgroundColor(Color.WHITE);
                        TextView toastmessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastmessage.setTextColor(Color.BLACK);
                        toast.show();
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