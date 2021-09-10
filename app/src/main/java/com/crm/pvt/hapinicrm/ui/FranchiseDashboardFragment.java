package com.crm.pvt.hapinicrm.ui;

import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchiseDashboardBinding;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FranchiseDashboardFragment extends Fragment {

    private FragmentFranchiseDashboardBinding binding;
    public static String addAdminTypes;
    private boolean attendance=false;
    private static final String TAG = "TAG";
    private Bundle admin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFranchiseDashboardBinding.inflate(inflater,container,false);
        admin = new Bundle();
        if (attendance==false){
            Attendancedialogue attendancedialogue = new Attendancedialogue(getContext());
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
            Attendancedialogue.type="franchiseadmin";
            attendance=true;
        }
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeCurrentFranchise();
        binding.cvFranchiseChats.setOnClickListener(v -> Navigation
                .findNavController(v)
                .navigate(FranchiseDashboardFragmentDirections
                        .actionFranchiseDashboardFragmentToFranchiseUserChatFragment()));

        binding.addadminfromfranchiseadmin.setOnClickListener(v -> {
            addAdminTypes = "CRM";
            Navigation
                    .findNavController(v)
                    .navigate(FranchiseDashboardFragmentDirections
                            .actionFranchiseDashboardFragmentToAddAdminFormDetailsFragment2());
        });

        binding.sendcsvfile.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(FranchiseDashboardFragmentDirections
                    .actionFranchiseDashboardFragmentToCsvfilefromfranchise());
        });

        binding.trackadminfromfranchiseadmin.setOnClickListener(v -> {
            admin.putString("ADMIN","crm");
            Navigation
                    .findNavController(v)
                    .navigate(R.id.action_franchiseDashboardFragment_to_adminDataViewFragment2,admin);
        });
        binding.profileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment.user="";
                Navigation
                        .findNavController(v)
                        .navigate(R.id.franchisetoprofilefragment,admin);
            }
        });
        binding.franchiseadminlogout.setOnClickListener(v->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
               // if(Splashscreen.spAdminsData != null)
                    Log.e(TAG, "onViewCreated: "+"yes");
                FirebaseAuth.getInstance().signOut();

                //Splashscreen.spAdminsData.edit().clear().commit();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE).edit();
                editor.putString("passcode", "no data");
                editor.putString("password", "no data");
                editor.putString("type", "no data");
                editor.apply();
                Splashscreen.isFranchise=false;
                Navigation.findNavController(getView()).navigate(FranchiseDashboardFragmentDirections.actionFranchiseDashboardFragmentToStartFragment());
            });
            builder.setNegativeButton("No", (dialog, which) -> {
            });

            AlertDialog logoutDialog = builder.create();
            logoutDialog.show();
        });
    }

    private void initializeCurrentFranchise() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("franchiseV2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (!Splashscreen.spAdminsData.getString("passcode", "").equals("")) {
                        if (key.equals(Splashscreen.spAdminsData.getString("passcode", ""))) {
                            String password = dataSnapshot.child("password").getValue().toString();
                            if (password.equals(password)) {
                                currentFranchise = dataSnapshot.getValue(Franchise.class);
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
}