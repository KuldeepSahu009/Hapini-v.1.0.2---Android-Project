package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.crm.pvt.hapinicrm.Adapters.MasterviewAdapter;
import com.crm.pvt.hapinicrm.Adapters.videoeditoradminAdapter;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.MasterviewModel;
import com.crm.pvt.hapinicrm.model.videoeditorviewmodel;

import java.util.ArrayList;

public class Videoeditoradmin extends AppCompatActivity {
    private videoeditoradminAdapter videoeditoradminAdapter;
    ArrayList<videoeditorviewmodel> videoeditorviewmodelArrayList;
    RecyclerView recyclerViewvideoeditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoeditoradmin);
        recyclerViewvideoeditor=findViewById(R.id.videoeditoruserrecycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerViewvideoeditor.setLayoutManager(layoutManager);
        recyclerViewvideoeditor.setHasFixedSize(true);
        videoeditorviewmodelArrayList=new ArrayList<>();
        videoeditorviewmodelArrayList.add(new videoeditorviewmodel("Suyash shukla","mail@gmail.com","12345","lucknow"));
        videoeditorviewmodelArrayList.add(new videoeditorviewmodel("Aditya singh","mail@gmail.com","12345","Delhi"));
        videoeditorviewmodelArrayList.add(new videoeditorviewmodel("Challa Rashmita","mail@gmail.com","12345","xyz"));
        videoeditorviewmodelArrayList.add(new videoeditorviewmodel("Param","mail@gmail.com","12345","xyz"));
        videoeditorviewmodelArrayList.add(new videoeditorviewmodel("Satyam","mail@gmail.com","12345","xyz"));
        recyclerViewvideoeditor.setAdapter(new videoeditoradminAdapter(videoeditorviewmodelArrayList));
    }
}