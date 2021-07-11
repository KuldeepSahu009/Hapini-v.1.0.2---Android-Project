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
import com.crm.pvt.hapinicrm.databinding.FragmentUserBinding;

import org.jetbrains.annotations.NotNull;


public class UserFragment extends Fragment {

    public UserFragment() { }

    private static FragmentUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvUserCrm.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_userLoginFragment);
        });

        binding.cvUserDataEntry.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_userLoginFragment);
        });

        binding.cvUserVideEditor.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_userLoginFragment);
        });

        binding.cvUserMaster.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_userLoginFragment);
        });

    }
}