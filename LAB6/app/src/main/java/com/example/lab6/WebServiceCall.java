package com.example.lab6;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceCall {

    private static final String TAG = WebServiceCall.class.getSimpleName();
    private static HttpTransportSE ht; // Khai báo HttpTransportSE như một biến lớp để giữ kết nối mở.

    public static String callWSThreadSoapPrimitive(String strURL, String strSoapAction, SoapObject request) {

        try {
            StringBuffer result;
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            if (ht == null) {
                ht = new HttpTransportSE(strURL);
                ht.debug = true;
            }

            ht.call(strSoapAction, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            result = new StringBuffer(response.toString());
            Log.i(TAG, "Kết quả: " + result.toString());
            return result.toString();

        } catch (Exception e) {
            Log.e(TAG, "Lỗi gọi web service: " + e.getMessage());
            return null;
        }
    }
}
