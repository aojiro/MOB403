package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {
    private TextView userNameTV;

    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logoutBtn = findViewById(R.id.idBtnLogin);

        // initializing our variables
       userNameTV = findViewById(R.id.idTVHeader);

        // getting data from intent.
        String name = getIntent().getStringExtra("username");


        // setting data to our text view.
      //  userNameTV.setText(name);

        // initializing click listener for logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logging out our user.
                Toast.makeText(Login_Activity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
