package com.crm.pvt.hapinicrm.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.StringSearch;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAddtaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;


public class Addtask extends Fragment {

    private EditText passcode , taskReceived;
    private Button sendtask;
    private static String state;
    private FragmentAddtaskBinding binding;
    private FirebaseDatabase dataBaseInstance;
    String adminpasscode;
    String passcodes;
    private static final String TAG = "TAG";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentAddtaskBinding.inflate(inflater, container, false);
       dataBaseInstance = FirebaseDatabase.getInstance();
       passcode=binding.enterpasscodeforaddtask;
       sendtask=binding.sendtask;
       taskReceived=binding.addtasksettask;
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        adminpasscode=getshared.getString("passcode","no data");
        Log.e(TAG, "onCreateView: "+adminpasscode );
        binding.dataentryaddtaskbackimg.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        state = sendtask.getText().toString();

       sendtask.setOnClickListener(v -> {
           if(state.equals("VERIFY"))
           {
               verifyUser();
           }
           else
           {

           }
       });



       return binding.getRoot();
    }

    private void verifyUser() {
         passcodes=passcode.getText().toString();
        if(passcodes.length()<6){
            passcode.setError("Check passcode");
        }
        else {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Verifying User....");
            progressDialog.show();
            dataBaseInstance.getReference().child("usersv2").child("data").child(passcodes).get().addOnSuccessListener(task -> {
                if (task.exists()) {
                    passcode.setVisibility(View.GONE);
                    taskReceived.setVisibility(View.VISIBLE);
                    sendtask.setText("SEND TASK");
                    progressDialog.dismiss();
                    sendTaskToUser(task);
                } else {
                    Toast.makeText(getContext(), "No Such User Exist.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
   private FirebaseAuth auth;
    private void sendTaskToUser(DataSnapshot task) {
        auth = FirebaseAuth.getInstance();
        String tasks=taskReceived.getText().toString();
        if(TextUtils.isEmpty(tasks)){
            taskReceived.setError("Cannot sent empty");
        }
        else{
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Sending Task To User....");
            progressDialog.show();
           DatabaseReference reference=FirebaseDatabase.getInstance().getReference("dataentrytaskv2").child(adminpasscode);
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("userpasscode",passcodes);
            hashMap.put("task",tasks);
            reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Task sent", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigateUp();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "unable to send task", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}