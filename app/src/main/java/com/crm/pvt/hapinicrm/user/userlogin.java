package com.crm.pvt.hapinicrm.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class userlogin extends AppCompatActivity {
TextView usersignup,forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        usersignup=findViewById(R.id.tvSignUp);
        forgotpassword=findViewById(R.id.tvForgotPassword);
        usersignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(userlogin.this, com.crm.pvt.hapinicrm.user.usersignup.class);
                startActivity(intent);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(userlogin.this, forgetpassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(userlogin.this,workselect.class);
        startActivity(intent);
    }
}