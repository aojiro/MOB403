package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Lab3_Bai1_Activity extends AppCompatActivity {

    private ListView lvContact;
    GetContact getContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_bai1);
        lvContact = (ListView) findViewById(R.id.listContact);
        getContact = new GetContact(this,lvContact);
        getContact.execute();
    }
}