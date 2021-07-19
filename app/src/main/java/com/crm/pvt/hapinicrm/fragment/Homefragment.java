package com.crm.pvt.hapinicrm.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Adapters.crmadminAdapter;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.MasterviewModel;
import com.crm.pvt.hapinicrm.model.crmviewmodel;

import java.util.ArrayList;
import java.util.List;


public class Homefragment extends Fragment {
    RecyclerView crmdaminreyclerview;
    List<crmviewmodel> crmviewmodelList;
    com.crm.pvt.hapinicrm.Adapters.crmadminAdapter crmadminAdapter;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
        crmdaminreyclerview=view.findViewById(R.id.crmadminrecycler);
        crmdaminreyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        crmviewmodelList=new ArrayList<>();
        crmadminAdapter=new crmadminAdapter(getContext(),crmviewmodelList);
        crmdaminreyclerview.setAdapter(crmadminAdapter);

        setdata();


        return view;
    }
    private void setdata(){
        crmviewmodelList.add(new crmviewmodel("Suyash shukla","mail@gmail.com","12345","lucknow"));
        crmviewmodelList.add(new crmviewmodel("Aditya singh","mail@gmail.com","12345","Delhi"));
        crmviewmodelList.add(new crmviewmodel("Challa Rashmita","mail@gmail.com","12345","xyz"));
        crmviewmodelList.add(new crmviewmodel("Param","mail@gmail.com","12345","xyz"));
        crmviewmodelList.add(new crmviewmodel("Satyam","mail@gmail.com","12345","xyz"));

        crmadminAdapter.notifyDataSetChanged();

    }
}