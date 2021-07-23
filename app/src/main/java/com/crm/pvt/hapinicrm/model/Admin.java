package com.crm.pvt.hapinicrm.model;

// Sample Admin Model
//TODO Admin Model

public class Admin {
    private String name;
    private String email;
    private String contactNumber;
    private String location;

    public Admin(String name, String email, String contactNumber, String location) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
