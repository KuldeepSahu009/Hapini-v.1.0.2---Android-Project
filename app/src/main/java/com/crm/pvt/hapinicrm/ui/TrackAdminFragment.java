package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackAdminBinding;

public class TrackAdminFragment extends Fragment {

    FragmentTrackAdminBinding binding;
    CardView trackCRMAdminCardView, trackVideoEditorAdminCardView , trackDataEntryAdminCardView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {
        trackCRMAdminCardView = view.findViewById(R.id.trackCRMAdminCardView);
        trackDataEntryAdminCardView= view.findViewById(R.id.trackDataEntryAdminCardView);
        trackVideoEditorAdminCardView = view.findViewById(R.id.trackVideoEditorAdminCardView);

        trackCRMAdminCardView.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_trackAdminFragment_to_adminDataViewFragment));

        trackVideoEditorAdminCardView.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_trackAdminFragment_to_adminDataViewFragment));

        trackDataEntryAdminCardView.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_trackAdminFragment_to_adminDataViewFragment));

    }
}