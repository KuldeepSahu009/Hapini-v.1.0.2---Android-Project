package com.crm.pvt.hapinicrm.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;

public class Trackuserviewholders extends RecyclerView.ViewHolder {
    public TextView name, email, phone, whatsappno, passcode, password, location;
    public ImageView profileimg, delete,downloaduser,attendance;

    public Trackuserviewholders(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tvCustomerName);
        email = itemView.findViewById(R.id.trackuseremailid);
        phone = itemView.findViewById(R.id.trackuserphoneno);
        whatsappno = itemView.findViewById(R.id.trackuserwhatsappno);
        passcode = itemView.findViewById(R.id.trackuserpasscode);
        password = itemView.findViewById(R.id.trackuserpassword);
        location = itemView.findViewById(R.id.trackuserlocation);
        profileimg = itemView.findViewById(R.id.trackuserprofilepic);
        delete = itemView.findViewById(R.id.trackuserdeleteprofile);
        downloaduser=itemView.findViewById(R.id.trackuserdownload);
        attendance=itemView.findViewById(R.id.trackuserattendance);

    }
}

