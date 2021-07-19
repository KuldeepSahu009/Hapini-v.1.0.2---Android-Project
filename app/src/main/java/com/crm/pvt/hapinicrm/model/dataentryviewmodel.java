package com.crm.pvt.hapinicrm.model;

public class dataentryviewmodel {
    String name;
    String email;
    String contactnumber;
    String location;

    public dataentryviewmodel(String name, String email, String contactnumber, String location) {
        this.name = name;
        this.email = email;
        this.contactnumber = contactnumber;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public String getLocation() {
        return location;
    }
}
