package com.crm.pvt.hapinicrm.model;

import java.io.Serializable;

public class Admin implements Serializable {

    String name;
    String email;
    String mobileNumber;
    String whatsappNumber;
    String password;
    String type;
    String doj;

    public Admin(String name, String email, String mobileNumber, String whatsappNumber, String password, String type,String doj) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.whatsappNumber = whatsappNumber;
        this.password = password;
        this.type = type;
        this.doj = doj;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
