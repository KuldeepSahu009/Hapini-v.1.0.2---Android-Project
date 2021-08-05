package com.crm.pvt.hapinicrm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentDataEntryUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class DataEntryUserFragment extends Fragment {

    private SharedPreferences sp;
    private FragmentDataEntryUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryUserBinding.inflate(inflater, container, false);
        sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        return binding.getRoot();

    }
    private String givenTask;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.dataentryuserlogout.setOnClickListener(v->
              dataUserLogout(v));

        binding.ivOpenGS.setOnClickListener(v->
        {
            Navigation.findNavController(v).navigate(DataEntryUserFragmentDirections.actionDataEntryUserFragmentToGoogleSheetFragment());
        });

        binding.btnViewTaskDataEntryUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenTask != null)
                {
                    new AlertDialog.Builder(getContext())
                            .setCancelable(true)
                            .setTitle("Your Task")
                            .setMessage(givenTask)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
        String currentUserPasscode = sp.getString("passcode","null");
        DatabaseReference dataUserReference = FirebaseDatabase.getInstance().getReference("usersv2/data").child(currentUserPasscode);
        dataUserReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if(dataSnapshot.child("task").exists())
                    {
                        givenTask = dataSnapshot.child("task").getValue().toString();
                        binding.btnViewTaskDataEntryUser.setVisibility(View.VISIBLE);
                    }
                }
            }
            //
        });

        dataUserReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.getKey().equals("task"))
                {
                    binding.btnViewTaskDataEntryUser.setVisibility(View.VISIBLE);
                    givenTask = snapshot.getValue().toString();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.getKey().equals("task")) {
                    givenTask = snapshot.getValue().toString();
                    binding.btnViewTaskDataEntryUser.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("task").exists())
                {
                    givenTask = null;
                    binding.btnViewTaskDataEntryUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void dataUserLogout(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            SharedPreferences getshared = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            getshared.edit().clear().commit();
            Navigation.findNavController(v).navigateUp();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });

        AlertDialog logoutDialog = builder.create();
        logoutDialog.show();
    }
}