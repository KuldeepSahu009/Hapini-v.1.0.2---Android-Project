package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class DataEntryAdminLogin extends AppCompatActivity {
    TextView forgotpassword;
    Button btnDataEntryLogin;
    EditText passcodes,passwords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataentryadminlogin);
        passcodes=(EditText)findViewById(R.id.etVideoEditorAdminPasscode);
        passwords=(EditText)findViewById(R.id.etVideoEditorAdminPassword);
        btnDataEntryLogin = findViewById(R.id.btnVideoEditorAdminLogin);
        btnDataEntryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcode = passcodes.getText().toString();
                String password = passwords.getText().toString();
                if(autheticatedata(passcode, password)){
                    Intent intent=new Intent(DataEntryAdminLogin.this,DataEntryProfile.class);
                    startActivity(intent);
                }
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

    private boolean autheticatedata(String passcode, String password) {
        if(passcode.length() == 6 && password.length() != 0 ) {
            return true;
        } else {
            if(passcode.length() != 6) {
                passcodes.setError("Passcode must be of 6 characters long");
            }
            if(passwords.getText().toString().length() == 0) {
                passwords.setError("Enter password");
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DataEntryAdminLogin.this,workselectadmin.class);
        startActivity(intent);
    }
}