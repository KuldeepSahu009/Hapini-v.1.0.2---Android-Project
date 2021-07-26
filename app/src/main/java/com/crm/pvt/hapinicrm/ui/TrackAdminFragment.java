package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackAdminBinding;

public class TrackAdminFragment extends Fragment {

    FragmentTrackAdminBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.cvCrmAdmin.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment()));

        binding.cvVideoEditorAdmin.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment()));

        binding.cvDataEntryAdmin.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment()));

    }
}