package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class Admingenerallogin extends AppCompatActivity {
    TextView Adminsignupet,forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admingenerallogin);
        forgotpassword=findViewById(R.id.tvForgotPassword);
        forgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admingenerallogin.this, forgetpassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Admingenerallogin.this,workselectadmin.class);
        startActivity(intent);
    }
}