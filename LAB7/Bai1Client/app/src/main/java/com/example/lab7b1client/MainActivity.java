package com.example.lab7b1client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Socket socket;
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "10.0.0.2";
    EditText edt;
    Button btnSend;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = findViewById(R.id.edtText);
        btnSend = findViewById(R.id.btnActive);
        btnSend.setOnClickListener(this);
        new ConnectTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActive:
                str = edt.getText().toString();
                if (socket != null && socket.isConnected()) {
                    new SendDataTask().execute(str);
                } else {
                    Toast.makeText(this, "Kết nối chưa sẵn sàng!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class SendDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                out.println(strings[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
