package com.crm.pvt.hapinicrm.model;

public class Masteradmin {
   private String email,imgurl,name,password,passcode;

    public Masteradmin(String email, String imgurl, String name, String password, String passcode) {
        this.email = email;
        this.imgurl = imgurl;
        this.name = name;
        this.password = password;
        this.passcode = passcode;
    }

    public String getEmail() {
        return email;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPasscode() {
        return passcode;
    }
}
