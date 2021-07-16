package com.crm.pvt.hapinicrm.model;
import java.io.Serializable;
public class videoeditorviewmodel implements Serializable {
    String name;
    String email;
    String contactnumber;
    String location;

    public videoeditorviewmodel(String name, String email, String contactnumber, String location) {
        this.name = name;
        this.email = email;
        this.contactnumber = contactnumber;
        this.location = location;
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

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

