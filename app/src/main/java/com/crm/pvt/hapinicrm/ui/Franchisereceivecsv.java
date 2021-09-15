package com.crm.pvt.hapinicrm.ui;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentFranchisereceivecsvBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Franchisereceivecsv extends Fragment {


    private FragmentFranchisereceivecsvBinding binding;
    private static final String TAG = "TAG";
    String passcodes;
    String name;
    String link;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentFranchisereceivecsvBinding.inflate(inflater, container, false);

       return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        passcodes=getshared.getString("passcode","no data");
        getdata(passcodes);
        binding.downloadcsvfilefranchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.equals("")) {
                    downloadFile(getContext(), name, ".xlsx", Environment.DIRECTORY_DOCUMENTS, link);
                    Toast.makeText(getContext(),"File Downloading...",Toast.LENGTH_LONG).show();
                    binding.downloadcsvfilefranchise.setEnabled(false);
                    binding.downloadcsvfilefranchise.setTextColor(Color.DKGRAY);
                }
            }
        });
        //Log.e(TAG, "onViewCreated: "+passcodes );
    }
    private long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);

    }
    private void getdata(String attendancepasscode){
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference("MASTERTOFRANCHISE");
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (key.equals(attendancepasscode)) {
                        link = dataSnapshot.child("CSV FILE").getValue().toString();
                        //currentFranchise = dataSnapshot.getValue(Franchise.class);

                        name = dataSnapshot.child("Csv File name").getValue().toString();
                        binding.csvfilenamefranchise.setText(" "+name);


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}