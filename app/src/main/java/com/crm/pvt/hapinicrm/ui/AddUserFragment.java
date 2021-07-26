package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentAddUserBinding;

public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public static String addUserType;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       binding.cvAddCRMUser.setOnClickListener(v -> {
           addUserType = "CRM";
           Navigation.findNavController(v).navigate(AddUserFragmentDirections.actionAddUserFragmentToAddUserFormDetailsFragment());
       });
        binding.cvAddDataEntryUser.setOnClickListener(v -> {
            addUserType = "DE";
            Navigation.findNavController(v).navigate(AddUserFragmentDirections.actionAddUserFragmentToAddUserFormDetailsFragment());
        });
        binding.cvAddVideoEditorUser.setOnClickListener(v -> {
            addUserType = "VE";
            Navigation.findNavController(v).navigate(AddUserFragmentDirections.actionAddUserFragmentToAddUserFormDetailsFragment());
        });
        binding.ivBackFromAddUserFragment.setOnClickListener(v->
                Navigation.findNavController(v).navigateUp());
    }
}