package com.crm.pvt.hapinicrm.ui.welcome;

import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentStartBinding;

import org.jetbrains.annotations.NotNull;

public class StartFragment extends Fragment {

    public StartFragment() { }

    private static FragmentStartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnUser.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_userFragment);
        });
    }
}