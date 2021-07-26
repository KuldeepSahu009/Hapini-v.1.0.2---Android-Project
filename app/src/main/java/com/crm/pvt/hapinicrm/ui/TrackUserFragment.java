package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUserBinding;

public class TrackUserFragment extends Fragment {

    private static final String TAG = "TAG";
    private FragmentTrackUserBinding binding;
    Bundle data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackUserBinding.inflate(inflater, container, false);
        data = new Bundle();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.crmtrackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putString("data", "crmuser");
                Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);

            }
        });
        binding.dataentrytrackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putString("data", "datauser");
                Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);
            }
        });
        binding.videoeditortrackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putString("data", "videouser");
                Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);
            }
        });

    }
//    binding.crmtrackuser.setOnClickListener(v ->
//            Navigation.findNavController(v).navigate(TrackUserFragmentDirections.gotospecifictrackuser(),crm));
//
}