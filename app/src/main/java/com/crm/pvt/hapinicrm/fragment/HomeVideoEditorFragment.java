package com.crm.pvt.hapinicrm.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Adapters.dataentryadminAdapter;
import com.crm.pvt.hapinicrm.Adapters.videoeditoradminAdapter;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.dataentryviewmodel;
import com.crm.pvt.hapinicrm.model.videoeditorviewmodel;

import java.util.ArrayList;
import java.util.List;

public class HomeVideoEditorFragment extends Fragment {
RecyclerView videoeditorrecyclerview;
List<videoeditorviewmodel> Videoeditor;
com.crm.pvt.hapinicrm.Adapters.videoeditoradminAdapter videoeditoradminAdapter1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home_video_editor, container, false);
        videoeditorrecyclerview=view.findViewById(R.id.videoeditoradminrecyclerview);
       videoeditorrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        Videoeditor=new ArrayList<>();
        videoeditoradminAdapter1=new videoeditoradminAdapter(Videoeditor);
       videoeditorrecyclerview.setAdapter(videoeditoradminAdapter1);

        setdata();

        return view;
    }

    private void setdata() {
        Videoeditor.add(new videoeditorviewmodel("Suyash shukla","mail@gmail.com","12345","lucknow"));
        Videoeditor.add(new videoeditorviewmodel("Aditya singh","mail@gmail.com","12345","Delhi"));
        Videoeditor.add(new videoeditorviewmodel("Challa Rashmita","mail@gmail.com","12345","xyz"));
        Videoeditor.add(new videoeditorviewmodel("Param","mail@gmail.com","12345","xyz"));
        Videoeditor.add(new videoeditorviewmodel("VSatyam","mail@gmail.com","12345","xyz"));

        videoeditoradminAdapter1.notifyDataSetChanged();
    }
}