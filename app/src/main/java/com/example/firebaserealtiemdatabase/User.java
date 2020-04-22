package com.example.firebaserealtiemdatabase;

public class User {
    public String firstname,lastname,address;

    public User() {
    }

    public User(String firstname, String lastname, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }
}
