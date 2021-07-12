package com.crm.pvt.hapinicrm.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;

public class usersignup extends AppCompatActivity {
TextView userloginto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_signup);
        userloginto=findViewById(R.id.tvLogin);
        userloginto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(usersignup.this,userlogin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(usersignup.this,workselect.class);
        startActivity(intent);
    }
}