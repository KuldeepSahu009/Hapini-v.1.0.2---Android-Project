package com.crm.pvt.hapinicrm.ui.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentResetPasswordBinding;

public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment() { }

    private static FragmentResetPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentResetPasswordBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}