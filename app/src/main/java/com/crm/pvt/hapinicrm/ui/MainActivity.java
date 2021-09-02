package com.crm.pvt.hapinicrm.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.util.DateTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PublicKey;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private static boolean isHandlerRunning = false;
    private static final long DISCONNECT_TIMEOUT = 7000000;
    private static Handler inactivityHandler;
    private static Runnable runnableCallback;
    private String currentTime;
    private Date date1;
    String Datee;
    FirebaseDatabase database;
    private boolean isFirstTimeHandlerStarted = true;
    private static String currentAdminPasscode = "";
    private static String types = "";
    private DatabaseReference activeStatusReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeStatusReference = FirebaseDatabase.getInstance().getReference("activeV2");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss");
        Date date1 = calendar.getTime();


        inactivityHandler = new Handler();
        runnableCallback = new Runnable() {
            @Override
            public void run() {
                //user has 1 minute inactivity
                updateDataBase();
                finish();
                Splashscreen.spUsersData.edit().clear().commit();
            }
        };
        startHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startHandler();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "franchise":
                        type = "franchises";
                        activeStatusReference.child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                    default:
                        break;
                }

            } else if (Splashscreen.spUsersData != null) {
                if (!Splashscreen.spUsersData.getString("passcode", "null").equals("null")) {
                    String type;
                    switch (Splashscreen.spUsersData.getString("type", "null")) {
                        case "crmuser":
                            type = "crm";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "datauser":
                            type = "data";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "videouser":
                            type = "video";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        default:
                            break;
                    }
                }
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // User has closed the app completely
        stopHandler();
        updateDataBase();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "franchise":
                        type = "franchises";
                        activeStatusReference.child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                    default:
                        break;
                }

            } else if (Splashscreen.spUsersData != null) {
                if (!Splashscreen.spUsersData.getString("passcode", "null").equals("null")) {
                    String type;
                    switch (Splashscreen.spUsersData.getString("type", "null")) {
                        case "crmuser":
                            type = "crm";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        case "datauser":
                            type = "data";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        case "videouser":
                            type = "video";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        default:
                            break;
                    }
                }
            } else {
                //both are null
                if (!currentAdminPasscode.equals("") && !types.equals("")) {
                    if (types.contains("users")) {
                        String type;
                        switch (types) {
                            case "crmuser":
                                type = "crm";
                                activeStatusReference.child("users").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                            case "datauser":
                                type = "data";
                                activeStatusReference.child("users").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                            case "videouser":
                                type = "video";
                                activeStatusReference.child("users").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                        }
                    } else if (types.contains("admins")) {
                        String type = "";
                        switch (types) {
                            case "crm":
                                type = "CRM";
                                activeStatusReference.child("admins").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                            case "data":
                                type = "DATA_ENTRY";
                                activeStatusReference.child("admins").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                            case "video":
                                type = "VIDEO_EDITOR";
                                activeStatusReference.child("admins").child(type)
                                        .child(currentAdminPasscode)
                                        .removeValue();
                                break;
                        }
                    } else if (types.contains("franchise")) {
                        activeStatusReference.child("franchises")
                                .child(currentAdminPasscode)
                                .removeValue();
                    }
                }
            }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                        break;
                    case "franchise":
                        type = "franchises";
                        activeStatusReference.child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .removeValue();
                    default:
                        break;
                }

            } else if (Splashscreen.spUsersData != null) {
                if (!Splashscreen.spUsersData.getString("passcode", "null").equals("null")) {
                    String type;
                    switch (Splashscreen.spUsersData.getString("type", "null")) {
                        case "crmuser":
                            type = "crm";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        case "datauser":
                            type = "data";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        case "videouser":
                            type = "video";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .removeValue();
                            break;
                        default:
                            break;
                    }
                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "franchise":
                        type = "franchises";
                        activeStatusReference.child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                    default:
                        break;
                }

            } else if (Splashscreen.spUsersData != null) {
                if (!Splashscreen.spUsersData.getString("passcode", "null").equals("null")) {
                    String type;
                    switch (Splashscreen.spUsersData.getString("type", "null")) {
                        case "crmuser":
                            type = "crm";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "datauser":
                            type = "data";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "videouser":
                            type = "video";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        default:
                            break;
                    }
                }
            }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!Splashscreen.spAdminsData.getString("passcode", "").equals("")) {
            currentAdminPasscode = Splashscreen.spAdminsData.getString("passcode", "");
            types = Splashscreen.spAdminsData.getString("type", "");
            Log.i("PARAMJEETSAINI", "admin");
            checkActive();
        } else if (!Splashscreen.spUsersData.getString("passcode", "").equals("")) {
            currentAdminPasscode = Splashscreen.spUsersData.getString("passcode", "");
            types = Splashscreen.spUsersData.getString("type", "");
            Log.i("PARAMJEETSAINI", "user");
            checkActive();
        } else {
            Log.i("PARAMJEETSAINI", "inactive");
            checkForInactive();
        }

        if (isHandlerRunning) {
            stopHandler();
        }
        startHandler();

    }

    private void checkActive() {
        if (Splashscreen.spAdminsData != null)
            if (!Splashscreen.spAdminsData.getString("passcode", "null").equals("null")) {
                String type;
                switch (Splashscreen.spAdminsData.getString("type", "null")) {
                    case "crm":
                        type = "CRM";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "data":
                        type = "DATA_ENTRY";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "video":
                        type = "VIDEO_EDITOR";
                        activeStatusReference.child("admins").child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                        break;
                    case "franchise":
                        type = "franchises";
                        activeStatusReference.child(type)
                                .child(Splashscreen.spAdminsData.getString("passcode", "null"))
                                .setValue("active");
                    default:
                        break;
                }

            } else if (Splashscreen.spUsersData != null) {
                if (!Splashscreen.spUsersData.getString("passcode", "null").equals("null")) {
                    String type;
                    switch (Splashscreen.spUsersData.getString("type", "null")) {
                        case "crmuser":
                            type = "crm";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "datauser":
                            type = "data";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        case "videouser":
                            type = "video";
                            activeStatusReference.child("users").child(type)
                                    .child(Splashscreen.spUsersData.getString("passcode", "null"))
                                    .setValue("active");
                            break;
                        default:
                            break;
                    }
                }
            }
    }

    private void checkForInactive() {
        if (!currentAdminPasscode.equals("") && (types.equals("crm") || types.equals("data") || types.equals("video"))) {
            String type = "";
            switch (types) {
                case "crm":
                    type = "CRM";
                    activeStatusReference.child("admins").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
                case "data":
                    type = "DATA_ENTRY";
                    activeStatusReference.child("admins").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
                case "video":
                    type = "VIDEO_EDITOR";
                    activeStatusReference.child("admins").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
            }
        } else if (!currentAdminPasscode.equals("") && types.contains("user")) {
            String type;
            switch (types) {
                case "crmuser":
                    type = "crm";
                    activeStatusReference.child("users").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
                case "datauser":
                    type = "data";
                    activeStatusReference.child("users").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
                case "videouser":
                    type = "video";
                    activeStatusReference.child("users").child(type)
                            .child(currentAdminPasscode)
                            .removeValue();
                    currentAdminPasscode = "";
                    types = "";
                    break;
            }
        } else if (!currentAdminPasscode.equals("") && types.contains("franchise")) {
            activeStatusReference.child("franchises")
                    .child(currentAdminPasscode)
                    .removeValue();
            currentAdminPasscode = "";
            types = "";
        }
    }

    public void startHandler() {
        if (areWorkingHours()) {
            inactivityHandler.postDelayed(runnableCallback, DISCONNECT_TIMEOUT);
            isHandlerRunning = true;
        }
    }

    public void stopHandler() {
        inactivityHandler.removeCallbacks(runnableCallback);
        isHandlerRunning = false;
    }

    private boolean areWorkingHours() {
        if (Splashscreen.usertype != null) {
            // user is logged in
            if (Splashscreen.usertype.equals("crmuser")) {
                //code for inactive CRM User  timings are 9am to 8pm
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss");
                if (isFirstTimeHandlerStarted) {
                    date1 = calendar.getTime();
                    String currentTime = simpleDateFormat.format(date1);
                    this.currentTime = currentTime;
                    isFirstTimeHandlerStarted = false;
                }
                if (currentTime.compareTo("09:00:00") >= 0 && currentTime.compareTo("22:00:00") < 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (Splashscreen.usertype.equals("datauser")) {
                //code for inactive Data Entry User flexible timings
                return false;
            } else if (Splashscreen.usertype.equals("videouser")) {
                //code for inactive Video Editor User flexible timings
                return false;
            }
        }
        return false;
    }

    private void updateDataBase() {
        if (Splashscreen.usertype.equals("crmuser")) {
            String activeTime = "";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

            Date date1 = calendar.getTime();
            String currentTime = simpleDateFormat.format(date1);
            Datee = simpleDateFormat1.format(date1);

            HashMap<String, String> updation = new HashMap<>();
            updation.put("Entry_Time", this.currentTime);
            updation.put("Exit_Time", currentTime);
            long diffInMilli = date1.getTime() - this.date1.getTime();
            long diffInSec = diffInMilli / 1000;
            if (diffInSec <= 60)
                activeTime = diffInSec + " seconds.";
            else if (diffInSec > 60) {
                int seconds = (int) diffInSec % 60;
                long minute = diffInSec / 60;
                if (minute <= 60)
                    activeTime = minute + " minutes and " + seconds + " seconds.";
                else {
                    int minute1 = (int) minute % 60;
                    int hours = (int) minute1 / 60;
                    activeTime = hours + " hours " + minute1 + " minutes and " + seconds + " seconds.";
                }
            }
            updation.put("Active_Time", activeTime);

            if (Splashscreen.passcode != null)


                database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("crm_user_active_time");
            myRef.child(Splashscreen.passcode).setValue(updation);
        }
    }
}

