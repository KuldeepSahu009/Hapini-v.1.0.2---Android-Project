package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.content.Intent;
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

import java.security.PublicKey;


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
    public static ImageView ivProfilePic;
    public static TextView tvProfileName , tvProfileEmail , tvProfilePasscode , tvProfilePassword;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromProfile).setOnClickListener(v->
                {
                    Navigation.findNavController(v).navigateUp();
                }
        );

        view.findViewById(R.id.tvProfileEditText).setOnClickListener(
                v->
                Navigation.findNavController(v).navigate(ProfileFragmentDirections.actionProfileFragmentToEditMasterProfileFragment())
        );
    }
    private void initializeAllUIComponents(View view)
    {
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
tvProfilePassword = view.findViewById(R.id.tvProfilePassword);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfilePasscode = view.findViewById(R.id.tvProfilePasscode);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(EditMasterProfileFragment.ivProfilePicURI != null && EditMasterProfileFragment.profileName != null )
        {
            try {
                Glide.with(getView()).load(EditMasterProfileFragment.ivProfilePicURI).into(ivProfilePic);
                tvProfileName.setText(EditMasterProfileFragment.profileName);
                tvProfileEmail.setText(EditMasterProfileFragment.profileEmail);
                tvProfilePasscode.setText(EditMasterProfileFragment.profilePasscode);
                tvProfilePassword.setText(EditMasterProfileFragment.profilePassword);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(EditMasterProfileFragment.ivProfilePicURI != null && EditMasterProfileFragment.profileName != null)
        {
            try {
            Glide.with(getView()).load(EditMasterProfileFragment.ivProfilePicURI).into(ivProfilePic);
            tvProfileName.setText(EditMasterProfileFragment.profileName);
            tvProfileEmail.setText(EditMasterProfileFragment.profileEmail);
            tvProfilePasscode.setText(EditMasterProfileFragment.profilePasscode);
            tvProfilePassword.setText(EditMasterProfileFragment.profilePassword);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }
    }
}