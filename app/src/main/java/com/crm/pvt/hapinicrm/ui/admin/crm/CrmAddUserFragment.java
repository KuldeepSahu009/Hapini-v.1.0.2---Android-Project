package com.crm.pvt.hapinicrm.ui.admin.crm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.databinding.FragmentCrmAddUserBinding;

import org.jetbrains.annotations.NotNull;

public class CrmAddUserFragment extends Fragment {

    private FragmentCrmAddUserBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}