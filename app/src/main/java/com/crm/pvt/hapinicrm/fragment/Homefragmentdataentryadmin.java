package com.crm.pvt.hapinicrm.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.Adapters.crmadminAdapter;
import com.crm.pvt.hapinicrm.Adapters.dataentryadminAdapter;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.crmviewmodel;
import com.crm.pvt.hapinicrm.model.dataentryviewmodel;

import java.util.ArrayList;
import java.util.List;


public class Homefragmentdataentryadmin extends Fragment {

    RecyclerView dataentrydaminreyclerview;
    List<dataentryviewmodel> dataentryadminviewmodelList;
    com.crm.pvt.hapinicrm.Adapters.dataentryadminAdapter dataentryadminAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homefragmentdataentryadmin, container, false);
        dataentrydaminreyclerview=view.findViewById(R.id.dataentryadminrecycler);
        dataentrydaminreyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        dataentryadminviewmodelList=new ArrayList<>();
        dataentryadminAdapter=new dataentryadminAdapter(getContext(),dataentryadminviewmodelList);
        dataentrydaminreyclerview.setAdapter(dataentryadminAdapter);

        setdata();


        return view;
    }
    private void setdata(){
        dataentryadminviewmodelList.add(new dataentryviewmodel("Suyash shukla","mail@gmail.com","12345","lucknow"));
        dataentryadminviewmodelList.add(new dataentryviewmodel("Aditya singh","mail@gmail.com","12345","Delhi"));
        dataentryadminviewmodelList.add(new dataentryviewmodel("Challa Rashmita","mail@gmail.com","12345","xyz"));
        dataentryadminviewmodelList.add(new dataentryviewmodel("Param","mail@gmail.com","12345","xyz"));
        dataentryadminviewmodelList.add(new dataentryviewmodel("DSatyam","mail@gmail.com","12345","xyz"));

        dataentryadminAdapter.notifyDataSetChanged();

    }
}