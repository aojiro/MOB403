package com.example.LAB4.APIServer;

import com.example.lab4.Model.Account;

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
