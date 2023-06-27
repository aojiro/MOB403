package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Lab1_Bai4_Activity extends AppCompatActivity implements View.OnClickListener {
    EditText edtText;
    Button btnRun;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_bai4);
        edtText = findViewById(R.id.edtTime);
        btnRun = findViewById(R.id.btnRun);
        tvResult = findViewById(R.id.tvResult);
        btnRun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String sleepTime = edtText.getText().toString();
        new Lab1_Bai4_AsynTask(this, tvResult, edtText).execute(sleepTime);


    }
}