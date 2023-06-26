package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.Lab1_Bai3_AsyncTask;

public class Lab1_Bai3_Activity extends AppCompatActivity implements View.OnClickListener, Lab1_Bai3_AsyncTask.Listener {
    TextView tvMessage;
    Button btnLoad;
    ImageView imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_bai3);

        tvMessage = findViewById(R.id.tvMessage);
        btnLoad = findViewById(R.id.btn_Load);
        imgLoad = findViewById(R.id.imgLoad);
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Lab1_Bai3_AsyncTask(this,this).execute("https://haycafe.vn/wp-content/uploads/2022/03/Background-anime-1-800x450.jpg");
    }


    @Override
    public void onImageLoader(Bitmap bitmap) {
        imgLoad.setImageBitmap(bitmap);
        tvMessage.setText("Đã tải ảnh");

    }

    @Override
    public void onError() {
        tvMessage.setText("Lỗi tải ảnh");
    }
}