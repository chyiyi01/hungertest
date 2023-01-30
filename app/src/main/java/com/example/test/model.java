package com.example.test;

public class model {
    String name,type,contact,userid;

    public model() {
    }

    public model(String name, String type, String contact, String userid) {
        this.name = name;
        this.type = type;
        this.contact = contact;
        this.userid = userid;
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

    public void setType(String type) { this.type = type;}

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

