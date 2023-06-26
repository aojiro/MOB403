package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.window.SplashScreen;


public class Lab1_Bai2_Activity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    ImageView ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_bai2);
        ImageView img = (ImageView) findViewById(R.id.imgView);
        img.setImageResource(R.drawable.fpt);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Lab1_Bai2_Activity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }


}