package com.crm.pvt.hapinicrm.ui.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.databinding.FragmentResetPasswordBinding;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment() { }

    private static FragmentResetPasswordBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentResetPasswordBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}