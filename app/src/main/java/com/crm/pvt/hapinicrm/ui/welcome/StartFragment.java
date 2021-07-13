package com.crm.pvt.hapinicrm.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentStartBinding;

import org.jetbrains.annotations.NotNull;

public class StartFragment extends Fragment {

    public StartFragment() { }

    private FragmentStartBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdmin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(StartFragmentDirections.actionStartFragmentToSelectTypeFragment())
        );
    }
}