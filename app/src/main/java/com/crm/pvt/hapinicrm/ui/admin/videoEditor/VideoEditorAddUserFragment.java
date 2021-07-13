package com.crm.pvt.hapinicrm.ui.admin.videoEditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.crm.pvt.hapinicrm.databinding.FragmentVideoEditorAddUserBinding;

import org.jetbrains.annotations.NotNull;

public class VideoEditorAddUserFragment extends Fragment {

    private FragmentVideoEditorAddUserBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVideoEditorAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}