package com.example.lab3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactList {
    @SerializedName("contacts")
    @Expose
    private ArrayList<Contact_Bai4> contacts = new ArrayList<>();
    /**
     * @return The contacts
     */
    public ArrayList<Contact_Bai4> getContacts() {
        return contacts;
    }
    /**
     * @param contacts The contacts
     */
    public void setContacts(ArrayList<Contact_Bai4> contacts) {
        this.contacts = contacts;
    }
}

