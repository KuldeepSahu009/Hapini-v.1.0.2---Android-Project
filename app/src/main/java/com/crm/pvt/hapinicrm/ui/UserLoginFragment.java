package com.crm.pvt.hapinicrm.ui;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentUserLoginBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;


public class UserLoginFragment extends Fragment {

    private FragmentUserLoginBinding binding;
    private String TAG = "TAG";
    public static boolean isUserLoggedIn = false;
    ProgressDialog progressDialog;
    public static String currentUserPasscode = "";
    public static TrackUserModel currentUser = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        binding.btnLoginUser.setOnClickListener(v -> {
            boolean isValid = validateCredentials();
            if (isValid) {
                int pos = binding.spSelectUser.getSelectedItemPosition();
                getdata(pos, v);
            } else {
                Snackbar.make(v, "Authentication Failed", Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.tvForgotPasswordUser.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToForgetpasswordUser()));
    }

    private boolean validateCredentials() {
        if (binding.etPasscodeUser.getText().toString().length() != 6) {
            binding.etPasscodeUser.setError("Passcode should be 6 characters long");
            return false;
        } else if (binding.etPasswordUser.getText().toString().isEmpty()) {
            binding.etPasswordUser.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }
    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.select_user_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectUser.setAdapter(adapter);
    }

    private void getdata(int pos, View view) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Authenticating");
        progressDialog.show();


        String passcodes = binding.etPasscodeUser.getText().toString();
        String passwords = binding.etPasswordUser.getText().toString();
        if (pos == 1) {
            if(!isUserLoggedIn)
            {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String key = dataSnapshot.getKey();
                            Log.e(TAG, "onDataChange: "+key );
                            if (key.equals(passcodes)) {
                                String password = dataSnapshot.child("password").getValue().toString();
                                if (password.equals(passwords)) {

                                    currentUser = dataSnapshot.getValue(TrackUserModel.class);
                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE).edit();
                                    editor.putString("passcode", passcodes);
                                    editor.putString("password", passwords);
                                    editor.putString("type", "crmuser");
                                    editor.apply();
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                                            .child("data").child(passcodes);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                                    Date date = new Date();
                                    String todaydate = dateFormat.format(date);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("present", todaydate);
                                    reference1.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            isUserLoggedIn = true;
                                            currentUserPasscode = passcodes;
                                            Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToCrmUserFragment());
                                        }
                                    });


                                } else {
                                    Toast.makeText(getContext(), "failed to login", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Log.e(TAG, "onCancelled: " + "error");
                    }
                });
            }
        } else if (pos == 2) {


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usersv2").child("data");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String key = dataSnapshot.getKey();
                            SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                            if (key.equals(passcodes)) {
                                String password = dataSnapshot.child("password").getValue().toString();
                                if (password.equals(passwords)) {
                                    if(!isUserLoggedIn) {
                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE).edit();
                                    editor.putString("passcode", passcodes);
                                    editor.putString("password", passwords);
                                    editor.putString("type", "datauser");
                                    editor.apply();

                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                                            .child("data").child(passcodes);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                                    Date date = new Date();
                                    String todaydate = dateFormat.format(date);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("present", todaydate);

                                        reference1.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                isUserLoggedIn = true;
                                                Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToDataEntryUserFragment());
                                            }
                                        });
                                    }

                                } else {
                                    Toast.makeText(getContext(), "failed to login", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });


        } else if (pos == 3) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usersv2").child("video");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getKey();

                        if (key.equals(passcodes)) {
                            String password = dataSnapshot.child("password").getValue().toString();
                            if (password.equals(passwords)) {

                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE).edit();
                                editor.putString("passcode", passcodes);
                                editor.putString("password", passwords);
                                editor.putString("type", "videouser");
                                editor.apply();


                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("attendencev2").child("users")
                                        .child("video").child(passcodes);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                                Date date = new Date();
                                String todaydate = dateFormat.format(date);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("present", todaydate);
                                reference1.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Navigation.findNavController(view).navigate(UserLoginFragmentDirections.actionUserLoginFragmentToVideoEditorUserFragment());

                                    }
                                });


                            } else {
                                Toast.makeText(getContext(), "failed to login", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });
        }
    }
}