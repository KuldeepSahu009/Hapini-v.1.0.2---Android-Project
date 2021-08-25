package com.crm.pvt.hapinicrm.model;

import java.io.Serializable;

public class TrackUserModel implements Serializable {

    private String name, email, phoneno, whatsappno, passcode, password, state , city , location, addedBy ,imgurl;

    public TrackUserModel() { }

    public TrackUserModel(String name, String email, String phoneno, String whatsappno, String passcode, String password, String state , String city , String location, String addedBy , String imgurl) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.whatsappno = whatsappno;
        this.passcode = passcode;
        this.password = password;
        this.state = state;
        this.city = city;
        this.location = location;
        this.addedBy = addedBy;
        this.imgurl = imgurl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setWhatsappno(String whatsappno) {
        this.whatsappno = whatsappno;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImgurl(String imgurl) {
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
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
