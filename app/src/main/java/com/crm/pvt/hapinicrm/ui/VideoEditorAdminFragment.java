package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentVideoEditorAdminBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class VideoEditorAdminFragment extends Fragment {

    private FragmentVideoEditorAdminBinding binding;
    private Bundle data;
    private boolean firstLogin = true;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoEditorAdminBinding.inflate(inflater,container,false);
        data = new Bundle();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (firstLogin()) {
            firstLogin = false;
            showAttendanceDialog();
        }

        binding.cvAddUser.setOnClickListener(v -> {
            AddUserFragment.addUserType = "VE";
            Navigation.findNavController(v)
                    .navigate(VideoEditorAdminFragmentDirections.actionVideoEditorAdminFragmentToAddUserFormDetailsFragment());
        });

        binding.cvTrackVideoEditorUser.setOnClickListener(v -> {
            data.putString("data", "videoUser");
            Navigation.findNavController(v).navigate(R.id.action_videoEditorAdminFragment_to_trackUsers, data);
        });

        binding.cvProfileVideoEditor.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(VideoEditorAdminFragmentDirections.actionVideoEditorAdminFragmentToProfileFragment())
        );

        binding.ivVideoEditorLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                auth.signOut();
                Navigation.findNavController(v).navigateUp();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
            });

            AlertDialog attendanceDialog = builder.create();
            attendanceDialog.show();
        });

    }

    private boolean firstLogin() {
        // will contain login to check if it's today's first login or not.
        return firstLogin;
    }

    private void showAttendanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Mark your attendance");
        builder.setTitle("Welcome Back");
        builder.setCancelable(false);
        builder.setPositiveButton("Mark Present", (dialog, which) -> Snackbar.make(getView(),"Attendance Marked",Snackbar.LENGTH_SHORT).show());

        AlertDialog attendanceDialog = builder.create();
        attendanceDialog.show();
    }
}