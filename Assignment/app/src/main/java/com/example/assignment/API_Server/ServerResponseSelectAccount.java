package com.example.assignment.API_Server;

import com.example.assignment.Model.Account;

public class ServerResponseSelectAccount {
    private Account[] account;
    private String message;

    public Account[] getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }
}
