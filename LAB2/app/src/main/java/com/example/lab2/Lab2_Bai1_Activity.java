package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Lab2_Bai1_Activity extends AppCompatActivity {

    EditText edtName, edtScore;
    TextView tvRs;
    String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_bai1);
        link = "http://192.168.217.90:8080/my-web/student_GET.php";

        edtName = (EditText) findViewById(R.id.edName);
        edtScore = (EditText) findViewById(R.id.edScore);
        tvRs = (TextView) findViewById(R.id.tvResult);
    }

    public void loadData(View view){
        StudentAsyncTask asyncTask = new StudentAsyncTask(this, link,edtName.getText().toString(), edtScore.getText().toString(), tvRs);

        asyncTask.execute();
    }
}