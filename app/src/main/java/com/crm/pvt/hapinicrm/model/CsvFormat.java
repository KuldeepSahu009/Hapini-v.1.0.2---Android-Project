package com.crm.pvt.hapinicrm.model;

public class CsvFormat {
    private String name , city , type , contact;

    public CsvFormat(String name, String city, String type, String contact) {
        this.name = name;
        this.city = city;
        this.type = type;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }
}
