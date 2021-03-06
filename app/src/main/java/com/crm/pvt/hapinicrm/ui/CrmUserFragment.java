package com.crm.pvt.hapinicrm.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserBinding;

public class CrmUserFragment extends Fragment {

    private FragmentCrmUserBinding binding;
    private boolean attendance=false;
    private static final String TAG = "TAG";
    Dialog dialog;
    Button okButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new Dialog(getContext());
            if (attendance==false){
                SharedPreferences getshared = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                String passcode=getshared.getString("passcode","no data");
                Attendancedialogue attendancedialogue = new Attendancedialogue(getContext());
                attendancedialogue.show(getFragmentManager(), "attendance dialogue");
                Attendancedialogue.type="crmuser";
                Log.e(TAG, "onViewCreated: "+passcode );
                attendance=true;
            }

        binding.cvSeeTaskAssigned.
                setOnClickListener(v ->
                        Navigation.findNavController(v)
                                .navigate(CrmUserFragmentDirections.actionCrmUserFragmentToTaskListFragment()));

        binding.cvChatWithAdmin.setOnClickListener(v -> {
            setUpCustomDialogBox();
            //Navigation.findNavController(v).navigate(CrmUserFragmentDirections.actionCrmUserFragmentToCrmUserChatFragment());
        });

        binding.cvVerifyYourself.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_crmUserFragment_to_verificationByAdmin));
        binding.ivCrmUserLogout.setOnClickListener(v -> {
            crmUserLogout(v);
        });
        binding.cvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment.user="user";
                Navigation.findNavController(v).navigate(R.id.crmusertoprofilefragment);
            }
        });

    }

    private void crmUserLogout(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Splashscreen.spUsersData.edit().clear().commit();
            Splashscreen.usertype=null;
            Navigation.findNavController(v).navigate(R.id.crmtostartfragment);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });

        AlertDialog logoutDialog = builder.create();
        logoutDialog.show();
    }

    @Override
    public void onStop() {//To prevent user from being logged in even when not working
        Splashscreen.spUsersData.edit().clear().commit();
        Splashscreen.usertype=null;
        super.onStop();
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