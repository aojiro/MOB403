package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Lab2_Bai2_Activity extends AppCompatActivity {
    EditText edRong, edDai;
    TextView tvResult;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_bai2);
        edDai = (EditText) findViewById(R.id.edtChieuDai);
        edRong = (EditText) findViewById(R.id.edtChieuRong);
        tvResult = (TextView) findViewById(R.id.tvResult);
        link = "http://192.168.146.90:8080/my-web/hinhchunhat_post.php";
    }

    public void Loading(View view) {
        BackgroundTask_POST backgroundTask_post = new BackgroundTask_POST(this, link,edDai.getText().toString(), edRong.getText().toString(), tvResult);
        backgroundTask_post.execute();
    }
}
