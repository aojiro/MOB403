package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class Lab1Bai1Activity extends Activity implements View.OnClickListener {
    TextView tvMessage;
    Button btnLoadImage;
    ImageView imgLoadImage;
    String url = "https://i.kym-cdn.com/photos/images/newsfeed/001/052/958/ddd.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_bai1);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        imgLoadImage = (ImageView) findViewById(R.id.imgView);
        btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        btnLoadImage.setOnClickListener(this);
    }

    //Load hình ảnh từ server
    private Bitmap loadImageFromNetWork(String link) {
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();

        }
        return bmp;
    }

    @Override
    public void onClick(View view) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = loadImageFromNetWork(url);
                imgLoadImage.post(new Runnable() {
                    @Override
                    public void run() {
                        tvMessage.setText("Đang tải ảnh xuống");
                        imgLoadImage.setImageBitmap(bitmap);
                        // Lệnh sau khi load ảnh xong
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText("Xong rồi đoá");
                            }
                        }, 1000); // Thời gian chờ là 3000 mili giây (3 giây)
                    }
                });
            }
        });
        thread.start();
    }
}
