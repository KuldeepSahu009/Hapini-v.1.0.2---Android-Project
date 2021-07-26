package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentAddAdminBinding;

public class AddAdminFragment extends Fragment {

    private FragmentAddAdminBinding binding;
    public static String addAdminType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cvAddCRMAdmin.setOnClickListener(v -> {
            addAdminType = "CRM";
            Navigation.findNavController(v).navigate(AddAdminFragmentDirections.actionAddAdminFragmentToAddAdminFormDetailsFragment());
        });
        binding.cvAddDataEntryAdmin.setOnClickListener(v -> {
            addAdminType = "DE";
            Navigation.findNavController(v).navigate(AddAdminFragmentDirections.actionAddAdminFragmentToAddAdminFormDetailsFragment());
        });
        binding.cvAddVideoEditorAdmin.setOnClickListener(v -> {
            addAdminType = "VE";
            Navigation.findNavController(v).navigate(AddAdminFragmentDirections.actionAddAdminFragmentToAddAdminFormDetailsFragment());
        });
        binding.ivBackFromAddAdminFragment.setOnClickListener(v->
                Navigation.findNavController(v).navigateUp());
    }
}