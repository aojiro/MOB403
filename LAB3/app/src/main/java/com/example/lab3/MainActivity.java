package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Bai1(View view) {
        Intent intent = new Intent(MainActivity.this, Lab3_Bai1_Activity.class);
        startActivity(intent);
    }
    public void Bai2(View view) {
        Intent intent = new Intent(MainActivity.this, Lab3_Bai2_Activity.class);
        startActivity(intent);
    }
    public void Bai3(View view) {
        Intent intent = new Intent(MainActivity.this, Lab3_Bai3_Activity.class);
        startActivity(intent);
    }
    public void Bai4(View view) {
        Intent intent = new Intent(MainActivity.this, Lab3_Bai4_Activity.class);
        startActivity(intent);
    }
}