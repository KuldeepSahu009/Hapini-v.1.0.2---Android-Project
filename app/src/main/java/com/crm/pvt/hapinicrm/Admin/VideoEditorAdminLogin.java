package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class VideoEditorAdminLogin extends AppCompatActivity {

    TextView tvVideoEditorAdminForgotpassword;
    Button btnVideoEditorAdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoeditoradminlogin);
        btnVideoEditorAdminLogin = findViewById(R.id.btnVideoEditorAdminLogin);
        btnVideoEditorAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoEditorAdminLogin.this, Videoeditoradmin.class);
                startActivity(intent);
            }
        });
        tvVideoEditorAdminForgotpassword=findViewById(R.id.tvVideoEditorAdminForgotPassword);
        tvVideoEditorAdminForgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoEditorAdminLogin.this, forgetpassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(VideoEditorAdminLogin.this,workselectadmin.class);
        startActivity(intent);
    }
}