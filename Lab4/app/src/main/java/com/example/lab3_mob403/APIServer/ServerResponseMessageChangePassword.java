package com.example.lab3_mob403.APIServer;

import com.example.lab3_mob403.Model.Account;

public class ServerResponseMessageChangePassword {
    private String message;
    private Account account;

    public Account getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }
}
