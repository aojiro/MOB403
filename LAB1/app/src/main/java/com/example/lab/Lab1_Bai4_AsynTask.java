package com.example.lab;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Lab1_Bai4_AsynTask extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    TextView tvResult;
    EditText edtText;
    Context context;
    String resp;

    public Lab1_Bai4_AsynTask(Context context, TextView tvResult, EditText edtText) {
        this.context = context;
        this.tvResult = tvResult;
        this.edtText = edtText;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Tiêu đề", "Xin chờ : " + edtText.getText().toString() + " giây");
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            int time = Integer.parseInt(strings[0]) * 1000;
            Thread.sleep(time);
            resp = "Sleep trong " + strings[0] + " giây";
        } catch (InterruptedException e) {
            e.printStackTrace();
            resp = e.getMessage();
        }
        return resp;
    }



    //Kết quả gán lại cho view sau khi hoàn tất công việc
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        tvResult.setText(result);
    }

}