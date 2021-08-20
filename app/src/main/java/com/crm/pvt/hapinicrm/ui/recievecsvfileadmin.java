package com.crm.pvt.hapinicrm.ui;

import static android.text.TextUtils.split;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.databinding.FragmentRecievecsvfileadminBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class recievecsvfileadmin extends Fragment {


    private FragmentRecievecsvfileadminBinding binding;
    String link="";
    String name="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentRecievecsvfileadminBinding.inflate(inflater, container, false);
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        String attendancepasscode=getshared.getString("passcode","no data");

        getdata(attendancepasscode);
        binding.downloadcsvfile.setOnClickListener(v -> {
            if(!name.equals("")) {
                downloadFile(getContext(), name, ".xlsx", Environment.DIRECTORY_DOCUMENTS, link);
                Toast.makeText(getContext(),"File Downloading...",Toast.LENGTH_LONG).show();
                binding.downloadcsvfile.setEnabled(false);
                binding.downloadcsvfile.setTextColor(Color.DKGRAY);
            }});
        return binding.getRoot();
    }

    private void getdata(String attendancepasscode) {
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference("CSVFILEFROMFRANCHISEv2");
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    if (key.equals(attendancepasscode)) {
                        link = dataSnapshot.child("CSV FILE").getValue().toString();
                       //currentFranchise = dataSnapshot.getValue(Franchise.class);

                        name = dataSnapshot.child("Csv File name").getValue().toString();
                        binding.csvfilename.setText(" "+name);


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

}