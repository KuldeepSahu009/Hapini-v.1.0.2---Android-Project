package com.crm.pvt.hapinicrm.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CRMProfile extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    private ImageView save,goback,showimg,setimg,back;
     Uri getfileuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crmprofile);
        goback=(ImageView)findViewById(R.id.crmprofilegoback);
        save=(ImageView)findViewById(R.id.savecrmprofile);
        setimg=(ImageView)findViewById(R.id.setcrmprofileimg);
        showimg=(ImageView)findViewById(R.id.crmadminprofileimg);
        back=(ImageView)findViewById(R.id.crmadminback1) ;
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CRMProfile.this,CrmadminLogin.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CRMProfile.this,Crmadmin.class);
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
        Dexter.withContext(CRMProfile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please select a Folder"),1);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(CRMProfile.this,"Need permission",Toast.LENGTH_LONG).show();
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
            Glide.with(CRMProfile.this).load(getfileuri).into(showimg);
            setimg.setVisibility(View.GONE);
            back.setVisibility(View.GONE);



        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}