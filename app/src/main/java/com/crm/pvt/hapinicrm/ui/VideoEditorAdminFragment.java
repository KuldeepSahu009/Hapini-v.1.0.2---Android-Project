package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.databinding.FragmentVideoEditorAdminBinding;

public class VideoEditorAdminFragment extends Fragment {

    private FragmentVideoEditorAdminBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoEditorAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}