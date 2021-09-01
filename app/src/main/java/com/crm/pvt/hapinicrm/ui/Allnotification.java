package com.crm.pvt.hapinicrm.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.adapters.notificationadapter;
import com.crm.pvt.hapinicrm.databinding.FragmentAllnotificationBinding;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.model.notificationmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Allnotification extends Fragment {

private FragmentAllnotificationBinding binding;
private RecyclerView recyclerView;
ArrayList<notificationmodel> notificationList=new ArrayList<>();
notificationadapter NotificationAdapter;
DatabaseReference mbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding= FragmentAllnotificationBinding.inflate(inflater, container, false);

       return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.allnotificationsview.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationList=new ArrayList<>();
        NotificationAdapter =new notificationadapter(getContext(),notificationList);
        binding.allnotificationsview.setAdapter(NotificationAdapter);

    }



  void readdata() {


      Query query=mbase.child("crm_user_active_time");
      query.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot datasnapshot) {
              notificationList.clear();
              for(DataSnapshot snapshot: datasnapshot.getChildren()){
                  notificationmodel NotificationList=new notificationmodel();
                  String Key= datasnapshot.getKey();
                  NotificationList.setPasscode(Key);
                  NotificationList.setLastlogin(snapshot.child("Entry_Time").getValue().toString());
                  NotificationList.setLastlogout(snapshot.child("Exit_Time").getValue().toString());
                  NotificationList.setTotalworktime(snapshot.child("Active_Time").getValue().toString());
                  notificationList.add(NotificationList);
              }
              notificationadapter Notificationadapter=new notificationadapter(getContext(),notificationList);

              Notificationadapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

    }
}