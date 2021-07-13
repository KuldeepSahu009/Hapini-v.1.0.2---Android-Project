package com.crm.pvt.hapinicrm.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.databinding.FragmentSelectTypeBinding;

import org.jetbrains.annotations.NotNull;

public class SelectTypeFragment extends Fragment {

    private FragmentSelectTypeBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTypeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvAdminMaster.setOnClickListener( v ->
                Navigation.findNavController(v).navigate(SelectTypeFragmentDirections.actionSelectTypeFragmentToMasterLoginFragment())
        );

        binding.cvAdminCrm.setOnClickListener(v ->
            Navigation.findNavController(v).navigate(SelectTypeFragmentDirections.actionSelectTypeFragmentToCrmLoginFragment())
        );

        binding.cvAdminDataEntry.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(SelectTypeFragmentDirections.actionSelectTypeFragmentToDataEntryOperatorLoginFragment())
        );

        binding.cvAdminVideoEditor.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(SelectTypeFragmentDirections.actionSelectTypeFragmentToVideoEditorLoginFragment())
        );
    }
}