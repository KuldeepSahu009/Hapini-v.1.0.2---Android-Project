package com.crm.pvt.hapinicrm.resetpassword;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crm.pvt.hapinicrm.R;

public class forgetpassword extends AppCompatActivity {
    Button  otppusher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        otppusher=findViewById(R.id.btnSendOtp);
        otppusher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //check from database if email registered
                //if not message no otp send and a message appears no registered email
                //if yes otp send and next activity opens
                Intent intent=new Intent(forgetpassword.this, confirmotp.class);
                startActivity(intent);
            }
        });
    }
}