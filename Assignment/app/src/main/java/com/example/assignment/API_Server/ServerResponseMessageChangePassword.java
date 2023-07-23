package com.example.assignment.API_Server;

import com.example.assignment.Model.Account;

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
