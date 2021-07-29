package com.crm.pvt.hapinicrm.model;

// Sample Admin Model
//TODO Admin Model

public class Admin {
     String name;
     String email;
     String phoneno;
     String whatsappno;
     String passcode;
     String password;
     String location;
     String imgurl;

    public Admin(String name, String email, String phoneno, String whatsappno, String passcode, String password, String location, String imgurl) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.whatsappno = whatsappno;
        this.passcode = passcode;
        this.password = password;
        this.location = location;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getWhatsappno() {
        return whatsappno;
    }

    public String getPasscode() {
        return passcode;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getImgurl() {
        return imgurl;
    }}
