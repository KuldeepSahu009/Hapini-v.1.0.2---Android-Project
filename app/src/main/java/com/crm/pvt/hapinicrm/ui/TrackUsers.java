package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.adapters.TrackUserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackUsersBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;

import java.util.ArrayList;
import java.util.List;


public class TrackUsers extends Fragment {

    private static final String TAG = "TAG";
    private FragmentTrackUsersBinding binding;
    private TrackUserAdapter trackUserAdapter;
    private List<TrackUserModel> trackUserModelList;
    private String data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrackUsersBinding.inflate(inflater, container, false);

        data = getArguments().getString("data");
        Log.e(TAG, "onCreateView: " + data);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        trackUserModelList = new ArrayList<>();
        binding.rvTrackUser.setLayoutManager(new LinearLayoutManager(getContext()));
        trackUserAdapter = new TrackUserAdapter(getContext(), trackUserModelList);
        binding.rvTrackUser.setAdapter(trackUserAdapter);

        switch (data) {
            case "crmUser":
                getCrmData();
                break;
            case "videoUser":
                getVideoEditorData();
                break;
            case "dataUser":
                getDataEntryOperatorData();
                break;
        }

    }

    private void getCrmData() {
        trackUserModelList.add(new TrackUserModel("Satyam Kumar", "crm@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name1", "crm@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name2", "crm@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name3", "crm@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name4", "crm@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", "https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));

        trackUserAdapter.notifyDataSetChanged();
    }

    private void getVideoEditorData() {
        trackUserModelList.add(new TrackUserModel("Satyam Kumar", "video@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name1", "vidoe@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name2", "video@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name3", "video@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name4", "video@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", "https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));

        trackUserAdapter.notifyDataSetChanged();

    }

    private void getDataEntryOperatorData() {
        trackUserModelList.add(new TrackUserModel("Satyam Kumar", "data@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name1", "data@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name2", "data@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name3", "data@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", ""));
        trackUserModelList.add(new TrackUserModel("Random name4", "data@gmail.com", "47474096", "749402338", "4747444", "57575fbf", "some where,still searching,lets see", "https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));

        trackUserAdapter.notifyDataSetChanged();

    }
}