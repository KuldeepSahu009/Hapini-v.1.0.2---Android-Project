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
import com.crm.pvt.hapinicrm.databinding.FragmentTrackAdminBinding;

public class TrackAdminFragment extends Fragment {

    FragmentTrackAdminBinding binding;
    private Bundle admin;
    Dialog dialog;
    Button okButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackAdminBinding.inflate(inflater,container,false);
        admin = new Bundle();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {

        dialog = new Dialog(getContext());

        binding.trackCRMAdminCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                admin.putString("ADMIN" , "crm");
                Navigation.findNavController(v).navigate(R.id.action_trackAdminFragment_to_adminDataViewFragment, admin);
            }
        });

        binding.trackVideoEditorAdminCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                admin.putString("ADMIN" , "video_editor");
                Navigation.findNavController(v).navigate(R.id.action_trackAdminFragment_to_adminDataViewFragment, admin);
            }
        });

        binding.trackDataEntryAdminCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setUpDialogBox();
            }
        });
        binding.ivBackTrackAdmin.setOnClickListener(v->
                Navigation.findNavController(v).navigateUp());
    }

    private void setUpDialogBox() {
        dialog = new Dialog(dialog.getContext());
        dialog.setContentView(R.layout.custom_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}  