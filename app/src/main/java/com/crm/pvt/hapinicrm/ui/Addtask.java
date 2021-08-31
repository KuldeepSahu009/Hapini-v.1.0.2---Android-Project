package com.crm.pvt.hapinicrm.ui;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentAddtaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
public class Addtask extends Fragment {

    private EditText passcode , taskReceived;
    private Button sendtask;
    private static String state;
    private FragmentAddtaskBinding binding;
    private FirebaseDatabase dataBaseInstance;
    private String userType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        binding = FragmentAddtaskBinding.inflate(inflater, container, false);
       dataBaseInstance = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
       checkUserType();
       passcode=binding.enterpasscodeforaddtask;
       sendtask=binding.sendtask;
       taskReceived=binding.addtasksettask;
        binding.dataentryaddtaskbackimg.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        state = sendtask.getText().toString();

       sendtask.setOnClickListener(v -> {
           if(state.equals("VERIFY"))
           {
               if(userType != null)
               verifyUser();
               else
                   Toast.makeText(getContext(),"Please Check Your Connection",Toast.LENGTH_LONG).show();
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

                dataBaseInstance.getReference().child("usersv2").child(userType).child(passcodes).get().addOnSuccessListener(task -> {
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
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                });

        }
    }
   private FirebaseAuth auth;
    private void sendTaskToUser(DataSnapshot task) {

            String tasks = taskReceived.getText().toString();
            if (TextUtils.isEmpty(tasks)) {
                taskReceived.setError("Cannot sent empty");
            } else {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Sending Task To User....");
                progressDialog.show();

                if(userType.equals("data")) {
                    task.getRef().child("task").setValue(tasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if (task1.isSuccessful()) {
                                task.getRef().child("taskGivenBy").setValue(auth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Task given to that user", Toast.LENGTH_SHORT).show();
                                            Navigation.findNavController(getView()).navigateUp();
                                        } else {
                                            Toast.makeText(getContext(), "Something going Wrong", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
                else if( userType.equals("video"))
                {
                        dataBaseInstance.getReference().child("VideoEditorTaskV2").child(passcode.getText().toString()).child("task").setValue(tasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task1) {
                                if(task1.isSuccessful())
                                {
                                    Toast.makeText(getContext(),"Task Sent." ,Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Navigation.findNavController(getView()).navigateUp();
                                }
                                else
                                {
                                    Toast.makeText(getContext(),"Something Went Wrong" ,Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }


            }
        }
     private void checkUserType() {
         if(auth.getCurrentUser().getEmail().contains("veadmin"))
         {
            userType = "video";
         }
         else if(auth.getCurrentUser().getEmail().contains("deadmin"))
         {
            userType = "data";
         }
         else if(auth.getCurrentUser().getEmail().contains("crmadmin"))
         {
            userType = "crm";
         }
     }
    @Override
    public void onStart() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    default:
                        break;
                }

            }

        super.onStart();

    }
    @Override
    public void onPause() {
        if(Splashscreen.spAdminsData != null)
            if(!Splashscreen.spAdminsData.getString("passcode","null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type","null"))
                {
                    case "crm":
                        type = "CRM";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        CrmAdminFragment.activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    default:
                        break;
                }

            }
        super.onPause();

    }
}