package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.crm.pvt.hapinicrm.MainActivity;
import com.crm.pvt.hapinicrm.R;

public class workselectadmin extends AppCompatActivity {
    CardView crmAdminbtn,dataentryAdminbtn,videoeditorAdminbtn,masterAdminbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workselectadmin);
        crmAdminbtn=findViewById(R.id.cvAdminCrm);
        dataentryAdminbtn=findViewById(R.id.cvAdminDataEntry);
        videoeditorAdminbtn=findViewById(R.id.cvAdminVideEditor);
        masterAdminbtn=findViewById(R.id.cvAdminMaster);
        crmAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(workselectadmin.this, Admingenerallogin.class);
                startActivity(intent);
            }
        });
        dataentryAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(workselectadmin.this, Admingenerallogin.class);
                startActivity(intent);
            }
        });
        videoeditorAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(workselectadmin.this, Admingenerallogin.class);
                startActivity(intent);
            }
        });
        masterAdminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(workselectadmin.this, Onlyformasterlogin.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(workselectadmin.this, MainActivity.class);
        startActivity(intent);
    }
}