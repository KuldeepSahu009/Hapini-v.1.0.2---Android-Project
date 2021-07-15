package com.crm.pvt.hapinicrm.resetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crm.pvt.hapinicrm.R;

public class confirmotp extends AppCompatActivity {
    Button confirmotpfromforgotpassword;
    EditText getotptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmotp);
        confirmotpfromforgotpassword=findViewById(R.id.confirmotpbtn);
        confirmotpfromforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String s=getotptext.getText().toString();
                //s contains otp

                //add the activity after verifying otp
                Intent intent=new Intent(confirmotp.this, newpassword.class);
                startActivity(intent);

            }
        });
    }
}