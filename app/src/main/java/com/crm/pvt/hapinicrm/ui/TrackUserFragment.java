package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.databinding.FragmentTrackUserBinding;

public class TrackUserFragment extends Fragment {

    private FragmentTrackUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}