package com.crm.pvt.hapinicrm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.databinding.FragmentSignUpBinding;

import org.jetbrains.annotations.NotNull;

public class SignUpFragment extends Fragment {

    public SignUpFragment() { }

    private static FragmentSignUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvLogin.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment);
        });
    }
}