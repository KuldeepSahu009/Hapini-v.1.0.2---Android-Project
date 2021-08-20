package com.crm.pvt.hapinicrm.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.model.Admin;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.ui.Calendar;
import com.crm.pvt.hapinicrm.ui.DataCallBackTackFranchise;
import com.crm.pvt.hapinicrm.ui.Datacallbacktrackuser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TrackFranchiseAdapter extends RecyclerView.Adapter<TrackFranchiseAdapter.TrackFranchiseViewHolder> {
    Context context;
    ArrayList<Franchise> franchises;
    DataCallBackTackFranchise dataCallBackTackFranchise;
    private static final String TAG = "TAG";
    int pos;

    public TrackFranchiseAdapter(Context context, ArrayList<Franchise> franchises, DataCallBackTackFranchise dataCallBackTackFranchise) {
        this.context = context;
        this.franchises = franchises;
        this.dataCallBackTackFranchise = dataCallBackTackFranchise;
    }

    @NonNull
    @Override
    public TrackFranchiseAdapter.TrackFranchiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_admin_details, parent, false);
        return new TrackFranchiseAdapter.TrackFranchiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackFranchiseAdapter.TrackFranchiseViewHolder holder, int position) {
        Franchise franchise = franchises.get(position);
        holder.name.setText(franchise.getName());
        holder.email.setText(franchise.getEmail());
        holder.mobile.setText(franchise.getPhoneno());
        holder.whatsappno.setText(franchise.getWhatsappno());
        holder.passcode.setText(franchise.getPasscode());
        holder.password.setText(franchise.getPassword());
        holder.location.setText(franchise.getLocation());

        holder.attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        showattedance("franchise",franchise.getPasscode());



            }
        });

        holder.deleteAdmin.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Attention");
            builder.setMessage("Do you want to delete this franchise?");
            builder.setCancelable(true);

            builder.setPositiveButton("ok", (dialog, which) -> {
                if (franchises.size() > 0) {
                    dataCallBackTackFranchise.remove(franchises.get(position));
                }
                dialog.dismiss();
            });

            builder.setNegativeButton("cancel", (dialog, which) -> {
                Log.e(TAG, "onClick: " + "cancel");
                dialog.dismiss();
            });

            AlertDialog deleteAdminDialog = builder.create();
            deleteAdminDialog.show();
        });

        if (!franchise.getImgurl().isEmpty()) {
            Glide.with(context).load(franchise.getImgurl()).into(holder.profilepic);
        }
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                checkpermission();
            }
        });
        holder.calladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcallpermission(franchise.getPhoneno());
            }
        });

    }


    @Override
    public int getItemCount() {

        return franchises.size();
    }


    static class TrackFranchiseViewHolder extends RecyclerView.ViewHolder {
        ImageView profilepic, deleteAdmin, download, calladmin,attendance;

        TextView name, email, mobile, location, whatsappno, password, passcode;

        public TrackFranchiseViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trackadminname);
            email = itemView.findViewById(R.id.trackadminemailid);
            mobile = itemView.findViewById(R.id.trackadminphoneno);
            location = itemView.findViewById(R.id.trackadminlocation);
            whatsappno = itemView.findViewById(R.id.trackadminwhatsappno);
            password = itemView.findViewById(R.id.trackadminpassword);
            passcode = itemView.findViewById(R.id.trackadminpasscode);
            profilepic = itemView.findViewById(R.id.trackadminprofilepic);
            deleteAdmin = itemView.findViewById(R.id.trackadmindeleteprofile);
            download = itemView.findViewById(R.id.trackadmindownload);
            calladmin = itemView.findViewById(R.id.trackadmincall);
            attendance=itemView.findViewById(R.id.trackadminattendance);
        }
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
        String internalstorage = System.getenv("EXTERNAL_STORAGE");
        File storage = new File(internalstorage + "/Hapini");
        storage.mkdir();
        File file = new File(storage.getAbsolutePath(), System.currentTimeMillis() + ".pdf");
        try {
            file.createNewFile();
            writeinfile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeinfile(File file) throws IOException {
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

        Franchise admin = franchises.get(pos);

        ImageData imageData = ImageDataFactory.create(bytes);
        Image image = new Image(imageData);
        Paragraph paragraphname = new Paragraph();
        paragraphname.add("Name:" + admin.getName());
        paragraphname.setFontSize(20);
        Paragraph paragraphemail = new Paragraph();
        paragraphemail.add("Email:" + admin.getEmail());
        paragraphemail.setFontSize(20);
        Paragraph paragraphphoneo = new Paragraph();
        paragraphphoneo.add("Phone no:" + admin.getPhoneno());
        paragraphphoneo.setFontSize(20);
        Paragraph paragraphwhatsappno = new Paragraph();
        paragraphwhatsappno.add("Whatsapp no:" + admin.getWhatsappno());
        paragraphwhatsappno.setFontSize(20);
        Paragraph paragraphpasscode = new Paragraph();
        paragraphpasscode.add("Passcode:" + admin.getPasscode());
        paragraphpasscode.setFontSize(20);
        Paragraph paragraphpassword = new Paragraph();
        paragraphpassword.add("Password:" + admin.getPasscode());
        paragraphpassword.setFontSize(20);
        Paragraph paragraphlocation = new Paragraph();
        paragraphlocation.add("Location:" + admin.getLocation());
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
        Toast.makeText(context, "sdone", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

        openfile(file);

    }

    private void openfile(File file) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.e(TAG, "openfile: " + file.getAbsolutePath());
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        i.setDataAndType(uri, "application/pdf");
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);
    }

    private void checkcallpermission(String no) {

        Dexter.withContext(context).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                callfranchise(no);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(context, "need permission", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private void callfranchise(String no) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + no));
        context.startActivity(callIntent);


    }
    private void showattedance(String type,String passcode){
        ArrayList<java.util.Calendar>calendarArrayList=new ArrayList<>();
        Calendar calendar=new Calendar(calendarArrayList);
        //Log.e(TAG, "showattedance: "+passcode );

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("attendencev2").child("admin")
                .child(type)
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

}