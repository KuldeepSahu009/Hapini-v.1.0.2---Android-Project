package com.crm.pvt.hapinicrm.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUserBinding;

public class TrackUserFragment extends Fragment {

    private FragmentTrackUserBinding binding;
    private Bundle data;
    Dialog dialog;
    Button okButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackUserBinding.inflate(inflater, container, false);
        data = new Bundle();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new Dialog(getContext());

        binding.cvTrackCrmUser.setOnClickListener(v -> {
            data.putString("data", "crmUser");
            Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);

        });

        binding.cvTrackDataEntryAdmin.setOnClickListener(v -> {
            setUpCustomDialogBox();
        });

        binding.cvTrackVideoEditorAdmin.setOnClickListener(v -> {
            data.putString("data", "videoUser");
            Navigation.findNavController(v).navigate(R.id.gotospecifictrackuser,data);
        });
       binding.ivTrackUser.setOnClickListener(v->
               Navigation.findNavController(v).navigateUp());
    }

    private void setUpCustomDialogBox() {
        dialog = new Dialog(dialog.getContext());
        dialog.setContentView(R.layout.custom_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}