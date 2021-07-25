package com.crm.pvt.hapinicrm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;

public class AdminDataViewFragment extends Fragment {

    FragmentAdminDataViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDataViewBinding.inflate(inflater , container , false);
        return binding.getRoot();
    }
}