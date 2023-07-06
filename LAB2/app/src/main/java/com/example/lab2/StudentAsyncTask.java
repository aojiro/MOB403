package com.example.lab2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public StudentAsyncTask(Context context, String link, String name, String score, TextView tvResult) {
        this.context = context;
        this.link = link;
        this.name = name;
        this.score = score;
        this.tvResult = tvResult;
    }

    private String link, name, score, strResult;


    private TextView tvResult;

    @Override
    protected String doInBackground(String... strings) {
        link += "?name="+name+"&score="+score;
        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            strResult = sb.toString();
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        tvResult.setText(strResult);
    }
}
