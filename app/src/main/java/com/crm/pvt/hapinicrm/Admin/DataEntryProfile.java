package com.crm.pvt.hapinicrm.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class DataEntryProfile extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private ImageView save,goback,showimg,setimg,back;
    private Uri getfileuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_profile);
        goback=(ImageView)findViewById(R.id.dataentryrofilegoback);
        save=(ImageView)findViewById(R.id.savedataentryprofile);
        setimg=(ImageView)findViewById(R.id.setdataentryprofileimg);
        showimg=(ImageView)findViewById(R.id.dataentryadminprofileimg);
        back=(ImageView)findViewById(R.id.dataentryadminback1) ;
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataEntryProfile.this,DataEntryAdminLogin.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DataEntryProfile.this,Dataentryadmin.class);
                if (getfileuri!=null){
                    i.putExtra("filepath",getfileuri.toString());
                }

                startActivity(i);
            }
        });
        setimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpermission();
            }
        });

    }
    private void checkpermission(){
        Dexter.withContext(DataEntryProfile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please select a Folder"),1);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(DataEntryProfile.this,"Need permission",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode==1&&resultCode==RESULT_OK){
            getfileuri=data.getData();
            Glide.with(DataEntryProfile.this).load(getfileuri).into(showimg);
            setimg.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}