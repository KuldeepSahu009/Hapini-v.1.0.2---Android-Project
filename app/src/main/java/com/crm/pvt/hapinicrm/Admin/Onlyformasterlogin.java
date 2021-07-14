package com.crm.pvt.hapinicrm.Admin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;

import static android.widget.Toast.LENGTH_LONG;

public class Onlyformasterlogin extends AppCompatActivity {
    TextView formastersignup;
    Button mstrlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlyformasterlogin);
        formastersignup=findViewById(R.id.TOWARDSMASTERSIGNUP);
        mstrlogin=findViewById(R.id.btnMasterLogin);
        mstrlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Onlyformasterlogin.this,MasterAdmin.class);
                startActivity(intent);
            }
        });
        formastersignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
 Toast.makeText(getApplicationContext(), "Get passcode from company",LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Onlyformasterlogin.this,workselectadmin.class);
        startActivity(intent);
    }
}