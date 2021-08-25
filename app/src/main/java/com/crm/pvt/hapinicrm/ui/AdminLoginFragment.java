package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.StartFragment.selectedAdmin;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class AdminLoginFragment extends Fragment {

    private FragmentAdminLoginBinding binding;
    private FirebaseAuth auth;
    private static final String TAG = "TAG";
    public static Franchise currentFranchise = null;
    static String passcode;
    static String password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSpinner();

        auth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(v -> {

            binding.btnLogin.setEnabled(false);
            binding.pbAuth.setVisibility(View.VISIBLE);

            passcode = Objects.requireNonNull(binding.etPasscode.getText()).toString();
            password = Objects.requireNonNull(binding.etPassword.getText()).toString();

            if(passcode.length()!=6) {

                binding.etPasscode.setError("Passcode should be 6 characters long");
                binding.btnLogin.setEnabled(true);
                binding.pbAuth.setVisibility(View.INVISIBLE);

            } else if(password.isEmpty()) {

                binding.etPassword.setError("Password cannot be empty");
                binding.btnLogin.setEnabled(true);
                binding.pbAuth.setVisibility(View.INVISIBLE);

            } else {

                int selected = binding.spSelectAdmin.getSelectedItemPosition();
                if(selected == 3)
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("franchiseV2");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String key = dataSnapshot.getKey();

                                if (key.equals(passcode)) {
                                    String password = dataSnapshot.child("password").getValue().toString();
                                    if (password.equals(password)) {

                                        currentFranchise = dataSnapshot.getValue(Franchise.class);

                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE).edit();
                                        editor.putString("passcode", passcode);
                                        editor.putString("password", password);
                                        editor.putString("type", "franchise");
                                        editor.apply();


                                        if(!UserLoginFragment.isUserLoggedIn) {
                                            UserLoginFragment.isUserLoggedIn = true;
                                            Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToFranchiseDashboardFragment());
                                        }
                                        else {
                                            Toast.makeText(getContext(), "failed to login", Toast.LENGTH_LONG).show();
                                        }
                                }
                            }
                        }
                    }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    String postString = "";

                    if (selected == 1) {
                        postString = "@crmadmin.com";
                    } else if (selected == 5) {//not
                        postString = "@deadmin.com";
                    } else if (selected == 4) {//not in use
                        postString = "@veadmin.com";
                    } else if (selected == 2) {
                        postString = "@masteradmin.com";
                    }

                    auth.signInWithEmailAndPassword(passcode + postString, password).addOnCompleteListener(task -> {
                        binding.btnLogin.setEnabled(true);
                        binding.pbAuth.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            navigateTo(v, selected);
                        } else {
                            Snackbar.make(v, "Authorisation Failed", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        binding.tvForgotPassword.setOnClickListener(v -> Navigation.findNavController(v).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToForgetpassword()));
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.select_admin_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectAdmin.setAdapter(adapter);
    }

    private void navigateTo(View view, int pos) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("infos", Context.MODE_PRIVATE).edit();
        switch (pos) {

            case 1:
                selectedAdmin = 1;
                editor.putString("type", "crm");
                editor.putString("passcode",passcode);
               // Log.e(TAG, "navigateTo: "+passcode )
                editor.apply();
                Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToCrmAdminFragment());
                break;
            case 4://not in use
                selectedAdmin = 4;
                editor.putString("type", "data");
                editor.putString("passcode",passcode);
                // Log.e(TAG, "navigateTo: "+passcode );
                editor.apply();
                Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToDataEntryAdminFragment());
                break;
            case 5://not in use
                selectedAdmin = 5;
                editor.putString("type", "video");
                editor.putString("passcode",passcode);
                // Log.e(TAG, "navigateTo: "+passcode );
                editor.apply();
                Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToVideoEditorNavigation());
                break;
            case 2:
                selectedAdmin = 2;
                Log.e(TAG, "navigateTo: "+"master" );
                editor.putString("type", "master");
                editor.putString("passcode",passcode);
                editor.putString("password",password);
                editor.apply();
                Navigation.findNavController(view).navigate(AdminLoginFragmentDirections.actionAdminLoginFragmentToMasterDashboardFragment());
                break;
        }
    }

}