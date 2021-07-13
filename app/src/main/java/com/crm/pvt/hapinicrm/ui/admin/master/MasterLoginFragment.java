package com.crm.pvt.hapinicrm.ui.admin.master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentMasterLoginBinding;

import org.jetbrains.annotations.NotNull;

public class MasterLoginFragment extends Fragment {

    private FragmentMasterLoginBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMasterLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(MasterLoginFragmentDirections.actionMasterLoginFragmentToAdminListFragment())
        );

    }
}