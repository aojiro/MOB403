package com.example.bai2client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText addressEditText;
    private EditText portEditText;
    private Button connectButton;
    private Button clearButton;
    private TextView responseTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressEditText = (EditText) findViewById(R.id.addressEditText);
        portEditText = (EditText) findViewById(R.id.portEditText);
        connectButton = (Button) findViewById(R.id.connectButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        responseTextView = (TextView) findViewById(R.id.responseTextView);

        Client c = new Client("192.168.1.125", 8080, responseTextView);
        c.execute();
    }
}