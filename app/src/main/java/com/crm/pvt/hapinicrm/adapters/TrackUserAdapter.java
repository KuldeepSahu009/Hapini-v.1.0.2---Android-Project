package com.crm.pvt.hapinicrm.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.TrackUserModel;

import com.crm.pvt.hapinicrm.ui.Calendar;

import com.crm.pvt.hapinicrm.ui.TrackUsers;
import com.crm.pvt.hapinicrm.util.UserClickCallback;
import com.crm.pvt.hapinicrm.viewholder.Trackuserviewholders;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TrackUserAdapter extends RecyclerView.Adapter<Trackuserviewholders> {

    private final Context context;
    private final List<TrackUserModel> trackUserModelList;

    private static final String TAG ="TAG" ;

    private  List<String> activeUserList;
    public static String usertypes;
    private UserClickCallback userClickCallback;
    int pos;
    public TrackUserAdapter(
            Context context,
            List<TrackUserModel> trackUserModelList,
            List<String> activeUserList,
            UserClickCallback userClickCallback
    ) {
        this.context = context;
        this.trackUserModelList = trackUserModelList;
        this.activeUserList = activeUserList;
        this.userClickCallback = userClickCallback;
    }

    public TrackUserAdapter(Context context, List<TrackUserModel> trackUserModelList) {
        this.context = context;
        this.trackUserModelList = trackUserModelList;
    }

    @NonNull
    @Override
    public Trackuserviewholders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_user_info_card_view,parent,false);
        return new Trackuserviewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Trackuserviewholders holder, int position) {
        TrackUserModel tempmodel=trackUserModelList.get(position);

        holder.activeStatusUser.setImageResource(R.drawable.red_dot);
        holder.name.setText(tempmodel.getName());
        holder.email.setText(tempmodel.getEmail());
        holder.phone.setText(tempmodel.getPhoneno());
        holder.whatsappno.setText(tempmodel.getWhatsappno());
        holder.passcode.setText(tempmodel.getPasscode());
        holder.password.setText(tempmodel.getPassword());
        holder.state.setText(tempmodel.getState());
        holder.city.setText(tempmodel.getCity());
        holder.location.setText(tempmodel.getLocation());
        holder.addedBy.setText(tempmodel.getAddedBy());

        holder.cardView.setOnClickListener(v -> {
            userClickCallback.navigateToTaskList(tempmodel.getPasscode());
        });


        if(activeUserList != null) {
            for (String passcode : activeUserList) {
                if (passcode.equals(tempmodel.getPasscode())) {
                    holder.activeStatusUser.setImageResource(R.drawable.green_dot);
                    break;
                }
            }
        }
        //For Deletion of User
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usersv2").child(TrackUsers.userType);
                Toast.makeText(v.getContext(), "Deleting User....",Toast.LENGTH_LONG).show();
                reference.child(tempmodel.getPasscode()).removeValue();
                trackUserModelList.clear();
                notifyDataSetChanged();
            }
        });

        if(tempmodel.getImgurl() != null) {
            if (!tempmodel.getImgurl().equals("")) {
                Glide.with(context)
                        .load(tempmodel.getImgurl())
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .into(holder.profileimg);
            }
        }
        holder.downloaduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAdapterPosition();
                checkpermission();
            }
        });
        holder.calluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcallpermission(tempmodel.getPhoneno());
            }
        });
        holder.attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showattendance(tempmodel.getPasscode());
            }
        });
    }
    @Override
    public int getItemCount() {
        return trackUserModelList.size();
    }
    private void checkpermission() {
        Dexter.withContext(context).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(
                new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            createpdf();
                        } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            Toast.makeText(context, "permission nedded", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }
        ).check();
    }

    private void createpdf() {
        Log.e(TAG, "createpdf: " + "pdf");
        File file = context.getFilesDir();
        Log.e(TAG, "createpdf: " + file.getAbsolutePath());
        File storage = new File(file, "/Hapini");
        Log.e(TAG, "createpdf: " + storage.getName());
        storage.mkdir();
        File files=new File(storage.getAbsolutePath(),System.currentTimeMillis()+".pdf");
        try {
            files.createNewFile();
            writeinfile(files);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeinfile(File file) throws IOException {
        Toast.makeText(context, file.getName(), Toast.LENGTH_LONG).show();
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Downloading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.setMargins(0, 0, 0, 0);
        Drawable d = context.getResources().getDrawable(R.drawable.account);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        TrackUserModel tempmodel = trackUserModelList.get(pos);

        ImageData imageData = ImageDataFactory.create(bytes);
        Image image = new Image(imageData);
        Paragraph paragraphname = new Paragraph();
        paragraphname.add("Name:" + tempmodel.getName());
        paragraphname.setFontSize(20);
        Paragraph paragraphemail = new Paragraph();
        paragraphemail.add("Email:" + tempmodel.getEmail());
        paragraphemail.setFontSize(20);
        Paragraph paragraphphoneo = new Paragraph();
        paragraphphoneo.add("Phone no:" + tempmodel.getPhoneno());
        paragraphphoneo.setFontSize(20);
        Paragraph paragraphwhatsappno = new Paragraph();
        paragraphwhatsappno.add("Whatsapp no:" + tempmodel.getWhatsappno());
        paragraphwhatsappno.setFontSize(20);
        Paragraph paragraphpasscode = new Paragraph();
        paragraphpasscode.add("Passcode:" + tempmodel.getPasscode());
        paragraphpasscode.setFontSize(20);
        Paragraph paragraphpassword = new Paragraph();
        paragraphpassword.add("Password:" + tempmodel.getPasscode());
        paragraphpassword.setFontSize(20);
        Paragraph paragraphlocation = new Paragraph();
        paragraphlocation.add("Location:" + tempmodel.getLocation());
        paragraphlocation.setFontSize(20);
        // paragraph.setFont(context.getResources().getFont(R.font.alef_bold));
        document.add(image.setHeight(250).setWidth(250).setFixedPosition(1, 330, 550));
        document.add(paragraphname.setRelativePosition(0, 10, 0, 0));
        document.add(paragraphemail.setRelativePosition(0, 0, 0, 0));
        document.add(paragraphphoneo.setRelativePosition(0, 0, 0, 0));
        document.add(paragraphwhatsappno.setRelativePosition(0, 0, 0, 0));
        document.add(paragraphpasscode.setRelativePosition(0, 0, 0, 0));
        document.add(paragraphpassword.setRelativePosition(0, 0, 0, 0));
        document.add(paragraphlocation.setRelativePosition(0, 0, 0, 0));
        document.close();
        Toast.makeText(context, "Download Complete", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

        openfile(file);

    }

    private void openfile(File file) {
        Intent i = new Intent();
        Log.e(TAG, "openfile: "+file.getAbsolutePath() );
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            Uri apkUri = FileProvider.getUriForFile(context.getApplicationContext(),context.getPackageName()+".provider",file);
            i.setDataAndType(apkUri,"application/pdf");
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            i.setDataAndType(Uri.fromFile(file),"application/pdf");
        }
        i.setAction(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void showattendance(String passcode) {
        Log.e(TAG, "showattendance: " + passcode);
        ArrayList<java.util.Calendar> calendarArrayList=new ArrayList<>();
        com.crm.pvt.hapinicrm.ui.Calendar calendar=new com.crm.pvt.hapinicrm.ui.Calendar(calendarArrayList);
        //Log.e(TAG, "showattedance: "+passcode );
        Log.e(TAG, "showattendance: "+usertypes );

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("attendenceV2").child("user")
                .child(usertypes)
                .child(passcode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String date=dataSnapshot.getKey().toString();
                    i++;
                    //// Log.e(TAG, "onDataChange: "+date );
                    int year=Integer.parseInt(date.substring(0,4));
                    //Log.e(TAG, "onDataChange: "+year );
                    int month=Integer.parseInt(date.substring(5,7));
                    int days=Integer.parseInt(date.substring(8,10));
                    // Log.e(TAG, "onDataChange: "+month+""+days );
                    java.util.Calendar calendar1= java.util.Calendar.getInstance();
                    calendar1.set(year,month-1,days);
                    calendarArrayList.add(calendar1);
                }
                calendar.show(((FragmentActivity)context).getSupportFragmentManager(),"TAG");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
    private void checkcallpermission(String no){
        Dexter.withContext(context).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                calluser(no);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(context,"need permission",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }
    private void calluser(String no){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+no));
        context.startActivity(callIntent);
    }

}
