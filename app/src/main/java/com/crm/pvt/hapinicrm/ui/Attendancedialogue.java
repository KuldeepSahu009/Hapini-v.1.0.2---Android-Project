package com.crm.pvt.hapinicrm.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.crm.pvt.hapinicrm.R;

public class Attendancedialogue extends AppCompatDialogFragment {
    TextView textViewpasscode;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialogueforattendence,null);
        textViewpasscode=view.findViewById(R.id.showpasscodeinattendance);
        SharedPreferences getshared = getActivity().getSharedPreferences("infos", Context.MODE_PRIVATE);
        String passcode=getshared.getString("passcode","no data");
        textViewpasscode.setText(passcode);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}
