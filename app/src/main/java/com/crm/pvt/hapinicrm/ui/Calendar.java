package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.crm.pvt.hapinicrm.R;


import java.util.ArrayList;


public class Calendar extends AppCompatDialogFragment {

    CalendarView calendarView;
    ArrayList<java.util.Calendar> calendars;

    public Calendar(ArrayList<java.util.Calendar> calendars) {
        this.calendars = calendars;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.calendar,null);
        builder.setView(view);
        calendarView=view.findViewById(R.id.calendarView);
//       android.icu.util.Calendar calendar= android.icu.util.Calendar.getInstance();
//        java.util.Calendar calendar1= java.util.Calendar.getInstance();
//        calendar1.set(2021,8,16);
//       java.util.Calendar calendar2= java.util.Calendar.getInstance();
//       calendar2.set(2021,8,17);
//       ArrayList<java.util.Calendar> calendars=new ArrayList<>();
//       calendars.add(calendar1);
//       calendars.add(calendar2);
        calendarView.setHighlightedDays(calendars);







      ;



        return builder.create();

    }

}
