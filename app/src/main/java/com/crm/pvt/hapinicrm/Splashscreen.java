package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.crm.pvt.hapinicrm.ui.CrmUserFragment;
import com.crm.pvt.hapinicrm.ui.MainActivity;
import com.crm.pvt.hapinicrm.ui.UserLoginFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Splashscreen extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int totalCount;
    private final int SPLASH_TIME_OUT=6500;

    String password;
    String passcode;
    String usertype;
    private String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splashscreen.this, MainActivity.class);
                startActivity(intent);
            }
        },SPLASH_TIME_OUT);

        getdatafromsharedpreference();
        checkforuser();



    }
    private void getdatafromsharedpreference() {
        SharedPreferences getshared = getSharedPreferences("info", Context.MODE_PRIVATE);
        usertype = getshared.getString("type", "no data");
        passcode = getshared.getString("passcode", "no passcode");
        password = getshared.getString("password", "no password");

    }

    private void checkforuser() {


        if (usertype != "no data") {

            markattendance();
        }else if (usertype=="no data"){
            Log.e(TAG, "checkforuser: "+"no user");
        }
    }

    private void markattendance() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date date = new Date();
        String todaydate = dateFormat.format(date);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("present", todaydate);

        switch (usertype) {
            case "crmuser":
                Log.e(TAG, "getuserinfo: "+"crm" );
                DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                        .child("crm").child(passcode);

                reference3.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(TAG, "onSuccess: "+"attendencedonecrm" );



                    }
                });

                break;
            case "videouser":
                Log.e(TAG, "getuserinfo: "+"video" );
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                        .child("video").child(passcode);

                reference2.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(TAG, "onSuccess: "+"attendencedonevideo" );


                    }
                });
                break;
            case "datauser":
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                        .child("data").child(passcode);

                reference1.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(TAG, "onSuccess: "+"attendencedonedata" );



                    }
                });

                break;

        }
    }
}