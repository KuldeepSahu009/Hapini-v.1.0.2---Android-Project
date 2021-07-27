package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUserBinding;

public class TrackUserFragment extends Fragment {

    private FragmentTrackUserBinding binding;
    private Bundle data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackUserBinding.inflate(inflater, container, false);
        data = new Bundle();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cvCrmUser.setOnClickListener(v -> {
            data.putString("data", "crmUser");
            Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);

        });

        binding.cvDataEntryUser.setOnClickListener(v -> {
            data.putString("data", "dataUser");
            Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);
        });

        binding.cvVideoEditorUser.setOnClickListener(v -> {
            data.putString("data", "videoUser");
            Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);
        });

    }
}