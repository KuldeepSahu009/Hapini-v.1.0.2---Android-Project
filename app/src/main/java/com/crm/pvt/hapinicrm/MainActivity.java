package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button lgnpg,sgnpg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
lgnpg=findViewById(R.id.btnLogin);
sgnpg=findViewById(R.id.btnSignUp);
lgnpg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent intent=new Intent(MainActivity.this, forgetpassword.class);
//forgetpassword class is referred for example, edit it..
startActivity(intent);
//add the activity after login
        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
    }
});
sgnpg.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
        //add the activity to be opened after signup.
        Toast.makeText(getApplicationContext(),"SignUp Successful",Toast.LENGTH_LONG).show();
    }
});
    }
}