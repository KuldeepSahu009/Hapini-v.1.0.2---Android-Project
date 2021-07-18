package com.crm.pvt.hapinicrm.Admin;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;

public class CrmadminLogin extends AppCompatActivity {
    private EditText passcodes,passwords;
    private TextView forgotpassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crmadminlogin);
        passcodes=(EditText)findViewById(R.id.crmadminpasscode);
        passwords=(EditText)findViewById(R.id.crmadminpassword);
        forgotpassword=(TextView)findViewById(R.id.crmadminforgotpassword);
        login=(Button)findViewById(R.id.crmadminlogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcode = passcodes.getText().toString();
                String password = passwords.getText().toString();
                if(autheticatedata(passcode, password)){
                    Intent intent=new Intent(CrmadminLogin.this,Crmadmin.class);
                    startActivity(intent);
                }
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrmadminLogin.this, forgetpassword.class));
            }
        });
    }
    private boolean autheticatedata(String passcode, String password){
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
        Intent intent=new Intent(CrmadminLogin.this,workselectadmin.class);
        startActivity(intent);
    }
}