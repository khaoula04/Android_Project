package com.example.contactapp;

public class ContactModel {
    String name,number,email;

    int id;


    public ContactModel(String name, String number, String email,int id) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void  setId(){

        this.id = id;
    }
}
