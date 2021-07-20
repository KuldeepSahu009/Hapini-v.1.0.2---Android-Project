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
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.fragment.AdduserVideoeditoradminfragment;
import com.crm.pvt.hapinicrm.fragment.HomeVideoEditorFragment;
import com.crm.pvt.hapinicrm.fragment.Homefragmentdataentryadmin;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;


public class Videoeditoradmin extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoeditoradmin);

        navigationView=(NavigationView)findViewById(R.id.videoeditoradminnavigationview);
        toolbar=(Toolbar)findViewById(R.id.videoeditoradmintoolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout_videoeditor);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutvideoeditoradmin,new Homefragmentdataentryadmin()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.home_videoeditoradmin:
                        item.setChecked(true);
                        fragment=new HomeVideoEditorFragment();
                        break;
                    case R.id.Addvideoeditoruser:
                        fragment=new AdduserVideoeditoradminfragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutvideoeditoradmin,fragment ).addToBackStack(null).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
}