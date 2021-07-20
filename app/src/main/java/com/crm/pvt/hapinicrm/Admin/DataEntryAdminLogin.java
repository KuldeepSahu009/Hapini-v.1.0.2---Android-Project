package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class DataEntryAdminLogin extends AppCompatActivity {
    TextView forgotpassword;
    Button btnDataEntryLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataentryadminlogin);
        btnDataEntryLogin = findViewById(R.id.btnVideoEditorAdminLogin);
        btnDataEntryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DataEntryAdminLogin.this, DataEntryProfile.class);
                startActivity(intent);
            }
        });
        forgotpassword=findViewById(R.id.tvVideoEditorAdminForgotPassword);
        forgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DataEntryAdminLogin.this, forgetpassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DataEntryAdminLogin.this,workselectadmin.class);
        startActivity(intent);
    }
}