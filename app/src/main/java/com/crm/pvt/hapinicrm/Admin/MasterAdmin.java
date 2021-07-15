package com.crm.pvt.hapinicrm.Admin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.crm.pvt.hapinicrm.Adapters.MasterviewAdapter;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.MasterviewModel;

import java.util.ArrayList;
import java.util.List;

public class MasterAdmin extends AppCompatActivity {
    Spinner Adminfilling;
    Dialog add,add2,add3;
    private MasterviewAdapter masterviewAdapter;
   ArrayList<MasterviewModel> masterviewModelArrayList;
    RecyclerView recyclerViewmaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_admin);
        Adminfilling=findViewById(R.id.Adminselectdropdown);
        recyclerViewmaster=findViewById(R.id.masterrecyclerview);

LinearLayoutManager layoutManager=new LinearLayoutManager(this);
recyclerViewmaster.setLayoutManager(layoutManager);
recyclerViewmaster.setHasFixedSize(true);
masterviewModelArrayList=new ArrayList<>();
masterviewModelArrayList.add(new MasterviewModel("Suyash shukla","mail@gmail.com","12345","lucknow"));
        masterviewModelArrayList.add(new MasterviewModel("Aditya singh","mail@gmail.com","12345","Delhi"));
        masterviewModelArrayList.add(new MasterviewModel("Challa Rashmita","mail@gmail.com","12345","xyz"));
        masterviewModelArrayList.add(new MasterviewModel("Param","mail@gmail.com","12345","xyz"));
        masterviewModelArrayList.add(new MasterviewModel("Satyam","mail@gmail.com","12345","xyz"));
        recyclerViewmaster.setAdapter(new MasterviewAdapter(masterviewModelArrayList));
    {
}
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdownfilesname, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Adminfilling.setAdapter(adapter);
        Adminfilling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Crm Admin"))
                {
                    add.show();
                    Toast.makeText(getApplicationContext(),"CRM SELECTED",Toast.LENGTH_LONG).show();
                }
                if(selectedItem.equals("Data Entry Admin"))
                {
                    add2.show();
                    Toast.makeText(getApplicationContext(),"Data Entry Admin",Toast.LENGTH_LONG).show();
                }

                if(selectedItem.equals("Video Editor Admin"))
                {
                    add3.show();
                    Toast.makeText(getApplicationContext(),"Video Editor Admin",Toast.LENGTH_LONG).show();
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        crmeditor();
        dataentryeditor();
        videoeditor();
    }

    private void crmeditor() {
        add=new Dialog(this);
        add.setContentView(R.layout.crmadmindetailsformfill);
        add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add.getWindow().getAttributes().gravity= Gravity.TOP;
    }

    private void dataentryeditor() {
        add2=new Dialog(this);
        add2.setContentView(R.layout.dataentrydetailsformfill);
        add2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add2.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add2.getWindow().getAttributes().gravity= Gravity.TOP;
    }

    private void videoeditor() {
        add3=new Dialog(this);
        add3.setContentView(R.layout.videoeditoradminformfill);
        add3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add3.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add3.getWindow().getAttributes().gravity= Gravity.TOP;
    }
}