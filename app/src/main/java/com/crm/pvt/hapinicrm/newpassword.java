package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newpassword extends AppCompatActivity {
Button submitnewpasswordafterforgot;
EditText getnewpasswordafterforgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        submitnewpasswordafterforgot=findViewById(R.id.savenewpassword);
        getnewpasswordafterforgot=findViewById(R.id.getnewpassword);
        submitnewpasswordafterforgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String s=getnewpasswordafterforgot.getText().toString();
                //send this new string(passord) to database overwriting old password
                //after success
                //open main activity
            }
        });
    }
}