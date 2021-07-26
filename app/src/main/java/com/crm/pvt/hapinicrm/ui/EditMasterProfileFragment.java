package com.crm.pvt.hapinicrm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;

import java.util.HashSet;


public class EditMasterProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_master_profile, container, false);
    }

    private View thisView;
    private EditText etProfileName , etProfileEmail , etProfilePasscode , etProfilePassword;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thisView = view;
        initializeAllUIComponents(view);
        view.findViewById(R.id.ivBackFromEditMasterDetailFragment).setOnClickListener(v->

            Navigation.findNavController(v).navigateUp()

        );
        view.findViewById(R.id.btnMasterDetailsChange).setOnClickListener(v->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), 1000);
        });
        view.findViewById(R.id.ivSaveEditMasterDetailFragment).setOnClickListener( v->
                Navigation.findNavController(thisView).navigate(EditMasterProfileFragmentDirections.actionEditMasterProfileFragmentToProfileFragment())
         );
    }
    private void initializeAllUIComponents(View view)
    {
        etProfileName = view.findViewById(R.id.etProfileName);
        etProfileEmail = view.findViewById(R.id.etProfileEmail);
        etProfilePasscode = view.findViewById(R.id.etProfilePasscode);
        etProfilePassword = view.findViewById(R.id.etProfilePassword);
    }

    public  static Uri ivProfilePicURI;
    public static String profileName;
    public static String profileEmail;
    public static String profilePasscode;
    public static String profilePassword;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK)
        {
            if(data != null)
            {
                ivProfilePicURI = data.getData();
                sendDataToPreviousFragment();
            }
        }
    }

    private void sendDataToPreviousFragment()
    {


            profileName = etProfileName.getText().toString();
            profileEmail = etProfileEmail.getText().toString();
            profilePasscode = etProfilePasscode.getText().toString();
            profilePassword = etProfilePassword.getText().toString();

    }
}