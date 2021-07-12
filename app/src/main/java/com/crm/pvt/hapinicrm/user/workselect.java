package com.crm.pvt.hapinicrm.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.crm.pvt.hapinicrm.MainActivity;
import com.crm.pvt.hapinicrm.R;

public class workselect extends AppCompatActivity {
CardView crmbtn,dataentrybtn,videoeditorbtn,masterbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        crmbtn=findViewById(R.id.cvUserCrm);
        dataentrybtn=findViewById(R.id.cvUserDataEntry);
        videoeditorbtn=findViewById(R.id.cvUserVideEditor);
        masterbtn=findViewById(R.id.cvUserMaster);
        crmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(workselect.this, userlogin.class);
                startActivity(intent);
            }
        });
        dataentrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent=new Intent(workselect.this, userlogin.class);
                startActivity(intent);
            }
        });
        videoeditorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(workselect.this, userlogin.class);
                startActivity(intent);
            }
        });
        masterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent=new Intent(workselect.this, userlogin.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(workselect.this,MainActivity.class);
        startActivity(intent);
    }
}