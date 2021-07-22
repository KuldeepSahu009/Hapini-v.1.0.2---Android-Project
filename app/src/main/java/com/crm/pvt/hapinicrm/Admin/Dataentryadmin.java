package com.crm.pvt.hapinicrm.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.fragment.AdduserDataentryadminfragment;
import com.crm.pvt.hapinicrm.fragment.Homefragmentdataentryadmin;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Dataentryadmin extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    String filepath;
    View myview;
    ImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataentryadmin_v2);
        navigationView=(NavigationView)findViewById(R.id.dataentryadminnavigationview);
        toolbar=(Toolbar)findViewById(R.id.dataentryadmintoolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout_dataentry);

        toggle=new ActionBarDrawerToggle(Dataentryadmin.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        myview=(navigationView).getHeaderView(0);
        profilepic=(ImageView)myview.findViewById(R.id.profilepicofdataadmin);



        filepath=getIntent().getStringExtra("filepath");
        if (filepath!=null){
            Glide.with(Dataentryadmin.this).load(filepath).into(profilepic);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutdataentryadmin,new Homefragmentdataentryadmin()).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.home_dataentryadmin:
                        item.setChecked(true);
                        fragment=new Homefragmentdataentryadmin();
                        break;
                    case R.id.Adddataentryuser:
                        fragment=new AdduserDataentryadminfragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutdataentryadmin,fragment ).addToBackStack(null).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
}