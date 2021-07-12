package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;

public class Onlyformasterlogin extends AppCompatActivity {
TextView formastersignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlyformasterlogin);
        formastersignup=findViewById(R.id.TOWARDSMASTERSIGNUP);
        formastersignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Onlyformasterlogin.this,MasterAdminsignup.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Onlyformasterlogin.this,workselectadmin.class);
        startActivity(intent);
    }
}