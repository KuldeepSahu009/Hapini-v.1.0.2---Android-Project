package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.crm.pvt.hapinicrm.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MasterDashboardFragment extends Fragment {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_master_dashboard, container, false);
        getActivity(). getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView=view.findViewById(R.id.navigationview);
        toolbar=view.findViewById(R.id.mastertoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        drawerLayout=view.findViewById(R.id.drawerlayout);

        toggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new Masterhomefragment()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homemaster:
                        item.setChecked(true);
                        Fragment homefragmet=new Masterhomefragment();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,homefragmet ).addToBackStack(null).commit();
                        break;
                    case R.id.profilemaster:
                        Fragment profilefragmnet=new Masterprofilefragment();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,profilefragmnet ).addToBackStack(null).commit();
                        break;

                    case R.id.logoutmaster:

                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

                //hello
            }
        });





        return view;


    }
}