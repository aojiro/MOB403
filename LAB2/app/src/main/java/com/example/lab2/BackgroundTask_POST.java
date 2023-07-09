package com.example.lab2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask_POST extends AsyncTask<String, Void, String> {

    public BackgroundTask_POST(Context context, String link, String dai, String rong, TextView tvResult) {
        this.context = context;
        this.link = link;
        this.dai = dai;
        this.rong = rong;
        this.tvResult = tvResult;
    }

    private Context context;
    private String link, dai, rong, strResult;
    private TextView tvResult;


    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(link);
            String strParams = "chieudai=" + URLEncoder.encode(dai, "utf-8") + "&chieurong=" + URLEncoder.encode(rong, "utf-8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(strParams.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
            //printWriter.println(strParams);
            printWriter.print(strParams);
            printWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            strResult = sb.toString();

        } catch (Exception ex) {
            Log.e("Error BackgroundTask_POST", "//=" + ex.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        tvResult.setText(strResult);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progessbar show
    }
}
