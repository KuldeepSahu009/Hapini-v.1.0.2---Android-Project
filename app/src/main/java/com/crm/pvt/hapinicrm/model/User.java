package com.crm.pvt.hapinicrm.model;

public class User {
    String name;
    String email;
    int mobileNo;
    int whatsAppNo;
    String city;
    String locality;
    int passcode;
    int password;

    public User(String name, String email, int mobileNo, int whatsAppNo, String city, String locality, int passcode, int password) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.whatsAppNo = whatsAppNo;
        this.city = city;
        this.locality = locality;
        this.passcode = passcode;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(int mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getWhatsAppNo() {
        return whatsAppNo;
    }

    public void setWhatsAppNo(int whatsAppNo) {
        this.whatsAppNo = whatsAppNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getPasscode() {
        return passcode;
    }

    public void setPasscode(int passcode) {
        this.passcode = passcode;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
