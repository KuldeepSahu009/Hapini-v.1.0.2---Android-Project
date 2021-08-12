package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentCrmUserBinding;

public class CrmUserFragment extends Fragment {

    private FragmentCrmUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvSeeTaskAssigned.
                setOnClickListener(v ->
                        Navigation.findNavController(v)
                                .navigate(CrmUserFragmentDirections.actionCrmUserFragmentToTaskListFragment()));

        binding.cvChatWithAdmin.setOnClickListener(v -> {

        });
    }
}