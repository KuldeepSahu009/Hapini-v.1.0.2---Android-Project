package com.crm.pvt.hapinicrm.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.resetpassword.forgetpassword;


public class Onlyformasterlogin extends AppCompatActivity {
    Button mstrlogin;
    EditText mstrPasscode,mstrPassword;
    TextView mstrForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlyformasterlogin);
        mstrlogin=findViewById(R.id.btnMasterLogin);
        mstrPasscode = findViewById(R.id.etMasterPasscode);
        mstrPassword = findViewById(R.id.etMasterPassword);
        mstrForgotPassword = findViewById(R.id.tvForgotPassword);

        mstrlogin.setOnClickListener(v -> {

            String passcode = mstrPasscode.getText().toString();
            String password = mstrPassword.getText().toString();

            if(isCredentialsValid(passcode,password)) {
                Intent intent=new Intent(Onlyformasterlogin.this,MasterAdmin.class);
                startActivity(intent);
            }


        });

        mstrForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, forgetpassword.class);
            startActivity(intent);
        });

    }

    private boolean isCredentialsValid(String passcode, String password) {
        if(passcode.length() == 6 && password.length() != 0 ) {
            return true;
        } else {
            if(passcode.length() != 6) {
                mstrPasscode.setError("Passcode must be of 6 characters long");
            }
            if(mstrPassword.getText().toString().length() == 0) {
                mstrPassword.setError("Enter password");
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Onlyformasterlogin.this,workselectadmin.class);
        startActivity(intent);
    }
}