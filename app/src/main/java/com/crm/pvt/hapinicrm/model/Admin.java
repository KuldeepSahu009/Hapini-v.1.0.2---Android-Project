package com.crm.pvt.hapinicrm.model;

import java.io.Serializable;

public class Admin implements Serializable {

    String name;
    String type;
    String email;
    String contact;
    String doj;

    public Admin(String name, String type, String email, String contact, String doj) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.contact = contact;
        this.doj = doj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

}
