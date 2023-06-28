package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Lab1_Bai2_EX2_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button btnLoad;
    private ProgressDialog progressDialog;
    private String url = "https://cdn.pixabay.com/photo/2016/12/17/14/00/tree-1913523_960_720.png";
    private Bitmap bitmap = null;
    private TextView tvMesseage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_bai2_ex2);
        imageView = (ImageView) findViewById(R.id.imgLoad);
        btnLoad = (Button) findViewById(R.id.btnLoadIG);
        tvMesseage = (TextView) findViewById(R.id.tvMessage);
        btnLoad.setOnClickListener(this);

    }

    private final Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String message = bundle.getString("message");
            tvMesseage.setText(message);
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        progressDialog = ProgressDialog.show(Lab1_Bai2_EX2_Activity.this, "", "Downloading...");
        Runnable aRunnable = new Runnable() {
            @Override
            public void run() {
                bitmap = downLoadBitMap(url);
                Message msg = messageHandler.obtainMessage();
                Bundle bundle = new Bundle();
                String threadMessage = "Thành Công Rồi Nè";
                bundle.putString("Nội dung", threadMessage);
                msg.setData(bundle);
                messageHandler.sendMessage(msg);
            }
        };
        Thread aThread = new Thread(aRunnable);
        aThread.start();
    }

    private Bitmap downLoadBitMap(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}