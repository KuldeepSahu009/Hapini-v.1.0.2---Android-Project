package com.crm.pvt.hapinicrm.ui.admin.master;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapter.AdminListAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminListBinding;
import com.crm.pvt.hapinicrm.model.Admin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminListFragment extends Fragment {

    private FragmentAdminListBinding binding;
    private AdminListAdapter adminListAdapter;
    private final List<Admin> adminList = new ArrayList<>();
    EditText etName;
    EditText etEmail;
    EditText etMobileNo;
    EditText etWhatsappNo;
    EditText etPassword;
    Button btnSubmit;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAdmins();
        setAdapter();
        btnSubmit = new Button(getContext());
        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String mobileNumber = etMobileNo.getText().toString();
            String whatsappNumber = etWhatsappNo.getText().toString();
            String password = etPassword.getText().toString();
            String type = "CRM";
            String doj = "2 July 2021";
            Admin admin = new Admin(name, email, mobileNumber, whatsappNumber, password, type, doj);
            adminListAdapter.addAdmin(admin);
        });

        binding.spinnerAddAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    addCrmDetails();
                } else if (position == 2) {
                    addDataEntryOperatorDetails();
                } else if (position == 3) {
                    addVideoEditorDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAdapter() {

        adminListAdapter = new AdminListAdapter(adminList);
        binding.rvAdmins.setAdapter(adminListAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.admins_choice,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAddAdmin.setAdapter(adapter);
    }

    private void getAdmins() {
        adminList.add(new Admin("Kuldeep Sahu", "kuldeep@admin.com", "9012345678", "9012345678", "admin", "CRM", "2 Feb 2021"));
        adminList.add(new Admin("Suyash Shukla", "suyash@admin.com", "9012345678", "9012345678", "admin", "Video Editor", "2 Mar 2021"));
        adminList.add(new Admin("Challa Rashmitha", "rashmitha@admin.com", "9012345678", "9012345678", "admin", "Data Entry Operator", "2 Apr 2021"));
        adminList.add(new Admin("Aditya Singh", "aditya@admin.com", "9012345678", "9012345678", "admin", "Video Editor", "2 May 2021"));
    }

    private void addCrmDetails() {
        Dialog addAdmin = new Dialog(getContext());
        addAdmin.setContentView(R.layout.add_admin_dialog); // Attaching layout file
        addAdmin.getWindow().setElevation(20f);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(addAdmin.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        addAdmin.getWindow().setAttributes(layoutParams);
        addAdmin.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        addAdmin.getWindow().getAttributes().gravity = Gravity.TOP;
        addAdmin.show();
        getViewById(addAdmin, "CRM");
    }


    private void addDataEntryOperatorDetails() {
        Dialog addAdmin = new Dialog(getContext());
        addAdmin.setContentView(R.layout.add_admin_dialog); // Attaching layout file
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(addAdmin.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        addAdmin.getWindow().setAttributes(layoutParams);
        addAdmin.getWindow().setElevation(20f);
        addAdmin.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        addAdmin.getWindow().getAttributes().gravity = Gravity.TOP;
        addAdmin.show();
        getViewById(addAdmin, "Data Entry Operator");
    }

    private void addVideoEditorDetails() {
        Dialog addAdmin = new Dialog(getContext());
        addAdmin.setContentView(R.layout.add_admin_dialog); // Attaching layout file
        addAdmin.getWindow().setElevation(20f);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(addAdmin.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        addAdmin.getWindow().setAttributes(layoutParams);
        addAdmin.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        addAdmin.getWindow().getAttributes().gravity = Gravity.TOP;
        addAdmin.show();
        getViewById(addAdmin, "Video Editor");
    }


    private void getViewById(Dialog addAdmin, String adminType) {
        btnSubmit = addAdmin.findViewById(R.id.btnSubmit);
        etName = addAdmin.findViewById(R.id.etName);
        etEmail = addAdmin.findViewById(R.id.etEmail);
        etMobileNo = addAdmin.findViewById(R.id.etMobileNo);
        etWhatsappNo = addAdmin.findViewById(R.id.etWhatsappNo);
        etPassword = addAdmin.findViewById(R.id.etPassword);

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String mobileNumber = etMobileNo.getText().toString();
            String whatsappNumber = etWhatsappNo.getText().toString();
            String password = etPassword.getText().toString();
            String doj = "2 July 2021";
            Admin admin = new Admin(name, email, mobileNumber, whatsappNumber, password, adminType, doj);
            adminListAdapter.addAdmin(admin);
            addAdmin.cancel();
        });
    }
}