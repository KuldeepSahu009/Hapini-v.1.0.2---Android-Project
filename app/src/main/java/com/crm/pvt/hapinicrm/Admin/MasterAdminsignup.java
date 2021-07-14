package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;

public class MasterAdminsignup extends AppCompatActivity {
    TextView Masteradminloginto;
    Button masteradminsignedup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_adminsignup);
        Masteradminloginto=findViewById(R.id.tvMasteradminLogin);
        masteradminsignedup=findViewById(R.id.btnMasteradminSignUp);
        masteradminsignedup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MasterAdminsignup.this,MasterAdmin.class);
                startActivity(intent);
            }
        });
        Masteradminloginto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MasterAdminsignup.this, Onlyformasterlogin.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MasterAdminsignup.this,Onlyformasterlogin.class);
        startActivity(intent);
    }
}