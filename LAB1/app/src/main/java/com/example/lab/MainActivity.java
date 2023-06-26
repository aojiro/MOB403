package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnBai1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Bai1(View view) {
        Intent intent = new Intent(MainActivity.this, Lab1Bai1Activity.class);
        startActivity(intent);
    }
    public void Bai2(View view) {
        Intent intent = new Intent(MainActivity.this, Lab1_Bai2_EX2_Activity.class);
        startActivity(intent);
    }
    public void Bai3(View view) {
        Intent intent = new Intent(MainActivity.this, Lab1_Bai3_Activity.class);
        startActivity(intent);
    }

}