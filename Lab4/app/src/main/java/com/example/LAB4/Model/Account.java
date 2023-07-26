package com.example.lab4.Model;

public class Account {
    private int id;
    private String name;
    private String email;
    private String encrypted_password;


    public Account(String name, String email, String encrypted_password) {
        this.name = name;
        this.email = email;
        this.encrypted_password = encrypted_password;
    }


    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public int getId() {
        return id;
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
}
