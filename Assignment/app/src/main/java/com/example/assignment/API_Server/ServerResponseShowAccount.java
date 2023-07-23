package com.example.assignment.API_Server;

import com.example.assignment.Model.Account;

import java.util.List;

public class ServerResponseShowAccount {
    private List<Account> users;
    private int success;
    private String message;

    public List<Account> getUsers() {
        return users;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

