package com.example.lab6;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

public class ConvertTemperatureTask extends AsyncTask<String, Void, String> {

    ProgressDialog pDialog;
    private MainActivity activity;
    private String soapAction;
    private String methodName;
    private String paramsName;

    public ConvertTemperatureTask(MainActivity activity, String soapAction,
                                  String methodName, String paramsName) {
        this.soapAction = soapAction;
        this.methodName = methodName;
        this.paramsName = paramsName;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Vui lòng chờ...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
// tạo mới đối tượng yêu cầu soap
            SoapObject request = new SoapObject(Constants.NAME_SPACE,
                    methodName);
// thêm thuộc tính cho đối tượng soap
            request.addProperty(paramsName, params[0]);
// yêu cầu đến máy chủ và nhận phản hồi Soap Primitive
            return WebServiceCall.callWSThreadSoapPrimitive(Constants.URL, soapAction, request);
        } catch (Exception e) {
            Log.e("Error", "Lỗi khi gọi web service: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (result == null) {
            Log.e("Error", "Không nhận được kết quả");
        } else {
            Log.i("Success", result);
// gọi phương thức callback của Activity
            activity.callBackDataFromAsyncTask(result);
        }
    }

}