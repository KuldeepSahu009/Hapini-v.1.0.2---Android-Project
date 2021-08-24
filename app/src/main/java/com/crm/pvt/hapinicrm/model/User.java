package com.crm.pvt.hapinicrm.model;

public class User {
    String name;
    String email;
    String mobileNo;
    String whatsAppNo;
    String state;
    String city;
    String locality;
    String passcode;
    String password;
    String addedBy;

    public User(String name, String email, String  mobileNo, String whatsAppNo, String state, String city, String locality, String passcode, String password , String addedBy) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.whatsAppNo = whatsAppNo;
        this.state = state;
        this.city = city;
        this.locality = locality;
        this.passcode = passcode;
        this.password = password;
        this.addedBy = addedBy;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getWhatsAppNo() {
        return whatsAppNo;
    }

    public void setWhatsAppNo(String whatsAppNo) {
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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
