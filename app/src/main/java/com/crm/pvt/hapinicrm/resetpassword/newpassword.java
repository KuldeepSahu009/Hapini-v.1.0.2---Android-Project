package com.crm.pvt.hapinicrm.resetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crm.pvt.hapinicrm.R;

public class newpassword extends AppCompatActivity {
Button submitnewpasswordafterforgot;
EditText getnewpasswordafterforgot,confirmnewpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        submitnewpasswordafterforgot=findViewById(R.id.btnSubmit);
        getnewpasswordafterforgot=findViewById(R.id.etNewPassword);
        confirmnewpassword=findViewById(R.id.etConfirmPassword);
        submitnewpasswordafterforgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String s=getnewpasswordafterforgot.getText().toString();
                //send this new string(password) to database overwriting old password
                //after success
                //open main activity
            }
        });
    }
}