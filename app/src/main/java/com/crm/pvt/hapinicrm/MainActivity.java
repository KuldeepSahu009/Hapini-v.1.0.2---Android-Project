package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crm.pvt.hapinicrm.user.userlogin;
import com.crm.pvt.hapinicrm.user.usersignup;
import com.crm.pvt.hapinicrm.user.workselect;

public class MainActivity extends AppCompatActivity {
Button toadmin,touser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        toadmin=findViewById(R.id.btnAdmin);
        touser=findViewById(R.id.btnUser);
        toadmin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//for admin
            }
        });
        touser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
Intent intent=new Intent(MainActivity.this, workselect.class);
startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}