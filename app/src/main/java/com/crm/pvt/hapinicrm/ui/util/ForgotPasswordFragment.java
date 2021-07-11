package com.crm.pvt.hapinicrm.ui.util;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentForgotPasswordBinding;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordFragment extends Fragment {

    public ForgotPasswordFragment() { }

    private static FragmentForgotPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSendOtp.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_forgotPasswordFragment_to_resetPasswordFragment);
        });
    }
}