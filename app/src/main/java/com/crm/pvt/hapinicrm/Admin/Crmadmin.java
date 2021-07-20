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
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

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
    Dialog add;

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

    private void openform() {
        add=new Dialog(this);
        add.setContentView(R.layout.crmadmindetailsformfill);
        add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add.getWindow().setLayout(android.widget.Toolbar.LayoutParams.MATCH_PARENT, android.widget.Toolbar.LayoutParams.MATCH_PARENT);
        add.getWindow().getAttributes().gravity= Gravity.TOP;
    }
}