package com.crm.pvt.hapinicrm.ui.admin.dataEntryOperator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.databinding.FragmentDataEntryOperatorAddUserBinding;

import org.jetbrains.annotations.NotNull;

public class DataEntryOperatorAddUserFragment extends Fragment {

    private FragmentDataEntryOperatorAddUserBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDataEntryOperatorAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}