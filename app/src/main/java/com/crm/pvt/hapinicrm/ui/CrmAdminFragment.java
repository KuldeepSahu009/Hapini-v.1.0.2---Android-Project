package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Space;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminBinding;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrmAdminFragment extends Fragment {
    private FragmentCrmAdminBinding binding;
    private Boolean login = true;
    private FirebaseAuth auth;
    public static Admin currentCRMAdmin;
    public static  DatabaseReference activeStatusReference = FirebaseDatabase.getInstance().getReference("activeV2");
    Dialog dialog;
    Button okButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentCrmAdminBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        String attendancepasscode = bundle.getString("crmadminpasscode","");
        Bundle bundle1=new Bundle();
        bundle.putString("todialog",attendancepasscode);

        DatabaseReference currentCRMAdminRef = FirebaseDatabase.getInstance().getReference("adminV2").child("CRM");
        currentCRMAdminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usersSnapshot : snapshot.getChildren()) {
                    if(Splashscreen.spAdminsData != null)
                    if(Splashscreen.spAdminsData.getString("passcode","null").equals(usersSnapshot.getKey()))
                       currentCRMAdmin = usersSnapshot.getValue(Admin.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                currentCRMAdmin = null;
            }
        });
        if (login()) {
            login = false;
            Attendancedialogue attendancedialogue = new Attendancedialogue(getContext());
            attendancedialogue.setArguments(bundle1);
            attendancedialogue.show(getFragmentManager(), "attendance dialogue");
            Attendancedialogue.type="crmadmin";
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new Dialog(getContext());
        auth = FirebaseAuth.getInstance();

        binding.cvChatCrmAdmin.setOnClickListener( v ->
//                Navigation.findNavController(v)
//        .navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToCrmAdminChatFragment())
                setUpCustomDialogBox()
        );

        binding.crmadminAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserFragment.addUserType = "CRM";
                Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToAdduserdata());
            }
        });
        binding.crmadmintrackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "crmUser");
                if(Splashscreen.spAdminsData != null)
                AdminDataViewFragment.trackUserUnderThisAdminPasscode = Splashscreen.spAdminsData.getString("passcode","");
                Navigation.findNavController(v).navigate(R.id.action_crmAdminFragment_to_alltrackusersfragment, bundle);


            }
        });
        binding.crmadminprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment.user="";
                Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToSetrpofile());
            }
        });
        //


        binding.cvVerifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToCrmUserVerification());
            }
        });

        binding.ivCrmLogout.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                auth.signOut();
                Splashscreen.spAdminsData.edit().clear().commit();
                Navigation.findNavController(v).navigateUp();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
            });

            AlertDialog attendanceDialog = builder.create();
            attendanceDialog.show();
        });

        binding.cvGiveTask.setOnClickListener(v -> Navigation.findNavController(v).
                navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToCrmAdminGiveTaskFragment()));
binding.sendcsvfiletouser.setOnClickListener(v -> {
    Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToCrmadmincsvfilesend());
});
      binding.recievecsvfileadminfromfranchise.setOnClickListener(v-> {
          Navigation.findNavController(v).navigate(CrmAdminFragmentDirections.actionCrmAdminFragmentToRecievecsvfileadmin());
      });

    }

    private boolean login() {
        return login;
    }
    private void setUpCustomDialogBox() {
        dialog = new Dialog(dialog.getContext());
        dialog.setContentView(R.layout.custom_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}