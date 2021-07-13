package com.crm.pvt.hapinicrm.ui.admin.master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.adapter.AdminListAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminListBinding;
import com.crm.pvt.hapinicrm.model.Admin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminListFragment extends Fragment {

    private FragmentAdminListBinding binding;
    private final List<Admin> adminList = new ArrayList<>();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adminList.add(new Admin("Kuldeep Sahu","CRM","kuldeep@admin.com","9012345678","2 Feb 2021"));
        adminList.add(new Admin("Suyash Shukla","Video Editor","suyash@admin.com","9012345678","2 Mar 2021"));
        adminList.add(new Admin("Challa Rashmitha","Data Entry Operator","rashmitha@admin.com","9012345678","2 Apr 2021"));
        adminList.add(new Admin("Abhishek","Data Entry Operator","abhishek@admin.com","9012345678","2 May 2021"));
        adminList.add(new Admin("Aditya Singh","Video Editor","aditya@admin.com","9012345678","2 Jun 2021"));
        binding.rvAdmins.setAdapter(new AdminListAdapter(adminList));

    }
}