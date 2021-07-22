package com.crm.pvt.hapinicrm.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.fragment.AdduserCrmadminfragment;
import com.crm.pvt.hapinicrm.fragment.Homefragmentcrmadmin;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Crmadmin extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    String filepath;
    ImageView profilepic;
    View myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crmadmin);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        toolbar=(Toolbar)findViewById(R.id.crmadmintoolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);

        toggle=new ActionBarDrawerToggle(Crmadmin.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        filepath=getIntent().getStringExtra("filepath");



        myview=navigationView.getHeaderView(0);
        profilepic=(ImageView)myview.findViewById(R.id.profilepiccrmadmin);



        if (filepath!=null){
            Glide.with(Crmadmin.this).load(filepath).into(profilepic);
        }



        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutcrmadmin,new Homefragmentcrmadmin()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragmet=null;
                switch (item.getItemId()){
                    case R.id.home:
                        item.setChecked(true);
                        fragmet=new Homefragmentcrmadmin();
                        break;
                       case R.id.Addcrmuser:
                           fragmet=new AdduserCrmadminfragment();
                       break;
     

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutcrmadmin,fragmet ).addToBackStack(null).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}