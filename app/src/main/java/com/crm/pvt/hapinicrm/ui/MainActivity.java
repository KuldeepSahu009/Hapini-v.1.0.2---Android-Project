package com.crm.pvt.hapinicrm.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.google.api.client.util.DateTime;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private static boolean isHandlerRunning = false;
    private static final long DISCONNECT_TIMEOUT = 60000;
    private static Handler inactivityHandler;
    private static Runnable runnableCallback;
    private String currentTime;
    private  Date date1;
    private boolean isFirstTimeHandlerStarted = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inactivityHandler = new Handler();
        runnableCallback = new Runnable() {
            @Override
            public void run() {
                 //user has 1 minute inactivity
                updateDataBase();
            }
        };
        startHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startHandler();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // User has closed the app completely
        stopHandler();
        updateDataBase();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
          if(isHandlerRunning)
            {
                stopHandler();
            }
        startHandler();
    }

    public void startHandler()
    {
        if(areWorkingHours()) {
            inactivityHandler.postDelayed(runnableCallback, DISCONNECT_TIMEOUT);
            isHandlerRunning = true;
        }
    }

    public void stopHandler()
    {
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
                if(isFirstTimeHandlerStarted)
                {
                    date1 = calendar.getTime();
                    String currentTime = simpleDateFormat.format(date1);
                    this.currentTime = currentTime;
                    isFirstTimeHandlerStarted = false;
                }
                if (currentTime.compareTo("09:00:00") >= 0 && currentTime.compareTo("20:00:00") < 0) {
                        return true;
                }
                else
                {
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

    private void updateDataBase()
    {
        if(Splashscreen.usertype != null)
        {
            if(Splashscreen.usertype.equals("crmuser"))
            {
                String activeTime = "";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss");
                Date date1 =calendar.getTime();
                String currentTime = simpleDateFormat.format(date1);

                HashMap<String , String> updation = new HashMap<>();
                updation.put("Entry_Time",this.currentTime);
                updation.put("Exit_Time",currentTime);
                long diffInMilli = date1.getTime() - this.date1.getTime();
                long diffInSec = diffInMilli / 1000;
                if(diffInSec <= 60)
                    activeTime = diffInSec + " seconds.";
                else if (diffInSec > 60)
                {
                    int seconds = (int) diffInSec % 60;
                    long minute = diffInSec / 60;
                    if(minute <= 60)
                    activeTime = minute+" minutes and " +seconds+ " seconds.";
                    else
                    {
                        int minute1 = (int) minute % 60;
                        int hours = (int) minute1 / 60;
                        activeTime = hours+" hours "+minute1+" minutes and " +seconds+ " seconds.";
                    }
                }
                updation.put("Active_Time",activeTime);

                if(Splashscreen.passcode != null)
                FirebaseDatabase.getInstance().getReference("crm_user_active_time").child(Splashscreen.passcode)
                .child(currentTime)
                .setValue(updation);
            }
        }
    }
}