package com.crm.pvt.hapinicrm.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentUserLoginBinding;

import org.jetbrains.annotations.NotNull;

public class UserLoginFragment extends Fragment {

    public UserLoginFragment() { }

    private static FragmentUserLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvSignUp.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userLoginFragment_to_userSignupFragment);
        });

        binding.tvForgotPassword.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userLoginFragment_to_forgotPasswordFragment);
        });
    }
}