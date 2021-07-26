package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.TrackuserAdapter;
import com.crm.pvt.hapinicrm.databinding.FragmentTrackusersBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;

import java.util.ArrayList;
import java.util.List;


public class Trackusers extends Fragment {

    private static final String TAG = "TAG";
    private FragmentTrackusersBinding binding;
    RecyclerView recyclerView;
    TrackuserAdapter trackuserAdapter;
    List<TrackUserModel> trackUserModelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTrackusersBinding.inflate(inflater,container,false);

        recyclerView=binding.trackuserecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trackUserModelList=new ArrayList<>();
        trackuserAdapter=new TrackuserAdapter(getContext(),trackUserModelList);
        recyclerView.setAdapter(trackuserAdapter);

        String data=getArguments().getString("data");
        Log.e(TAG, "onCreateView: "+data );

        if (data.equals("crmuser")){
        getcrmdata();
        } else if (data.equals("videouser")){
            getvideodata();
        }else if (data.equals("datauser")){
            getdataentrydata();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getcrmdata(){
        trackUserModelList.add(new TrackUserModel("Satyam Kumar","crm@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name1","crm@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name2","crm@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name3","crm@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name4","crm@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see","https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));


        trackuserAdapter.notifyDataSetChanged();
    }
    private void getvideodata(){
        trackUserModelList.add(new TrackUserModel("Satyam Kumar","video@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name1","vidoe@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name2","video@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name3","video@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name4","video@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see","https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));


        trackuserAdapter.notifyDataSetChanged();

    }
    private void getdataentrydata(){
        trackUserModelList.add(new TrackUserModel("Satyam Kumar","data@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name1","data@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name2","data@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name3","data@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see",""));
        trackUserModelList.add(new TrackUserModel("Random name4","data@gmail.com","47474096","749402338","4747444","57575fbf","some where,still searching,lets see","https://static.toiimg.com/photo/68081708/Haunted-2.jpg?width=748&resize=4"));


        trackuserAdapter.notifyDataSetChanged();

    }
}