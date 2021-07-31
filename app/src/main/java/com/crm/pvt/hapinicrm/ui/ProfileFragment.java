package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;


public class ProfileFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public ImageView ivProfilePic;
    public  TextView tvProfileName, tvProfileEmail, tvProfilePasscode, tvProfilePassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromProfile).setOnClickListener(v ->
                {
                    Navigation.findNavController(v).navigateUp();
                }
        );

        view.findViewById(R.id.tvProfileEditText).setOnClickListener(
                v ->
                        Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment)
        );
    }

    private void initializeAllUIComponents(View view) {
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvProfilePassword = view.findViewById(R.id.tvProfilePassword);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfilePasscode = view.findViewById(R.id.tvProfilePasscode);
    }



    private void getprofileinfo(){

    }
}