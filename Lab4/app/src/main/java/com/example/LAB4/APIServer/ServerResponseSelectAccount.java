package com.example.LAB4.APIServer;

import com.example.lab4.Model.Account;

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
