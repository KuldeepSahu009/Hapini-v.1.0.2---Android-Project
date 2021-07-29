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
    Bundle admin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackAdminBinding.inflate(inflater,container,false);
        admin = new Bundle();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.trackCRMAdminCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.putString("ADMIN" , "crm");
                Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment());
            }
        });

        binding.trackVideoEditorAdminCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.putString("ADMIN" , "video_editor");
                Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment());
            }
        });

        binding.trackDataEntryAdminCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.putString("ADMIN" , "data_entry");
                Navigation.findNavController(v).navigate(TrackAdminFragmentDirections.actionTrackAdminFragmentToAdminDataViewFragment());
            }
        });

    }
}