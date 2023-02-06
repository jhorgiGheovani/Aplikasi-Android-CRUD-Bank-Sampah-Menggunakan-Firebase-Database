package com.example.aplikasibanksampah;

public class User {

    String Email, Name;

    public User(){}

    public User(String email, String name ) {
        Email = email;
        Name = name;

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
