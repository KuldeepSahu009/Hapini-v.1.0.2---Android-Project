package com.crm.pvt.hapinicrm.ui;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentCrmAdminGiveTaskBinding;
import com.crm.pvt.hapinicrm.model.CsvFormat;
import com.crm.pvt.hapinicrm.model.TaskModel;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrmAdminGiveTaskFragment extends Fragment {

    public static FragmentCrmAdminGiveTaskBinding binding;
    public static int countForTask = 0;
    private DatabaseReference taskDatabaseReference;
    private DatabaseReference userReference;
public static View views;
public  static Context context;
    public static List<CsvFormat> detailOfCustomers;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrmAdminGiveTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        views = getView();
        context = getContext();
        detailOfCustomers = new ArrayList<>();
        loadDataFromCSVFile();
        taskDatabaseReference = FirebaseDatabase.getInstance().getReference("Task_Assignment_V2").child("CRM_User");
        userReference = FirebaseDatabase.getInstance().getReference("usersv2").child("crm");
        binding.btnAssignTask.setOnClickListener(v -> {
            binding.pbTask.setVisibility(View.VISIBLE);
            giveTaskToUser();
        });

    }
private String currentPasscode;
    public static ProgressDialog dialog;
    private void loadDataFromCSVFile() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading....");
        dialog.show();

        if(!Splashscreen.spAdminsData.getString("passcode","").equals(""))
            currentPasscode = Splashscreen.spAdminsData.getString("passcode","");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CSVFILEFROMFRANCHISEv2");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(currentPasscode!= null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.getKey().equals(currentPasscode)) {
                              new ReadFile().execute(ds.child("CSV FILE").getValue().toString());

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Navigation.findNavController(getView()).navigateUp();
                Toast.makeText(getContext(), "Please Try Again and Check Your Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void giveTaskToUser() {

        String userPasscode = Objects.requireNonNull(binding.etGivePasscodeTask.getText()).toString();
        String customerName = Objects.requireNonNull(binding.etNameOfCustomer.getText()).toString();
        String customerNumber = Objects.requireNonNull(binding.etMobileNumber.getText()).toString();
        String customerCity = Objects.requireNonNull(binding.etCity.getText()).toString();
        String task = Objects.requireNonNull(binding.etTaskDescription.getText()).toString();

        TaskModel taskModel = new TaskModel(
                userPasscode,
                customerName,
                customerNumber,
                customerCity,
                task,
                "Not Completed",
                ""
        );

        if(userPasscode.length() != 6) {

            binding.etGivePasscodeTask.setError("Enter valid passcode");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else if (customerNumber.length() != 10 ) {

            binding.etMobileNumber.setError("Please enter a valid number");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else if(task.length() == 0) {

            binding.etTaskDescription.setError("Enter task");
            binding.pbTask.setVisibility(View.INVISIBLE);

        } else {

            userReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String key;
                    TrackUserModel user = null;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        key = dataSnapshot.getKey();
                        assert key != null;
                        if (key.equals(userPasscode)) {
                            user = dataSnapshot.getValue(TrackUserModel.class);
                            break;
                        }

                    }
                    if(user != null) {
                        TrackUserModel finalUser = user;
                        taskDatabaseReference.child(userPasscode).child(customerNumber).setValue(taskModel).addOnCompleteListener(setTask -> {
                            if(setTask.isSuccessful()) {
                                dialog.show();
                                nextTask();
                                Snackbar.make(binding.getRoot(),"Task Assigned to " + finalUser.getName() + " ",Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(binding.getRoot(),"Something went wrong !!! Try Again ",Snackbar.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        binding.etGivePasscodeTask.setError("Enter valid user passcode");
                    }
                    binding.pbTask.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }
    public static void nextTask()
    {
        if(detailOfCustomers.size() > 0)
        {
            CsvFormat task = detailOfCustomers.get(countForTask);
         binding.etNameOfCustomer.setText(task.getName());
         binding.etCity.setText(task.getCity());
         binding.etTaskDescription.setText("Type of Customer: "+task.getType());
         binding.etMobileNumber.setText(task.getContact()+"");
         detailOfCustomers.remove(countForTask);
        }
        else
        {
            Navigation.findNavController(views).navigateUp();
            Toast.makeText(context, "List Finished", Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }
}

class ReadFile extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            if(conn.getResponseCode() == 200)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    String[] comps = inputLine.split(",");
                    CrmAdminGiveTaskFragment.detailOfCustomers.add(new CsvFormat(comps[0],comps[1],comps[3],comps[2]));
                }
            }

        }catch (Exception e){
            Log.e("Error", e.toString());
        }
        finally
        {
            if(conn!=null)
                conn.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        CrmAdminGiveTaskFragment.nextTask();
    }

    @Override
    protected void onPreExecute() {}
}