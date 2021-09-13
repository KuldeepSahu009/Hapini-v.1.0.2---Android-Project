package com.crm.pvt.hapinicrm.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;

public class Trackuserviewholders extends RecyclerView.ViewHolder {
    public TextView name, email, phone, whatsappno, passcode, password, state , city , location , addedBy,nameaddedby;
    public ImageView profileimg, delete , activeStatusUser,downloaduser,attendance,calluser;
    public CardView cardView;

    public Trackuserviewholders(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cvUser);
        name = itemView.findViewById(R.id.tvCustomerName);
        email = itemView.findViewById(R.id.trackuseremailid);
        phone = itemView.findViewById(R.id.trackuserphoneno);
        whatsappno = itemView.findViewById(R.id.trackuserwhatsappno);
        passcode = itemView.findViewById(R.id.trackuserpasscode);
        password = itemView.findViewById(R.id.trackuserpassword);
        state = itemView.findViewById(R.id.trackuserState);
        city = itemView.findViewById(R.id.trackuserCity);
        location = itemView.findViewById(R.id.trackuserlocation);
        addedBy = itemView.findViewById(R.id.trackUserAddedBy);
        profileimg = itemView.findViewById(R.id.trackuserprofilepic);
        delete = itemView.findViewById(R.id.trackuserdeleteprofile);
        activeStatusUser = itemView.findViewById(R.id.trackUserStatus);
        downloaduser=itemView.findViewById(R.id.trackuserdownload);
        attendance=itemView.findViewById(R.id.trackuserattendance);
        calluser=itemView.findViewById(R.id.trackusercall);
        nameaddedby=itemView.findViewById(R.id.trackUserAddedByname);

    }
}



