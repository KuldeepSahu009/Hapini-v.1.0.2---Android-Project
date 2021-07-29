package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminLoginBinding;
import com.crm.pvt.hapinicrm.databinding.FragmentTaskAssignBinding;

import java.util.ArrayList;

public class TaskAssignFragment extends Fragment {

    FragmentTaskAssignBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskAssignBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> admins= new ArrayList<>();
        admins.add("CRM Admin");
        admins.add("Data Entry Admin");
        admins.add("Video Editor Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this.getContext(), android.R.layout.simple_spinner_item, admins);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.adminSpinner.setAdapter(adapter);
    }

}