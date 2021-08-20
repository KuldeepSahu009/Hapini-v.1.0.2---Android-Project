package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.crm.pvt.hapinicrm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Attendancedialogue extends AppCompatDialogFragment {
    private static final String TAG = "TAG";
    TextView textViewpasscode;
    private Context context;
    public static  String type;

    public Attendancedialogue(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialogueforattendence,null);
        textViewpasscode=view.findViewById(R.id.showpasscodeinattendance);
        Log.e(TAG, "onCreateDialog: "+type );
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        String passcode=getshared.getString("passcode","no data");
        SharedPreferences getshareds = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String passcodes=getshareds.getString("passcode","no data");
        //Log.e(TAG, "onCreateDialog: "+passcode );
        if (type=="crmadmin"){
        textViewpasscode.setText(passcode);}
        else if(type=="crmuser"){
            Log.e(TAG, "onCreateDialog: "+"crmuser" );
            textViewpasscode.setText(passcodes);
        }else if (type=="franchiseadmin"){
            textViewpasscode.setText(passcode);
        }
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (type) {
                            case "crmadmin":
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("attendencev2")
                                    .child("admin").child("crm").child(passcode);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("present", "present");
                            reference.child(date).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.e(TAG, "onSuccess: " + "success");
                                    Toast.makeText(context, "Attendance Marked", Toast.LENGTH_LONG).show();
                                    dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Unable to mark attendance", Toast.LENGTH_LONG).show();
                                }
                            });
                            break;
                            case "crmuser":
                                Log.e(TAG, "onClick: "+"crmuser" );
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("attendencev2")
                                        .child("user").child("crm").child(passcodes);

                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("present", "present");
                                reference1.child(date).setValue(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.e(TAG, "onSuccess: " + "success");
                                        Toast.makeText(context, "Attendance Marked", Toast.LENGTH_LONG).show();
                                        dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Unable to mark attendance", Toast.LENGTH_LONG).show();
                                    }
                                });
                                break;
                            case "franchiseadmin":
                                Log.e(TAG, "onClick: "+"franchise" );
                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("attendencev2")
                                        .child("admin").child("franchise").child(passcode);

                                HashMap<String, String> hashMap2 = new HashMap<>();
                                hashMap2.put("present", "present");
                                reference2.child(date).setValue(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.e(TAG, "onSuccess: " + "success");
                                        Toast.makeText(context, "Attendance Marked", Toast.LENGTH_LONG).show();
                                        dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Unable to mark attendance", Toast.LENGTH_LONG).show();
                                    }
                                });
                                break;



                        }
                    }
                });

        return builder.create();
    }
}
