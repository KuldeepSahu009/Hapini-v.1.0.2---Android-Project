package com.crm.pvt.hapinicrm.ui;

import android.app.ProgressDialog;
import android.icu.text.StringSearch;
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
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAddtaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Addtask extends Fragment {

    private EditText passcode , taskReceived;
    private Button sendtask;
    private static String state;
    private FragmentAddtaskBinding binding;
    private FirebaseDatabase dataBaseInstance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentAddtaskBinding.inflate(inflater, container, false);
       dataBaseInstance = FirebaseDatabase.getInstance();
       passcode=binding.enterpasscodeforaddtask;
       sendtask=binding.sendtask;
       taskReceived=binding.addtasksettask;
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
        String passcodes=passcode.getText().toString();
        if(passcodes.length()<6){
            passcode.setError("Check passcode");
        }
        else {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Verifying User....");
            progressDialog.show();
            dataBaseInstance.getReference().child("usersv2").child("data").equalTo(passcodes).get().addOnSuccessListener(task -> {
                if (task != null) {
                    passcode.setVisibility(View.GONE);
                    taskReceived.setVisibility(View.VISIBLE);
                    sendtask.setText("SEND TASK");
                    progressDialog.dismiss();
                    sendTaskToUser(task);
                } else {
                    Toast.makeText(getContext(), "No Such User Exist.", Toast.LENGTH_LONG).show();
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
            task.getRef().child(passcode.getText().toString()).child("task").setValue(tasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task1) {
                    if(task1.isSuccessful())
                    {
                       task.getRef().child(passcode.getText().toString()).child("taskGivenBy").setValue(auth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful())
                               {
                                   progressDialog.dismiss();
                                   Toast.makeText(getContext(),"Task given to that user" ,Toast.LENGTH_SHORT).show();
                                   Navigation.findNavController(getView()).navigateUp();
                               }
                               else
                               {
                                   Toast.makeText(getContext(),"Something going Wrong" ,Toast.LENGTH_LONG).show();
                               }
                           }
                       });
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Something Went Wrong" ,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}