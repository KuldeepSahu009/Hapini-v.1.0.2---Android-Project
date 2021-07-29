package com.crm.pvt.hapinicrm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.adapters.TrackAdminAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAdminDataViewBinding;
import com.crm.pvt.hapinicrm.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDataViewFragment extends Fragment {

    FragmentAdminDataViewBinding binding;
    List<Admin> admins = new ArrayList<>();
    TrackAdminAdapter trackAdminAdapter;
    String admin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentAdminDataViewBinding.inflate(inflater , container , false);
        admin = getArguments().getString("ADMIN");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.trackAdminRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trackAdminAdapter = new TrackAdminAdapter(getContext(), admins);
        binding.trackAdminRecyclerView.setAdapter(trackAdminAdapter);

        switch (admin) {
            case "crm":
                getCrmAdminData();
                break;
            case "video_editor":
                getVideoEditorAdminData();
                break;
            case "data_entry":
                getDataEntryAdminData();
                break;
        }
    }

     void getDataEntryAdminData(){
        admins.add(new Admin("Person 1" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 2" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 3" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 4" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 5" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 6" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 7" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 8" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 9" , "xyz@example.com" , "9000000000" , "Somewhere on earth"));
        trackAdminAdapter.notifyDataSetChanged();
    }

    void getVideoEditorAdminData(){
        admins.add(new Admin("Person 1" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 2" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 3" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 4" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 5" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 6" , "xyz@protonmail.com" , "9000000000" , "Somewhere on earth"));
        trackAdminAdapter.notifyDataSetChanged();
    }

    void getCrmAdminData(){
        admins.add(new Admin("Person 1" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 2" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 3" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 4" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 5" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        admins.add(new Admin("Person 6" , "xyz@gmail.com" , "9000000000" , "Somewhere on earth"));
        trackAdminAdapter.notifyDataSetChanged();
    }
}