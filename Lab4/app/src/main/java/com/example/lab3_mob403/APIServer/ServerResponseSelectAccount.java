package com.example.lab3_mob403.APIServer;

import com.example.lab3_mob403.Model.Account;

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
