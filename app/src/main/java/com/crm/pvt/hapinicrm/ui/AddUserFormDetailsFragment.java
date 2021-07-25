package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;

public class AddUserFormDetailsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user_form_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = "";
        if(AddUserFragment.addUserType != null) {
            if (AddUserFragment.addUserType.equals("CRM")) {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO CRM IN FUTURE
                 title = " ADD CRM USER";
            } else if (AddUserFragment.addUserType.equals("DE"))
            {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO DATA ENTRY IN FUTURE
                title = " ADD DATA ENTRY USER";
            }
            else
            {
                //VISIBLE EDITTEXT SPECIALLY RELATED TO VIDEO EDITOR IN FUTURE
                title = " ADD VIDEO EDITOR USER";
            }
        }
        TextView tvAddUserFormTitle = view.findViewById(R.id.tvAddUserFormDashboardTitle);
        tvAddUserFormTitle.setText(title);

        view.findViewById(R.id.btnAddUserSubmit).setOnClickListener(v->
        {
            CheckBox cvAddUserFormTermsAndCondiotion = view.findViewById(R.id.cvAddUserFormTermsAndCondiotion);
            if(cvAddUserFormTermsAndCondiotion.isChecked())
            {
                // Add User to Model
            }
            else
            {
                Toast.makeText(getContext(),"Please Accept all Terms and Conditions!.",Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.ivBackFromAddUserFormFragment).setOnClickListener(v ->
        {
            Navigation.findNavController(v).navigateUp();
        });
    }
}