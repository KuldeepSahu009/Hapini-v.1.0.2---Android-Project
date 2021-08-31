package com.crm.pvt.hapinicrm.model;

public class Admin {
    String name;
    String email;
    String phoneno;
    String whatsappno;
    String passcode;
    String password;
    String state;
    String city;
    String location;
    String imgurl;
    String addedBy;

    public Admin() { }

    public Admin(
            String name,
            String email,
            String phoneno,
            String whatsappno,
            String passcode,
            String password,
            String state,
            String city,
            String location,
            String imgurl,
            String addedBy
    ) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.whatsappno = whatsappno;
        this.passcode = passcode;
        this.password = password;
        this.state = state;
        this.city = city;
        this.location = location;
        this.imgurl = imgurl;
        this.addedBy = addedBy;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
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

    public String getCity() {
        return city;
    }
}
