package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crm.pvt.hapinicrm.R;


public class Addtask extends Fragment {
    Button sendtask;
    EditText passcode,task;
    ImageView back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_addtask, container, false);
       passcode=view.findViewById(R.id.enterpasscodeforaddtask);
       sendtask=view.findViewById(R.id.sendtask);
       task=view.findViewById(R.id.addtasksettask);
        back=view.findViewById(R.id.dataentryaddtaskbackimg);


       sendtask.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String passcodes=passcode.getText().toString();
               String tasks=task.getText().toString();
               if(passcodes.length()<6){
                   passcode.setError("Check passcode");
               }else if(TextUtils.isEmpty(tasks)){
                   task.setError("Cannot sent empty");
               }
               else{
                settask(passcodes,tasks);
               }
           }
       });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Navigation.findNavController(v).navigateUp();
           }
       });


       return view;
    }
    private void settask(String passcodes,String tasks){
       passcode.setText("");
       task.setText("");
    }


}