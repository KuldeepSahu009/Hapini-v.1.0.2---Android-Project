package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentStartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class StartFragment extends Fragment {


    private FragmentStartBinding binding;
    private FirebaseUser user;
    public static int selectedAdmin = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        binding = FragmentStartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAdmin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(StartFragmentDirections.actionStartFragmentToAdminLoginFragment())
        );
        binding.btnUser.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(StartFragmentDirections.actionStartFragmentToUserLoginFragment())
        );

        // for admin
        if(user != null) {
            String email = user.getEmail();
            if(email != null && email.contains("crmadmin")) {
                selectedAdmin = 1;
                Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToCrmAdminFragment());
            } else if(email != null && email.contains("veadmin")) {
                Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToVideoEditorNavigation());
            } else if(email != null && email.contains("deadmin")) {
                Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToDataEntryAdminFragment());
            } else if(email != null && email.contains("masteradmin")) {
                selectedAdmin = 2;
                Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToMasterNavigation());
            }
        }

        // for user

        if(Splashscreen.usertype != null) {
            switch (Splashscreen.usertype) {
                case "crmuser":
                    Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToCrmUserFragment());
                    break;
                case "datauser":
                    Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToDataEntryUserFragment());
                    break;
                case "videouser":
                    Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToVideoEditorUserFragment());
                    break;
            }
        }

        if(Splashscreen.isFranchise)
        {
            //After Designed Logged out button in Layput
        //  Navigation.findNavController(view).navigate(StartFragmentDirections.actionStartFragmentToFranchiseDashboardFragment());
        }
    }

}