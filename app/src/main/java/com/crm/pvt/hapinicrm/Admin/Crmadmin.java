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

public class Crmadmin extends AppCompatActivity {
    private EditText passcodes,passwords;
    private TextView forgotpassword,errormessage;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crmadmin);
        passcodes=(EditText)findViewById(R.id.crmadminpasscode);
        passwords=(EditText)findViewById(R.id.crmadminpassword);
        forgotpassword=(TextView)findViewById(R.id.crmadminforgotpassword);
        login=(Button)findViewById(R.id.crmadminlogin);
        errormessage=(TextView) findViewById(R.id.crmerrormessae);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autheticatedata();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Crmadmin.this, forgetpassword.class));
            }
        });
    }
    private void autheticatedata(){ 
        boolean cancel=false;
        View view=null;
        String passcode=passcodes.getText().toString();
        String password=passwords.getText().toString();
        if (TextUtils.isEmpty(passcode)){
            view=passcodes;
            cancel=true;
            passcodes.setBackgroundResource(R.drawable.bgedittexterror);
            passwords.setBackgroundResource(R.drawable.bg_edit_text);
            errormessage.setText("Please enter the passcode");
            errormessage.setVisibility(View.VISIBLE);

        }else if(TextUtils.isEmpty(password)){
            cancel=true;
            view=passwords;
            passwords.setBackgroundResource(R.drawable.bgedittexterror);
            passcodes.setBackgroundResource(R.drawable.bg_edit_text);
            errormessage.setText("Please enter the password");
            errormessage.setVisibility(View.VISIBLE);

        }else if(!TextUtils.isEmpty(passcode)&&!TextUtils.isEmpty(password)){
            checkdata(passcode,password);
            errormessage.setVisibility(View.GONE);
        }

        if (cancel==true){
            view.requestFocus();
        }
    }
    private void checkdata(String passcode,String password){
        Toast.makeText(Crmadmin.this,"Sign in completed",Toast.LENGTH_LONG).show();
    }
}